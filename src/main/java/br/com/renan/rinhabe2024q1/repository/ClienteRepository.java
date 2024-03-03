package br.com.renan.rinhabe2024q1.repository;

import br.com.renan.rinhabe2024q1.entity.Cliente;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
            SELECT c 
            FROM Cliente c
            WHERE c.id = :clienteId
            """)
    Cliente findByClienteId(@Param("clienteId") int clienteId);

    @Modifying
    @Query(value = """
            UPDATE cliente 
            SET saldo = saldo + :valor
            WHERE id = :clienteId
            """,
            nativeQuery = true)
    void updateSaldo(@Param("valor") int valor,
                     @Param("clienteId") int clienteId);
}