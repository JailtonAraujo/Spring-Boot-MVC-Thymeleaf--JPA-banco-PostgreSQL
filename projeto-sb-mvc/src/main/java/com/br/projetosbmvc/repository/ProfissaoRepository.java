package com.br.projetosbmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.br.projetosbmvc.model.Profissao;

@Transactional
@Repository
public interface ProfissaoRepository extends JpaRepository<Profissao, Long>{

}
