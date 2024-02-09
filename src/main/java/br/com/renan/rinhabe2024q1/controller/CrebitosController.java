package br.com.renan.rinhabe2024q1.controller;

import br.com.renan.rinhabe2024q1.dto.Conta;
import br.com.renan.rinhabe2024q1.dto.Transacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{id}")
public class CrebitosController {

    @GetMapping("/transacoes")
    public Conta transacoes(@RequestBody Transacao transacao) {

        var conta = new Conta(0,0);

        return conta;
    }

    @PostMapping("/extrato")
    public ResponseEntity<Void> extrato() {

        return ResponseEntity.ok().build();
    }
}
