package com.desafio.urbana.api.mapper;

import com.desafio.urbana.api.dto.UsuarioCreateRequest;
import com.desafio.urbana.api.dto.UsuarioResponse;
import com.desafio.urbana.domain.entities.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static Usuario toEntity(UsuarioCreateRequest dto, String encryptedPassword) {
        Usuario entity = new Usuario();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(encryptedPassword);
        return entity;
    }

    public static UsuarioResponse toResponse(Usuario entity) {
        return new UsuarioResponse(entity.getId(), entity.getNome(), entity.getEmail());
    }
}
