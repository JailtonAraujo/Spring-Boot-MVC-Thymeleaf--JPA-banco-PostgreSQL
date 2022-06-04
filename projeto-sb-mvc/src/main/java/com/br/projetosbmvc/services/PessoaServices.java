package com.br.projetosbmvc.services;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.projetosbmvc.dto.PessoaDTO;
import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.repository.PessoaRepository;

@Service
public class PessoaServices implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PessoaRepository pessoaRepository; 
	
	public Page<PessoaDTO> findPaginator(){
		
		Page<Pessoa> pessoas = pessoaRepository.findAll(PageRequest.of(0, 5));
		List<PessoaDTO> pessoasDto = pessoas.stream().map(obj -> new PessoaDTO(obj)).collect(Collectors.toList());
		
		Page<PessoaDTO> pageDTO = new PageImpl<PessoaDTO>(pessoasDto, pessoas.getPageable(), pessoas.getTotalElements());
		
		return pageDTO;
	}
	
	public Page<PessoaDTO> findPessoaByNameAndSexoPagitanor(String nomepesquisa, String sexopesquisa, Pageable pageable){
		
		Page<Pessoa> pessoas = pessoaRepository.findPessoaByNameAndSexoPagitanor(nomepesquisa, sexopesquisa, pageable);
		List<PessoaDTO> pessoasDTO = pessoas.stream().map(obj -> new PessoaDTO(obj)).collect(Collectors.toList());
		
		Page <PessoaDTO> pageDTO = new PageImpl<PessoaDTO>(pessoasDTO, pessoas.getPageable(), pessoas.getTotalElements());
		
		return pageDTO;
		
		
	}

}
