package com.example.cpProjetoBank.controller;

import com.example.cpProjetoBank.model.Conta;
import com.example.cpProjetoBank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Conta> criarConta(@RequestBody @Valid Conta conta) {
        Conta novaConta = contaService.cadastrarConta(conta);
        return ResponseEntity.ok(novaConta);
    }    

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listarContas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
        Optional<Conta> conta = contaService.buscarPorId(id);
        return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Conta> buscarContaPorCpf(@PathVariable String cpf) {
        Optional<Conta> conta = contaService.buscarPorCpf(cpf);
        return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> encerrarConta(@PathVariable Long id) {
        contaService.encerrarConta(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<Conta> realizarDeposito(@PathVariable Long id, @RequestParam double valor) {
        Conta conta = contaService.realizarDeposito(id, valor);
        return ResponseEntity.ok(conta);
    }

    @PostMapping("/{id}/saque")
    public ResponseEntity<Conta> realizarSaque(@PathVariable Long id, @RequestParam double valor) {
        Conta conta = contaService.realizarSaque(id, valor);
        return ResponseEntity.ok(conta);
    }

    @PostMapping("/{origemId}/transferencias/{destinoId}")
    public ResponseEntity<Conta> realizarPix(@PathVariable Long origemId, @PathVariable Long destinoId, @RequestParam double valor) {
        Conta conta = contaService.realizarPix(origemId, destinoId, valor);
        return ResponseEntity.ok(conta);
    }
}
