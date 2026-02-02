package com.desafio.urbana.service;

import com.desafio.urbana.api.dto.AddCartaoRequest;
import com.desafio.urbana.api.dto.CartaoResponse;
import com.desafio.urbana.api.exceptions.BusinessException;
import com.desafio.urbana.api.exceptions.ResourceNotFoundException;
import com.desafio.urbana.api.mapper.CartaoMapper;
import com.desafio.urbana.domain.entities.Cartao;
import com.desafio.urbana.domain.entities.Usuario;
import com.desafio.urbana.domain.enums.TipoCartao;
import com.desafio.urbana.domain.repositories.CartaoRepository;
import com.desafio.urbana.domain.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        validateCreate(request);

        Usuario user = findUserOrThrow(userId);

        validateNumeroCartaoUnique(request.getNumeroCartao());
        validateTipoCartaoUniqueForUser(userId, request.getTipoCartao());

        Cartao entity = CartaoMapper.toEntity(request, user);

        entity = cartaoRepository.save(entity);

        return CartaoMapper.toResponse(entity);
    }

    @Transactional
    public List<CartaoResponse> findAllByUser(Long userId) {

        ensureUserExists(userId);

        return cartaoRepository.findAllByUsuarioIdOrderByTipoCartaoAsc(userId)
                .stream()
                .map(CartaoMapper::toResponse)
                .toList();
    }

    private void validateCreate(AddCartaoRequest request) {

        if (request == null) {
            throw new BusinessException("corpo da requisição é obrigatório");
        }

        List<String> errors = new ArrayList<>();

        validateNumeroCartao(request.getNumeroCartao(), errors);
        validateTipoCartao(request.getTipoCartao(), errors);

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join("; ", errors));
        }
    }

    private void validateNumeroCartao(Long numeroCartao, List<String> errors) {

        boolean CARD_NUMBER_IS_EMPTY = numeroCartao == null;

        if (CARD_NUMBER_IS_EMPTY) {
            errors.add("numeroCartao é obrigatório");
        }
    }

    private void validateTipoCartao(Object tipoCartao, List<String> errors) {
        boolean CARD_TYPE_IS_NULL = tipoCartao == null;

        if (CARD_TYPE_IS_NULL) {
            errors.add("tipoCartao é obrigatório");
        }
    }

    private Usuario findUserOrThrow(Long userId) {
        return usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado " + userId));
    }

    private void ensureUserExists(Long userId) {

        boolean USER_DOES_NOT_EXIST = !usuarioRepository.existsById(userId);

        if (USER_DOES_NOT_EXIST) {
            throw new ResourceNotFoundException("Usuário não encontrado: " + userId);
        }
    }

    private void validateNumeroCartaoUnique(Long numeroCartao) {

        boolean CARD_NUMBER_ALREADY_EXISTS = cartaoRepository.existsByNumeroCartao(numeroCartao);

        if (CARD_NUMBER_ALREADY_EXISTS) {
            throw new BusinessException("Já existe um cartão com esse número");
        }
    }

    private void validateTipoCartaoUniqueForUser(Long userId, TipoCartao tipoCartao) {

        boolean USER_ALREADY_HAS_THIS_TYPE =
                cartaoRepository.existsByUsuarioIdAndTipoCartao(userId, tipoCartao);

        if (USER_ALREADY_HAS_THIS_TYPE) {
            throw new BusinessException(
                    "O usuário já possui um cartão desse mesmo tipo" + tipoCartao
            );
        }
    }

}
