package model.entidades;

import java.io.Serializable;

public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nomeFantasia;
	private String cnpj;
	private String email;
	private Endereco endereco;
	
	public Fornecedor() {		
	}
	
	public Fornecedor(Integer id, String nomeFantasia, String cnpj, String email, Endereco endereco) {
		this.id = id;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.email = email;
		this.endereco = endereco;	
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
		
}
