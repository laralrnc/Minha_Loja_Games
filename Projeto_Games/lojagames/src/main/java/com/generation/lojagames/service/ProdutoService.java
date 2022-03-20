package com.generation.lojagames.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.generation.lojagames.model.Produto;
import com.generation.lojagames.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	public ProdutoRepository produtoRepository;
	
	public Optional<Produto> curtir(Long id)
	{
		if(produtoRepository.existsById(id))
		{
			Produto produto = produtoRepository.findById(id).get();
			produto.setCurtir(produto.getCurtir() + 1);
			return Optional.of(produtoRepository.save(produto));
		}
		
		return Optional.empty();
	}

}
