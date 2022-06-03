package com.br.projetosbmvc.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.model.Usuario;

@Repository
public class OperattionsRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Pessoa> findPagination(PageRequest pageRequest){
		List<Pessoa> pessoas = entityManager.createQuery("select new com.br.projetosbmvc.model.Pessoa (p.id, p.nome, p.sobrenome, p.idade) from Pessoa p")
				.setMaxResults(6).setFirstResult(0).getResultList();
		
		return pessoas;
	}
	
	
	public List<Usuario> findPaginatorUsuario(int Offset){
		List<Usuario> usuarios = entityManager.createQuery("select new com.br.projetosbmvc.model.Usuario(u.id, u.nome, u.login) from Usuario u")
				.setMaxResults(5).setFirstResult(Offset).getResultList();
		
		return usuarios;
	}
	
	
	
	public int countPages(String tableName) {
		
		String total = entityManager.createNativeQuery("select count(1) from "+tableName.toLowerCase()+";").getSingleResult().toString();
		
		int numberElement = Integer.parseInt(total);
		
		
		double porpagina = 5.0;
		
		double pagina = numberElement/porpagina;
		double resto = pagina%2;
		
		if(resto > 0 ) {
			pagina++;
		}
		
		return (int) pagina;
	
		
	}
}
