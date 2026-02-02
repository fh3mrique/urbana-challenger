package com.desafio.urbana.domain.repositories;

import com.desafio.urbana.domain.entities.Cartao;
import com.desafio.urbana.domain.enums.TipoCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    boolean existsByNumeroCartao(Long numeroCartao);

    boolean existsByUsuarioIdAndTipoCartao(Long usuarioId, TipoCartao tipoCartao);

    List<Cartao> findAllByUsuarioIdOrderByTipoCartaoAsc(Long usuarioId);

    Optional<Cartao> findByIdAndUsuarioId(Long id, Long usuarioId);

}
