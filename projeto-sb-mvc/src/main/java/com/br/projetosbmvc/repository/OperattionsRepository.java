package com.br.projetosbmvc.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.br.projetosbmvc.model.Pessoa;

@Repository
public class OperattionsRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Pessoa> findPagination(){
		List<Pessoa> pessoas = entityManager.createQuery("select new com.br.projetosbmvc.model.Pessoa (p.id, p.nome, p.sobrenome, p.idade) from Pessoa p")
				.setMaxResults(6).setFirstResult(0).getResultList();
		
		return pessoas;
	}
}
