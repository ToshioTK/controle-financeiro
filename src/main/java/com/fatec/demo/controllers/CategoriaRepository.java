package com.fatec.demo.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fatec.demo.entities.Categoria;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByDescricao(String descricao);
}
