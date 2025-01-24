package com.biblioteca.livros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.livros.model.Autor;
import com.biblioteca.livros.service.AutorService;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

	@Autowired
    private AutorService autorService;

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable Integer id) {
        return autorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Autor> criar(@RequestBody Autor autor) {
        return ResponseEntity.ok(autorService.salvar(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> alterar(@PathVariable Integer id, @RequestBody Autor autor) {
        return autorService.buscarPorId(id)
                .map(autorExistente -> {
                    autor.setCodau(id);
                    Autor autorAtualizado = autorService.salvar(autor);
                    return ResponseEntity.ok(autorAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            autorService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).build(); 
        }
    }
	
}
