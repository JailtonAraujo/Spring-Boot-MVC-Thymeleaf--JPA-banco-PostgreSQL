package com.br.projetosbmvc.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@SuppressWarnings("deprecation")
@Table(name = "foto_pessoa")
@Entity
public class FotoPessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nameImage;

	private String typeImage;

	@Lob
	private byte[] fotoUser;
	
	@Transient
	private String fotoBase64 = "";

	@JoinColumn(name = "pessoa_id")
	@ForeignKey(name = "fk_fotopessoa_pessoa")
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	private Pessoa pessoa;
	
	public FotoPessoa() {
		// TODO Auto-generated constructor stub
	}

	public FotoPessoa(Long id, String nameImage, String typeImage, byte[] fotoUser) {
		this.id = id;
		this.nameImage = nameImage;
		this.typeImage = typeImage;
		this.fotoUser = fotoUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameImage() {
		return nameImage;
	}

	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}

	public String getTypeImage() {
		return typeImage;
	}

	public void setTypeImage(String typeImage) {
		this.typeImage = typeImage;
	}

	public byte[] getFotoUser() {
		return fotoUser;
	}

	public void setFotoUser(byte[] fotoUser) {
		this.fotoUser = fotoUser;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}
	
	

}
