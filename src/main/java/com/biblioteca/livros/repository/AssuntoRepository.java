package com.biblioteca.livros.repository;

import com.biblioteca.livros.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssuntoRepository extends JpaRepository<Assunto, Integer>{

}
