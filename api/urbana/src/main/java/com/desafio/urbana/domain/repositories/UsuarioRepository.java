package com.desafio.urbana.domain.repositories;

import com.desafio.urbana.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
}
