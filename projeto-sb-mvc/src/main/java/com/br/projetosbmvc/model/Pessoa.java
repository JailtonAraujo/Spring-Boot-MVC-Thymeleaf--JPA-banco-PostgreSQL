package com.br.projetosbmvc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("deprecation")
@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O nome não ode ser nulo!")
	@NotEmpty(message = "O nome não pode ser vazio !")
	private String nome;

	@NotNull(message = "Sobrenome não pode ser nulo")
	@NotNull(message = "Sobrenome não pode ser vazio!")
	private String sobrenome;

	@Min(value = 18, message = "A idade deve ser superior a 18 anos")
	private int idade;

	private String sexo;

	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private List<Telefone> telefones;

	@OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private Endereco endereco = new Endereco();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profissao_id")
	@org.hibernate.annotations.ForeignKey(name = "fk_pessoa_profissao")
	private Profissao profissao = new Profissao();

	@Enumerated(EnumType.STRING)
	private Cargo cargo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private FotoPessoa fotoPessoa;

	public Pessoa() {
	}

	public Pessoa(Long id, String nome, String sobrenome, int idade) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.idade = idade;
	}

	public Pessoa(String nome, String sobrenome, String cidade, String uf) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.endereco.setCidade(cidade);
		this.endereco.setUf(uf);
	}

	public Pessoa(FotoPessoa fotoPessoa) {
		this.fotoPessoa = fotoPessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public int getIdade() {
		return idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public FotoPessoa getFotoPessoa() {
		return fotoPessoa;
	}

	public void setFotoPessoa(FotoPessoa fotoPessoa) {
		this.fotoPessoa = fotoPessoa;
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
		Pessoa other = (Pessoa) obj;
		return Objects.equals(id, other.id);
	}

}
