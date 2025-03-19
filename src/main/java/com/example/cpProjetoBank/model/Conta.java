package com.example.cpProjetoBank.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class Conta {
    private Long id;
    
    @NotNull(message = "Número da conta é obrigatório")
    private String numero;
    
    @NotNull(message = "Agência é obrigatória")
    private String agencia;
    
    @NotNull(message = "Nome do titular é obrigatório")
    private String nomeTitular;
    
    @NotNull(message = "CPF do titular é obrigatório")
    private String cpfTitular;
    
    @NotNull(message = "Data de abertura não pode ser no futuro")
    private LocalDate dataAbertura;
    
    @Positive(message = "Saldo inicial não pode ser negativo")
    private double saldo;
    
    private boolean ativa;
    
    @NotNull(message = "Tipo da conta é obrigatório")
    private String tipo; // corrente, poupança, salário
    
    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
