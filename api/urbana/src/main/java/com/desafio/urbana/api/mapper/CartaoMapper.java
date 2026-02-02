package com.desafio.urbana.api.mapper;

import com.desafio.urbana.api.dto.CartaoResponse;
import com.desafio.urbana.api.dto.AddCartaoRequest;
import com.desafio.urbana.domain.entities.Cartao;
import com.desafio.urbana.domain.entities.Usuario;
import com.desafio.urbana.domain.enums.TipoCartao;

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

    public static Cartao toEntity(AddCartaoRequest dto, Usuario usuario) {


        boolean status;

        boolean IS_SPECIAL_TYPE =
                dto.getTipoCartao() == TipoCartao.TRABALHADOR ||
                        dto.getTipoCartao() == TipoCartao.ESTUDANTE;

        if (IS_SPECIAL_TYPE) {
            status = false;
        } else {
            status = dto.getStatus() == null || dto.getStatus();
        }

        Cartao entity = new Cartao();
        entity.setNumeroCartao(dto.getNumeroCartao());
        entity.setTipoCartao(dto.getTipoCartao());
        entity.setStatus(status);
        entity.setUsuario(usuario);

        return entity;
    }
}
