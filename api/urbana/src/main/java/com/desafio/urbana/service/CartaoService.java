package com.desafio.urbana.service;

import com.desafio.urbana.api.dto.AddCartaoRequest;
import com.desafio.urbana.api.dto.CartaoResponse;
import com.desafio.urbana.api.dto.UpdateCartaoStatusRequest;
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

        cartaoRepository.save(entity);

        return CartaoMapper.toResponse(entity);
    }

    @Transactional
    public List<CartaoResponse> findAllByUser(Long userId) {

        findUserOrThrow(userId);

        return cartaoRepository.findAllByUsuarioIdOrderByTipoCartaoAsc(userId)
                .stream()
                .map(CartaoMapper::toResponse)
                .toList();
    }

    @Transactional
    public CartaoResponse updateCardStatus(Long userId,
                                           Long cartaoId,
                                           UpdateCartaoStatusRequest request) {

        validateUpdateStatus(request);

        findUserOrThrow(userId);

        Cartao entity = cartaoRepository.findByIdAndUsuarioId(cartaoId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cartão não encontrado: " + cartaoId + " para o usuário: " + userId
                ));

        Boolean novoStatus = request.getStatus();

        boolean STATUS_IS_ALREADY_THE_SAME = novoStatus.equals(entity.getStatus());
        if (STATUS_IS_ALREADY_THE_SAME) {
            throw new BusinessException(
                    "O cartão já está " + (novoStatus ? "ativo" : "inativo")
            );
        }

        entity.setStatus(novoStatus);

        cartaoRepository.save(entity);

        return CartaoMapper.toResponse(entity);
    }

    private void validateCreate(AddCartaoRequest request) {

        validateRequestBody(request);

        List<String> errors = new ArrayList<>();

        validateNumeroCartao(request.getNumeroCartao(), errors);
        validateTipoCartao(request.getTipoCartao(), errors);

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join("; ", errors));
        }
    }

    private void validateUpdateStatus(UpdateCartaoStatusRequest request) {

        validateRequestBody(request);

        if (request.getStatus() == null) {
            throw new BusinessException("status é obrigatório");
        }
    }

    private void validateRequestBody(Object request) {
        if (request == null) {
            throw new BusinessException("corpo da requisição é obrigatório");
        }
    }

    private void validateNumeroCartao(Long numeroCartao, List<String> errors) {
        if (numeroCartao == null) {
            errors.add("numeroCartao é obrigatório");
        }
    }

    private void validateTipoCartao(TipoCartao tipoCartao, List<String> errors) {
        if (tipoCartao == null) {
            errors.add("tipoCartao é obrigatório");
        }
    }

    private Usuario findUserOrThrow(Long userId) {
        return usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado: " + userId
                ));
    }

    private void validateNumeroCartaoUnique(Long numeroCartao) {
        if (cartaoRepository.existsByNumeroCartao(numeroCartao)) {
            throw new BusinessException("Já existe um cartão com esse número");
        }
    }

    private void validateTipoCartaoUniqueForUser(Long userId, TipoCartao tipoCartao) {

        boolean USER_ALREADY_HAS_THIS_TYPE = cartaoRepository.existsByUsuarioIdAndTipoCartao(userId, tipoCartao);

        if (USER_ALREADY_HAS_THIS_TYPE) {
            throw new BusinessException(
                    "O usuário já possui um cartão desse mesmo tipo" + tipoCartao
            );
        }
    }
}
