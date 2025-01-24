package com.biblioteca.livros.repository;

import com.biblioteca.livros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Integer>{

}
