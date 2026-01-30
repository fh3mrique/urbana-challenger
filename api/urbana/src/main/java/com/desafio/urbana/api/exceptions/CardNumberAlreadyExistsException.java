package com.desafio.urbana.api.exceptions;

public class CardNumberAlreadyExistsException extends RuntimeException {
    public CardNumberAlreadyExistsException(Long numeroCartao) {
        super("numeroCartao já registrado: " + numeroCartao);
    }
}
