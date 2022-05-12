package com.br.projetosbmvc.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.google.gson.annotations.Expose;

@SuppressWarnings("deprecation")
@Table(name = "telefone")
@Entity
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Expose
	private String numero;

	@Expose
	private String tipo;

	@Expose
	@JoinColumn(name = "pessoa_id")
	@ForeignKey(name = "fk_pessoa_telefone")
	@ManyToOne(fetch = FetchType.LAZY)
	private Pessoa pessoa = new Pessoa();

	public Telefone() {}
	
	public Telefone(Long id, String numero, String tipo) {
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
