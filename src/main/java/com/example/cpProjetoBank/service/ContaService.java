package com.example.cpProjetoBank.service;

import com.example.cpProjetoBank.model.Conta;
import com.example.cpProjetoBank.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta cadastrarConta(Conta conta) {
        if (conta.getDataAbertura().isAfter(LocalDate.now())) {
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

    public void encerrarConta(Long id) {
        Optional<Conta> conta = contaRepository.findById(id);
        conta.ifPresent(c -> {
            c.setAtiva(false);
            contaRepository.save(c);
        });
    }

    public Conta realizarDeposito(Long id, double valor) {
        Optional<Conta> conta = contaRepository.findById(id);
        if (conta.isPresent() && valor > 0) {
            Conta c = conta.get();
            c.setSaldo(c.getSaldo() + valor);
            return contaRepository.save(c);
        }
        return null;
    }

    public Conta realizarSaque(Long id, double valor) {
        Optional<Conta> conta = contaRepository.findById(id);
        if (conta.isPresent() && valor > 0 && conta.get().getSaldo() >= valor) {
            Conta c = conta.get();
            c.setSaldo(c.getSaldo() - valor);
            return contaRepository.save(c);
        }
        return null;
    }

    public Conta realizarPix(Long origemId, Long destinoId, double valor) {
        Optional<Conta> origem = contaRepository.findById(origemId);
        Optional<Conta> destino = contaRepository.findById(destinoId);

        if (origem.isPresent() && destino.isPresent() && valor > 0 && origem.get().getSaldo() >= valor) {
            Conta contaOrigem = origem.get();
            Conta contaDestino = destino.get();

            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);

            contaRepository.save(contaOrigem);
            contaRepository.save(contaDestino);
            return contaOrigem;
        }
        return null;
    }
}
