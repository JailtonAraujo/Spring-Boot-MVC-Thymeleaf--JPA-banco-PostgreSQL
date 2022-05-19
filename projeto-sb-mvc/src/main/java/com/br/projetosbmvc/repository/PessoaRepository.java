package com.br.projetosbmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.br.projetosbmvc.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	@Query("select new com.br.projetosbmvc.model.Pessoa (p.id, p.nome, p.sobrenome, p.idade) from Pessoa p where p.nome like ?1% and p.sexo like ?2%")
	public List<Pessoa> findByName(String nome, String sexo);
	
	@Query("select new com.br.projetosbmvc.model.Pessoa (p.nome, p.sobrenome, p.endereco.cidade, p.endereco.uf) from Pessoa p where p.nome like ?1% and p.sexo like ?2%")
	public List<Pessoa> findByNameReport(String nome, String sexo);
}
