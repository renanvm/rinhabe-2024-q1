package br.com.renan.rinhabe2024q1.repository;

import br.com.renan.rinhabe2024q1.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

    @Query(value = """
            SELECT * 
            FROM conta 
            WHERE id = :contaId
            """,
            nativeQuery = true)
    Conta findByContaId(@Param("contaId") int contaId);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE conta 
            SET saldo = saldo + :valor
            WHERE id = :contaId
            """,
            nativeQuery = true)
    void updateSaldo(@Param("valor") int valor,
                     @Param("contaId") int contaId);
}