package com.biblioteca.livros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.livros.model.Autor;
import com.biblioteca.livros.repository.AutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
	@Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> buscarPorId(Integer id) {
        return autorRepository.findById(id);
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public void deletar(Integer id) {
        Optional<Autor> autor = autorRepository.findById(id);

        if (autor.isEmpty()) {
            throw new RuntimeException("Autor não encontrado");
        }

        autorRepository.deleteById(id);
    }
}
