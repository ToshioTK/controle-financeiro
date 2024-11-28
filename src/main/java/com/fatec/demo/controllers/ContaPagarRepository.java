package com.fatec.demo.controllers;

import com.fatec.demo.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
}
