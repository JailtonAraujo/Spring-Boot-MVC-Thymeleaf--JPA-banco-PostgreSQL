package com.br.projetosbmvc.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.br.projetosbmvc.model.FotoPessoa;
import com.br.projetosbmvc.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	@Query("select new com.br.projetosbmvc.model.Pessoa (p.id, p.nome, p.sobrenome, p.idade) from Pessoa p where p.nome like ?1% and p.sexo like ?2%")
	public List<Pessoa> findByName(String nome, String sexo);
	
	@Query("select new com.br.projetosbmvc.model.Pessoa (p.nome, p.sobrenome, p.endereco.cidade, p.endereco.uf) from Pessoa p where p.nome like ?1% and p.sexo like ?2%")
	public List<Pessoa> findByNameReport(String nome, String sexo);
	
	@Query("select new com.br.projetosbmvc.model.FotoPessoa (f.id, f.nameImage, f.typeImage, f.fotoUser) from FotoPessoa f where f.pessoa.id = ?1")
	public FotoPessoa findFoto(Long userId);
	
	default List<Pessoa> findPaginator (){
		List <Pessoa> pessoas = new ArrayList<Pessoa>();
		return pessoas;
	}
	
	
	default Page<Pessoa> findPessoaByNamePagitanor(String nome, Pageable pageable){
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
				.withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Pessoa> example = Example.of(pessoa, exampleMatcher);
		
		Page<Pessoa> Pessoas = findAll(example,pageable);
		
		return Pessoas;
		
	}
	
	default Page<Pessoa> findPessoaByNameAndSexoPagitanor(String nome, String sexopesquisa, Pageable pageable){
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		pessoa.setSexo(sexopesquisa);
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
				.withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Pessoa> example = Example.of(pessoa, exampleMatcher);
		
		Page<Pessoa> Pessoas = findAll(example,pageable);
		
		return Pessoas;
		
	}
	
}
