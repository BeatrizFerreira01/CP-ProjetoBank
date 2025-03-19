package com.example.cpProjetoBank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cpProjetoBank.model.Conta;
import com.example.cpProjetoBank.repository.ContaRepository;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta cadastrarConta(Conta conta) {
        if (conta.getDataAbertura() == null || conta.getDataAbertura().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de abertura não pode ser no futuro.");
        }
        conta.setAtiva(true);
        return contaRepository.save(conta);
    }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.findById(id);
    }

    public Optional<Conta> buscarPorCpf(String cpf) {
        return contaRepository.findByCpfTitular(cpf);
    }

    public boolean encerrarConta(Long id) {
        Optional<Conta> conta = contaRepository.findById(id);
        if (conta.isPresent()) {
            Conta c = conta.get();
            if (!c.isAtiva()) {
                return false; // Conta já está inativa
            }
            c.setAtiva(false);
            contaRepository.save(c);
            return true;
        }
        return false;
    }

    public Conta realizarDeposito(Long id, double valor) {
        Optional<Conta> conta = contaRepository.findById(id);
        if (conta.isPresent()) {
            Conta c = conta.get();
            if (!c.isAtiva() || valor <= 0) {
                return null; // Conta inativa ou valor inválido
            }
            c.setSaldo(c.getSaldo() + valor);
            return contaRepository.save(c);
        }
        return null;
    }

    public Conta realizarSaque(Long id, double valor) {
        Optional<Conta> conta = contaRepository.findById(id);
        if (conta.isPresent()) {
            Conta c = conta.get();
            if (!c.isAtiva() || valor <= 0 || c.getSaldo() < valor) {
                return null; // Conta inativa, saldo insuficiente ou valor inválido
            }
            c.setSaldo(c.getSaldo() - valor);
            return contaRepository.save(c);
        }
        return null;
    }

    public boolean realizarPix(Long origemId, Long destinoId, double valor) {
        Optional<Conta> origem = contaRepository.findById(origemId);
        Optional<Conta> destino = contaRepository.findById(destinoId);

        if (origem.isPresent() && destino.isPresent()) {
            Conta contaOrigem = origem.get();
            Conta contaDestino = destino.get();

            if (!contaOrigem.isAtiva() || !contaDestino.isAtiva() || valor <= 0 || contaOrigem.getSaldo() < valor) {
                return false; // Conta inativa, saldo insuficiente ou valor inválido
            }

            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);

            contaRepository.save(contaOrigem);
            contaRepository.save(contaDestino);
            return true;
        }
        return false;
    }
}
