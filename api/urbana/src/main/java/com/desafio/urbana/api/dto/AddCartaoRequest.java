package com.desafio.urbana.api.dto;

import com.desafio.urbana.domain.enums.TipoCartao;

public class AddCartaoRequest {

    private Long numeroCartao;
    private TipoCartao tipoCartao;
    private Boolean status;

    public Long getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(Long numeroCartao) { this.numeroCartao = numeroCartao; }

    public TipoCartao getTipoCartao() { return tipoCartao; }
    public void setTipoCartao(TipoCartao tipoCartao) { this.tipoCartao = tipoCartao; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}
