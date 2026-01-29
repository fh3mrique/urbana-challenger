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

        List<String> errors = new ArrayList<>();

        if (dto == null) {
            throw new ValidationException(List.of("corpo da requisição é obrigatório"));
        }

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            errors.add("nome é obrigatorio");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            errors.add("email é obrigatorio");
        } else {
            String email = dto.getEmail().trim();

            final Boolean EMAIL_IS_VALID = !email.contains("@") || email.startsWith("@") || email.endsWith("@");

            if (EMAIL_IS_VALID) {
                errors.add("email está em um formato inválido");
            }
        }

        if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
            errors.add("senha é obrigatorio");
        } else if (dto.getSenha().length() < 6) {
            errors.add("a senha deve ter mais de  6 caracteres");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}