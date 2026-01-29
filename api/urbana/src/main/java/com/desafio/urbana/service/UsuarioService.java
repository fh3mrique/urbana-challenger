package com.desafio.urbana.service;

import com.desafio.urbana.api.dto.UsuarioCreateRequest;
import com.desafio.urbana.api.dto.UsuarioResponse;
import com.desafio.urbana.api.exceptions.EmailAlreadyExistsException;
import com.desafio.urbana.api.exceptions.ValidationException;
import com.desafio.urbana.api.mapper.UsuarioMapper;
import com.desafio.urbana.domain.entities.Usuario;
import com.desafio.urbana.domain.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,  PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }


    @Transactional
    public UsuarioResponse insert(UsuarioCreateRequest dto) {

        validateCreate(dto);

        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        if (usuarioRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException(normalizedEmail);
        }

        String encryptedPassword = passwordEncoder.encode(dto.getSenha());

        Usuario entity = UsuarioMapper.toEntity(dto, encryptedPassword);
        entity.setEmail(normalizedEmail);

        entity = usuarioRepository.save(entity);

        return UsuarioMapper.toResponse(entity);
    }


    private void validateCreate(UsuarioCreateRequest dto) {

        if (dto == null) {
            throw new ValidationException(List.of("corpo da requisição é obrigatório"));
        }

        List<String> errors = new ArrayList<>();

        validateNome(dto.getNome(), errors);
        validateEmail(dto.getEmail(), errors);
        validateSenha(dto.getSenha(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }


    private void validateNome(String nome, List<String> errors) {

        boolean NAME_IS_EMPTY = nome == null || nome.trim().isEmpty();

        if (NAME_IS_EMPTY) {
            errors.add("nome é obrigatório");
        }
    }

    private void validateEmail(String email, List<String> errors) {

        boolean EMAIL_IS_EMPTY = email == null || email.trim().isEmpty();

        if (EMAIL_IS_EMPTY) {
            errors.add("email é obrigatório");
            return;
        }

        String normalizedEmail = email.trim();

        boolean EMAIL_HAS_NO_AT = !normalizedEmail.contains("@");
        boolean EMAIL_STARTS_WITH_AT = normalizedEmail.startsWith("@");
        boolean EMAIL_ENDS_WITH_AT = normalizedEmail.endsWith("@");

        boolean EMAIL_IS_INVALID =
                EMAIL_HAS_NO_AT ||
                        EMAIL_STARTS_WITH_AT ||
                        EMAIL_ENDS_WITH_AT;

        if (EMAIL_IS_INVALID) {
            errors.add("email está em um formato inválido");
        }
    }

    private void validateSenha(String senha, List<String> errors) {

        boolean PASSWORD_IS_EMPTY = senha == null || senha.trim().isEmpty();

        if (PASSWORD_IS_EMPTY) {
            errors.add("senha é obrigatória");
            return;
        }

        boolean PASSWORD_TOO_SHORT = senha.length() < 6;

        if (PASSWORD_TOO_SHORT) {
            errors.add("a senha deve ter no mínimo 6 caracteres");
        }
    }



}