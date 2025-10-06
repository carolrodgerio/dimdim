package com.fiap.dimdim.controller;

import com.fiap.dimdim.model.Conta;
import com.fiap.dimdim.repository.ClienteRepository;
import com.fiap.dimdim.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Conta> getAllContas() {
        return contaRepository.findAll();
    }

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Conta> createConta(@PathVariable Long clienteId, @RequestBody Conta conta) {
        return clienteRepository.findById(clienteId)
                .map(cliente -> {
                    conta.setCliente(cliente);
                    Conta savedConta = contaRepository.save(conta);
                    return new ResponseEntity<>(savedConta, HttpStatus.CREATED);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConta(@PathVariable Long id) {
        return contaRepository.findById(id)
                .map(conta -> {
                    contaRepository.delete(conta);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}