package com.biblioteca.livros.repository;

import com.biblioteca.livros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer>{

}
