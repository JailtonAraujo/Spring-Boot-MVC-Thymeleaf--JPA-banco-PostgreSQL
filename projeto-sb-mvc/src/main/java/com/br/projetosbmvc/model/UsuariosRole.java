package com.br.projetosbmvc.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

@SuppressWarnings("deprecation")
@Table(name = "usuarios_role")
@Entity
public class UsuariosRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@JoinColumn(name = "usuario_id")
	@ForeignKey(name = "fkopcaagbsri62yny3hlxui91vb")
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Usuario usuario = new Usuario();

	@JoinColumn(name = "role_id")
	@ForeignKey(name = "fkb4lgjns7jnrvtimlocbhgu9eu")
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Role role = new Role();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
