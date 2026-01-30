package com.desafio.urbana.api.dto;

import com.desafio.urbana.domain.enums.TipoCartao;

public class CartaoResponse {

    private Long id;
    private Long numeroCartao;
    private Boolean status;
    private TipoCartao tipoCartao;

    public CartaoResponse() {}

    public CartaoResponse(Long id, Long numeroCartao, Boolean status, TipoCartao tipoCartao) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.status = status;
        this.tipoCartao = tipoCartao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(Long numeroCartao) { this.numeroCartao = numeroCartao; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public TipoCartao getTipoCartao() { return tipoCartao; }
    public void setTipoCartao(TipoCartao tipoCartao) { this.tipoCartao = tipoCartao; }
}
