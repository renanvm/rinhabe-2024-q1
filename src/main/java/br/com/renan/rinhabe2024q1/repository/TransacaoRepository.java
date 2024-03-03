package br.com.renan.rinhabe2024q1.repository;

import br.com.renan.rinhabe2024q1.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    @Modifying
    @Query(value = """
            INSERT 
            INTO transacao (conta_id, valor, tipo, descricao, realizada_em)
            VALUES (:contaId, :valor, :tipo, :descricao , :realizadaEm)
            """,
            nativeQuery = true)
    void saveTransacao(@Param("contaId") int contaId,
                       @Param("valor") int valor,
                       @Param("tipo") String tipo,
                       @Param("descricao") String descricao,
                       @Param("realizadaEm") LocalDateTime realizadaEm);


    @Query(value = """
            SELECT * 
            FROM transacao 
            WHERE conta_id = :contaId
            ORDER BY realizada_em DESC LIMIT 10
            """,
            nativeQuery = true)
    List<Transacao> findRecentsByContaId(@Param("contaId") int contaId);


}