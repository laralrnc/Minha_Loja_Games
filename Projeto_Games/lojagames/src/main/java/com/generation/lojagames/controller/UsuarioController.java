package com.generation.lojagames.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.generation.lojagames.model.Usuario;
import com.generation.lojagames.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuariorepository;
	
	//listar todos os dados
	@GetMapping
	public ResponseEntity <List<Usuario>> getAll()
	{
		return ResponseEntity.ok(usuariorepository.findAll());
	}
	
	//busca pelo ID
	@GetMapping("/{id}")
	public ResponseEntity <Usuario> getById(@PathVariable Long id)
	{
		return usuariorepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//buscar pelo email = user
	@GetMapping("/usuario/{email}")
	public ResponseEntity <List <Usuario>> getByEmail(@PathVariable String email)
	{
		return ResponseEntity.ok(usuariorepository.
				findAllByEmailContainingIgnoreCase(email));
	}
	
	//cadastrar usuario
	@PostMapping
	public ResponseEntity<Usuario> postUsuario (@Valid @RequestBody Usuario usuario)
	{
		
	int hoje;
	hoje = LocalDate.now().getYear();
	int idade;
	idade = usuario.getDataNascimento().getYear();
	
	//Period period1 = Period.between(localDate10, usuario.getDataNascimento());
	
		if (hoje - idade > 18 )
		{
			return ResponseEntity.status(HttpStatus.CREATED).
					body(usuariorepository.save(usuario));
			
		}
		else
		{
			return ResponseEntity.status(HttpStatus.LOCKED).build();
		}

	}
	
	//atualizar usuario
	@PutMapping
	public ResponseEntity<Usuario> putUsuario (@Valid @RequestBody Usuario usuario)
	{
		return usuariorepository.findById(usuario.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(usuariorepository.save(usuario)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id)
	{
		return usuariorepository.findById(id).map(resposta -> 
		{
			usuariorepository.deleteById(id);
				return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}
	
//	// busca usuário maiores de 18 anos
//	@GetMapping("/maioridade/{idade}")
//	public ResponseEntity<List<Usuario>> getIdadeMaiorQue(@PathVariable int idade){ 
//		return ResponseEntity.ok(usuariorepository.findByIdadeGreaterThanOrderByIdade(idade));
//	}

}
 