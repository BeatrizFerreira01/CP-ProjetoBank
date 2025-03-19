package com.example.cpProjetoBank.repository;

import com.example.cpProjetoBank.model.Conta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContaRepository {
    private List<Conta> contas = new ArrayList<>();
    private Long nextId = 1L; // Variável para gerar IDs automaticamente

    public Conta save(Conta conta) {
        if (conta.getId() == null) {
            conta.setId(nextId); // Atribui o próximo ID disponível
            nextId++; // Incrementa o ID para a próxima conta
        }
        contas.add(conta);
        return conta;
    }

    public List<Conta> findAll() {
        return contas;
    }

    public Optional<Conta> findById(Long id) {
        return contas.stream().filter(conta -> conta.getId().equals(id)).findFirst();
    }

    public Optional<Conta> findByCpfTitular(String cpf) {
        return contas.stream().filter(conta -> conta.getCpfTitular().equals(cpf)).findFirst();
    }

    public void delete(Long id) {
        contas.removeIf(conta -> conta.getId().equals(id));
    }
}
