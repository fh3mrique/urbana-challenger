package com.desafio.urbana.api.mapper;

import com.desafio.urbana.api.dto.CartaoResponse;
import com.desafio.urbana.domain.entities.Cartao;

public class CartaoMapper {

    private CartaoMapper() {}

    public static CartaoResponse toResponse(Cartao c) {
        return new CartaoResponse(
                c.getId(),
                c.getNumeroCartao(),
                c.getStatus(),
                c.getTipoCartao()
        );
    }
}
