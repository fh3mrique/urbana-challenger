package com.desafio.urbana.api.resources;

import com.desafio.urbana.api.dto.UsuarioResponse;
import com.desafio.urbana.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioResources {

    private final UsuarioService usuarioService;

    public UsuarioResources(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {

        List<UsuarioResponse> usuarioResponseList = usuarioService.findAll();

        return ResponseEntity.ok().body(usuarioResponseList);

    }
}
