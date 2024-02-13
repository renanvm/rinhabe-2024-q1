package br.com.renan.rinhabe2024q1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Conta {

    @Id
    private Integer id;
    private int limite;
    private int saldo;

    public Conta(Integer id, int limite, int saldo) {
        this.id = id;
        this.limite = limite;
        this.saldo = saldo;
    }

    public Conta() {
    }

    public Integer getId() {
        return id;
    }

    public int getLimite() {
        return limite;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}

