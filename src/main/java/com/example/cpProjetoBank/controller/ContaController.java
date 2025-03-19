package com.example.cpProjetoBank.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cpProjetoBank.model.Conta;
import com.example.cpProjetoBank.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping("/")
    public String infoProjeto() {
        return "Projeto Bank - Criado por Beatriz e Bárbara";
    }

    @PostMapping
    public ResponseEntity<?> criarConta(@RequestBody @Valid Conta conta) {
        try {
            // Verifica manualmente requisitos adicionais que não estão cobertos pelo @Valid
            if (conta.getNumero() == null || conta.getNumero().trim().isEmpty()) {
                return ResponseEntity.status(400).body("Erro: O número da conta é obrigatório.");
            }
    
            if (conta.getAgencia() == null || conta.getAgencia().trim().isEmpty()) {
                return ResponseEntity.status(400).body("Erro: A agência é obrigatória.");
            }
    
            if (conta.getNomeTitular() == null || conta.getNomeTitular().trim().isEmpty()) {
                return ResponseEntity.status(400).body("Erro: O nome do titular é obrigatório.");
            }
    
            if (conta.getCpfTitular() == null || conta.getCpfTitular().trim().isEmpty()) {
                return ResponseEntity.status(400).body("Erro: O CPF do titular é obrigatório.");
            }
    
            if (conta.getDataAbertura() == null || conta.getDataAbertura().isAfter(LocalDate.now())) {
                return ResponseEntity.status(400).body("Erro: A data de abertura deve ser válida e não pode estar no futuro.");
            }
    
            if (conta.getSaldo() < 0) {
                return ResponseEntity.status(400).body("Erro: O saldo inicial não pode ser negativo.");
            }
    
            if (conta.getTipo() == null || conta.getTipo().trim().isEmpty()) {
                return ResponseEntity.status(400).body("Erro: O tipo da conta é obrigatório (corrente, poupança ou salário).");
            }
    
            // Chama o serviço para cadastrar a conta
            Conta novaConta = contaService.cadastrarConta(conta);
    
            if (novaConta != null) {
                return ResponseEntity.ok(novaConta);
            } else {
                return ResponseEntity.status(400).body("Erro: Não foi possível criar a conta. Verifique os dados fornecidos.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro inesperado ao criar conta.");
        }
    }    

    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaService.listarContas();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarContaPorId(@PathVariable Long id) {
        try {
            Optional<Conta> conta = contaService.buscarPorId(id);
            if (conta.isPresent()) {
                Conta c = conta.get();
                if (!c.isAtiva()) {
                    return ResponseEntity.status(400).body("Erro: A conta está inativa.");
                }
                return ResponseEntity.ok(c);
            } else {
                return ResponseEntity.status(404).body("Erro: Conta não encontrada.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao buscar conta.");
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> buscarContaPorCpf(@PathVariable String cpf) {
        Optional<Conta> conta = contaService.buscarPorCpf(cpf);

        if (conta.isPresent()) {
            return ResponseEntity.ok(conta.get());
        } else {
            return ResponseEntity.status(404).body("Conta com CPF " + cpf + " não encontrada.");
        }
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<?> realizarDeposito(@PathVariable Long id, @RequestParam double valor) {
        try {
            Conta conta = contaService.realizarDeposito(id, valor);
            if (conta != null) {
                return ResponseEntity.ok(conta);
            } else {
                return ResponseEntity.status(400).body("Depósito inválido ou conta não encontrada.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao realizar depósito.");
        }
    }

    @PostMapping("/{id}/saque")
    public ResponseEntity<?> realizarSaque(@PathVariable Long id, @RequestParam double valor) {
        try {
            Conta conta = contaService.realizarSaque(id, valor);
            if (conta != null) {
                return ResponseEntity.ok(conta);
            } else {
                return ResponseEntity.status(400).body("Erro: Saque inválido ou saldo insuficiente.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao realizar saque.");
        }
    }

    @PostMapping("/{origemId}/transferencias/{destinoId}")
    public ResponseEntity<?> realizarPix(@PathVariable Long origemId, @PathVariable Long destinoId, @RequestParam double valor) {
        try {
            boolean sucesso = contaService.realizarPix(origemId, destinoId, valor);
            if (sucesso) {
                return ResponseEntity.ok("Transferência PIX realizada com sucesso!");
            } else {
                return ResponseEntity.status(400).body("Erro: Transferência inválida, saldo insuficiente ou conta inativa.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao realizar PIX.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> encerrarConta(@PathVariable Long id) {
        try {
            boolean encerrada = contaService.encerrarConta(id);
            if (encerrada) {
                return ResponseEntity.ok("Conta com ID " + id + " foi encerrada com sucesso.");
            } else {
                return ResponseEntity.status(400).body("Erro: Conta com ID " + id + " já está inativa ou não existe.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao encerrar conta.");
        }
    }
}
