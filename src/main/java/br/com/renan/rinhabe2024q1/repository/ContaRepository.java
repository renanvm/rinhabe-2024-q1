package br.com.renan.rinhabe2024q1.repository;

import br.com.renan.rinhabe2024q1.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
}