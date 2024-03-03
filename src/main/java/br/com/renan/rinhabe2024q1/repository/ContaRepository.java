package br.com.renan.rinhabe2024q1.repository;

import br.com.renan.rinhabe2024q1.entity.Conta;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
            SELECT c 
            FROM Conta c
            WHERE c.id = :contaId
            """)
    Conta findByContaId(@Param("contaId") int contaId);

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