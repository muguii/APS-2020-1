package model.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Funcionario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String matricula;
	private String cpf;
	private LocalDate dataNascimento;
	private String email;
	private Endereco endereco;
	private Cargo cargo;
	
	public Funcionario() {		
	}
	
	public Funcionario(Integer id, String nome, String matricula, String cpf, LocalDate dataNascimento, String email,
			Endereco endereco, Cargo cargo) {

		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.endereco = endereco;
		this.cargo = cargo;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}	

}
