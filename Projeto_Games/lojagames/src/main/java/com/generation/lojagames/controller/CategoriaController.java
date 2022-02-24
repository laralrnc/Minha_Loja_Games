package com.generation.lojagames.controller;

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

import com.generation.lojagames.model.Categoria;
import com.generation.lojagames.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {
	
	@Autowired
	public CategoriaRepository categoriarepository;
	
	//listar todos
	@GetMapping 
	public ResponseEntity <List <Categoria>> getAll()
	{
		return ResponseEntity.ok(categoriarepository.findAll());
	}
	
	//cadastrar
	@PostMapping
	public  ResponseEntity <Categoria> postCategoria(@Valid @RequestBody Categoria categoria)
	{
		return ResponseEntity.status(HttpStatus.CREATED).
				body(categoriarepository.save(categoria));
	}
	
	//buscar por ID
	@GetMapping("/{id}")
	public ResponseEntity <Categoria> getById(@PathVariable Long id)
	{
		return categoriarepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//buscar por nome
	//("/descricao/{descricao}") == ("/nomeColuna/{Valor da variavel}")
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity <List <Categoria>> getByDescricao(@PathVariable String descricao)
	{
		return ResponseEntity.ok(categoriarepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	//atualização
	@PutMapping
	public ResponseEntity<Categoria> putCategoria(@Valid @RequestBody Categoria categoria)
	{
		return categoriarepository.findById(categoria.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
				.body(categoriarepository.save(categoria)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoria(@PathVariable Long id)
	{
		return categoriarepository.findById(id).map(resposta -> 
		{
			categoriarepository.deleteById(id);
				return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}
	

}
