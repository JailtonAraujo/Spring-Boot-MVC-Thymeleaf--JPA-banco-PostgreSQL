package com.br.projetosbmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.br.projetosbmvc.model.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends JpaRepository<Telefone, Long>{

	@Query(value = "select new com.br.projetosbmvc.model.Telefone (fone.id, fone.numero, fone.tipo) from Telefone fone where fone.pessoa.id = ?1 ")
	public List<Telefone> findAllFonesByPersonName(Long personId);
}
