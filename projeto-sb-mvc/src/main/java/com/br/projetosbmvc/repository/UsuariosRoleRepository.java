package com.br.projetosbmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.br.projetosbmvc.model.UsuariosRole;

@Transactional
@Repository
public interface UsuariosRoleRepository extends JpaRepository<UsuariosRole, Long>{

	@Query(value="select count(1) > 0 from usuarios_role inner join usuario on usuarios_role.usuario_id = usuario.id\r\n"
			+ "inner join role on usuarios_role.role_id = role.id where usuario.id = ?1 and role.id = ?2", nativeQuery = true)
	public boolean alreadyExists(Long userId, Long roleId);
	
	@Query(value="select id from usuarios_role where usuario_id = ?1 and role_id = ?2", nativeQuery = true)
	public Long findIdUsuarioRoleByUserIdAndRoleId(Long userId, Long roleId);
	
}
