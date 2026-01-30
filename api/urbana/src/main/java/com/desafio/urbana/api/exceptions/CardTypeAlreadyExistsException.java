package com.desafio.urbana.api.exceptions;

import com.desafio.urbana.domain.enums.TipoCartao;

public class CardTypeAlreadyExistsException extends RuntimeException {
    public CardTypeAlreadyExistsException(TipoCartao tipoCartao) {
        super("usuário já possui cartão do tipo: " + tipoCartao);
    }
}
