package com.fatec.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria", uniqueConstraints = @UniqueConstraint(columnNames = "descricao"))
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255, unique = true)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = true;

    // Construtor padrão
    public Categoria() {}

    public Categoria(Long id, String descricao, Boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
