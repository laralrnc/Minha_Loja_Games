package com.generation.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.lojagames.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
	
	List <Usuario> findAllByEmailContainingIgnoreCase(String email);

}
