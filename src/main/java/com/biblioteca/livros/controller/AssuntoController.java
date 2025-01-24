package com.biblioteca.livros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.livros.model.Assunto;
import com.biblioteca.livros.service.AssuntoService;

import java.util.List;

@RestController
@RequestMapping("/api/assuntos")
public class AssuntoController {

	@Autowired
    private AssuntoService assuntoService;

    @GetMapping
    public ResponseEntity<List<Assunto>> listarTodos() {
        return ResponseEntity.ok(assuntoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assunto> buscarPorId(@PathVariable Integer id) {
        return assuntoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Assunto> criar(@RequestBody Assunto assunto) {
        return ResponseEntity.ok(assuntoService.salvar(assunto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assunto> alterar(@PathVariable Integer id, @RequestBody Assunto assunto) {
        return assuntoService.buscarPorId(id)
                .map(assuntoExistente -> {
                    assunto.setCodas(id);
                    Assunto assuntoAtualizado = assuntoService.salvar(assunto);
                    return ResponseEntity.ok(assuntoAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            assuntoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).build(); // Retorna 404 Not Found
        }
    }
	
}
