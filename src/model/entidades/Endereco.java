package model.entidades;

import java.io.Serializable;

public class Endereco implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String logradouro;
	private String cep;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private Estado estado;
	private Telefone telefone;
	
	public Endereco() {		
	}
	
	public Endereco(Integer id, String logradouro, String cep, String numero, String complemento, String bairro,
			String municipio, Estado estado, Telefone telefone) {

		this.id = id;
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.municipio = municipio;
		this.estado = estado;
		this.telefone = telefone;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	
}
