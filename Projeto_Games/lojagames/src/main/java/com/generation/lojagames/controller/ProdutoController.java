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

import com.generation.lojagames.model.Produto;
import com.generation.lojagames.repository.CategoriaRepository;
import com.generation.lojagames.repository.ProdutoRepository;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtorepository;
	private CategoriaRepository categoriarepository;
	
	@GetMapping 
	public ResponseEntity <List <Produto>> getAll()
	{
		return ResponseEntity.ok(produtorepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Produto> getById(@PathVariable Long id)
	{
		return produtorepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity <List <Produto>> getByTitulo(@PathVariable String nome)
	{
		return ResponseEntity.ok(produtorepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Produto> postPostagem (@Valid @RequestBody Produto produto)
	{

		return ResponseEntity.status(HttpStatus.CREATED).
				body(produtorepository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> putPostagem (@Valid @RequestBody Produto produto)
	{
		return produtorepository.findById(produto.getId())
		.map(resposta -> ResponseEntity.status(HttpStatus.OK)
		.body(produtorepository.save(produto)))
		.orElse(ResponseEntity.notFound().build()); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePostagem(@PathVariable Long id)
	{
		return produtorepository.findById(id).map(resposta -> 
		{
			produtorepository.deleteById(id);
				return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}
	
	
	
}
