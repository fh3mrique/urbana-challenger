package com.desafio.urbana.api.resources;


import com.desafio.urbana.api.dto.AddCartaoRequest;
import com.desafio.urbana.api.dto.CartaoResponse;
import com.desafio.urbana.service.CartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class CartaoResources {

    private final CartaoService cartaoService;

    public CartaoResources(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping("/{userId}/cartoes")
    public ResponseEntity<CartaoResponse> addCardToUser(
            @PathVariable Long userId,
            @RequestBody AddCartaoRequest request
    ) {
        CartaoResponse created = cartaoService.addCardToUser(userId, request);

        return ResponseEntity
                .created(URI.create("/usuarios/" + userId + "/cartoes/" + created.getId()))
                .body(created);
    }


    @GetMapping("/{userId}/cartoes")
    public ResponseEntity<List<CartaoResponse>> findAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cartaoService.findAllByUser(userId));
    }
}
