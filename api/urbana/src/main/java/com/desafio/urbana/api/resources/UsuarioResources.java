package com.desafio.urbana.api.resources;

import com.desafio.urbana.api.dto.UsuarioCreateRequest;
import com.desafio.urbana.api.dto.UsuarioResponse;
import com.desafio.urbana.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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


    @PostMapping
    public ResponseEntity<UsuarioResponse> insert(
            @RequestBody UsuarioCreateRequest request) {

        UsuarioResponse response = usuarioService.insert(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(
            @PathVariable Long id,
            @RequestBody UsuarioCreateRequest dto
    ) {
        UsuarioResponse response = usuarioService.update(id, dto);
        return ResponseEntity.ok(response);
    }

}
