package com.desafio.urbana.service;

import com.desafio.urbana.api.dto.AddCartaoRequest;
import com.desafio.urbana.api.dto.CartaoResponse;
import com.desafio.urbana.api.exceptions.BusinessException;
import com.desafio.urbana.api.exceptions.ResourceNotFoundException;
import com.desafio.urbana.api.mapper.CartaoMapper;
import com.desafio.urbana.domain.entities.Cartao;
import com.desafio.urbana.domain.entities.Usuario;
import com.desafio.urbana.domain.repositories.CartaoRepository;
import com.desafio.urbana.domain.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoService {

    private final UsuarioRepository usuarioRepository;
    private final CartaoRepository cartaoRepository;

    public CartaoService(UsuarioRepository usuarioRepository, CartaoRepository cartaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cartaoRepository = cartaoRepository;
    }

    @Transactional
    public CartaoResponse addCardToUser(Long userId, AddCartaoRequest request) {

        if (request.getNumeroCartao() == null) {
            throw new BusinessException("numeroCartao is required");
        }
        if (request.getTipoCartao() == null) {
            throw new BusinessException("tipoCartao is required");
        }

        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (cartaoRepository.existsByNumeroCartao(request.getNumeroCartao())) {
            throw new BusinessException("Card number already exists");
        }

        if (cartaoRepository.existsByUsuarioIdAndTipoCartao(userId, request.getTipoCartao())) {
            throw new BusinessException("User already has a card of type " + request.getTipoCartao());
        }

        boolean status = (request.getStatus() != null) ? request.getStatus() : true;

        Cartao card = new Cartao();
        card.setNumeroCartao(request.getNumeroCartao());
        card.setTipoCartao(request.getTipoCartao());
        card.setStatus(status);
        card.setUsuario(user);

        Cartao saved = cartaoRepository.save(card);
        return CartaoMapper.toResponse(saved);
    }

    public List<CartaoResponse> findAllByUser(Long userId) {

        if (!usuarioRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found: " + userId);
        }

        return cartaoRepository.findAllByUsuarioIdOrderByTipoCartaoAsc(userId)
                .stream()
                .map(CartaoMapper::toResponse)
                .toList();
    }
}
