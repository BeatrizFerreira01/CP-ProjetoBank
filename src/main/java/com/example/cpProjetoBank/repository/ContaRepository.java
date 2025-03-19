package com.example.cpProjetoBank.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.cpProjetoBank.model.Conta;

@Repository
public class ContaRepository {
    private List<Conta> contas = new ArrayList<>();

    public Conta save(Conta conta) {
        if (conta.getId() == null) {
            conta.setId((long) (contas.size() + 1)); // Gera um ID automático para novas contas
            contas.add(conta);
        } else {
            // Atualiza a conta existente na lista, se ela já existir
            contas.replaceAll(c -> c.getId().equals(conta.getId()) ? conta : c);
        }
        return conta;
    }

    public List<Conta> findAll() {
        return contas;
    }

    public Optional<Conta> findById(Long id) {
        return contas.stream().filter(conta -> conta.getId().equals(id)).findFirst();
    }

    public Optional<Conta> findByCpfTitular(String cpf) {
        return contas.stream()
                     .filter(conta -> conta.getCpfTitular().equals(cpf))
                     .findFirst();
    }    

    public boolean delete(Long id) {
        return contas.removeIf(conta -> conta.getId().equals(id));
    }
}
