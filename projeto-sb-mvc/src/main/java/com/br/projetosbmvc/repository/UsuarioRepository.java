package com.br.projetosbmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.br.projetosbmvc.model.FotoPessoa;
import com.br.projetosbmvc.model.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.login = ?1")
	public Usuario findUsuarioByLogin(String login);
	
	@Query("select new com.br.projetosbmvc.model.Usuario(u.id, u.nome, u.login) from Usuario u")
	public List<Usuario> findUsuarioInit();
	
	@Query(value = "select count(1) > 0 from usuario where login = ?1", nativeQuery = true)
	public boolean alreadExistsByLogin(String login);
}
