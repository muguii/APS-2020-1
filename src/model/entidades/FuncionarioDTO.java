package model.entidades;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FuncionarioDTO {
	
	private Integer idFuncionario;
	private String nomeFuncionario;
	private String matriculaFuncionario;
	private String cpfFuncionario;
	private LocalDate dataNascimentoFuncionario;
	private String emailFuncionario;
	private Integer idEndereco;
	private String logradouroEndereco;
	private String cepEndereco;
	private String numeroEndereco;
	private String complementoEndereco;
	private String bairroEndereco;
	private String municipioEndereco;
	private Integer idEstado;
	private String nomeEstado;
	private String siglaEstado;
	private Integer idTelefone;
	private StringProperty dddTelefoneSP;
	private StringProperty numeroTelefoneSP; 
	private Integer idCargo;
	private String nomeCargo;
	private Double salarioCargo;
	
	public FuncionarioDTO() {		
	}
	
	public FuncionarioDTO(Funcionario funcionario) {
		idFuncionario = funcionario.getId();
		nomeFuncionario = funcionario.getNome();
		matriculaFuncionario = funcionario.getMatricula();
		cpfFuncionario = funcionario.getCpf();
		dataNascimentoFuncionario = funcionario.getDataNascimento();
		emailFuncionario = funcionario.getEmail();
		idEndereco = funcionario.getEndereco().getId();
		logradouroEndereco = funcionario.getEndereco().getLogradouro();
		cepEndereco = funcionario.getEndereco().getCep();
		numeroEndereco = funcionario.getEndereco().getNumero();
		complementoEndereco = funcionario.getEndereco().getComplemento();
		bairroEndereco = funcionario.getEndereco().getBairro();
		municipioEndereco = funcionario.getEndereco().getMunicipio();
		idEstado = funcionario.getEndereco().getEstado().getId();
		nomeEstado = funcionario.getEndereco().getEstado().getNome();
		siglaEstado = funcionario.getEndereco().getEstado().getSigla();
		idTelefone = funcionario.getEndereco().getTelefone().getId();
		dddTelefoneSP =  new SimpleStringProperty(funcionario.getEndereco().getTelefone().getDdd());
		numeroTelefoneSP =  new SimpleStringProperty(funcionario.getEndereco().getTelefone().getTelefone());	
		idCargo = funcionario.getCargo().getId();
		nomeCargo = funcionario.getCargo().getNome();
		salarioCargo = funcionario.getCargo().getSalario();
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getMatriculaFuncionario() {
		return matriculaFuncionario;
	}

	public void setMatriculaFuncionario(String matriculaFuncionario) {
		this.matriculaFuncionario = matriculaFuncionario;
	}

	public String getCpfFuncionario() {
		return cpfFuncionario;
	}

	public void setCpfFuncionario(String cpfFuncionario) {
		this.cpfFuncionario = cpfFuncionario;
	}

	public LocalDate getDataNascimentoFuncionario() {
		return dataNascimentoFuncionario;
	}

	public void setDataNascimentoFuncionario(LocalDate dataNascimentoFuncionario) {
		this.dataNascimentoFuncionario = dataNascimentoFuncionario;
	}

	public String getEmailFuncionario() {
		return emailFuncionario;
	}

	public void setEmailFuncionario(String emailFuncionario) {
		this.emailFuncionario = emailFuncionario;
	}

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getLogradouroEndereco() {
		return logradouroEndereco;
	}

	public void setLogradouroEndereco(String logradouroEndereco) {
		this.logradouroEndereco = logradouroEndereco;
	}

	public String getCepEndereco() {
		return cepEndereco;
	}

	public void setCepEndereco(String cepEndereco) {
		this.cepEndereco = cepEndereco;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	public String getBairroEndereco() {
		return bairroEndereco;
	}

	public void setBairroEndereco(String bairroEndereco) {
		this.bairroEndereco = bairroEndereco;
	}

	public String getMunicipioEndereco() {
		return municipioEndereco;
	}

	public void setMunicipioEndereco(String municipioEndereco) {
		this.municipioEndereco = municipioEndereco;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}

	public Integer getIdTelefone() {
		return idTelefone;
	}

	public void setIdTelefone(Integer idTelefone) {
		this.idTelefone = idTelefone;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public String getNomeCargo() {
		return nomeCargo;
	}

	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}

	public Double getSalarioCargo() {
		return salarioCargo;
	}

	public void setSalarioCargo(Double salarioCargo) {
		this.salarioCargo = salarioCargo;
	}

	public StringProperty getDddTelefoneSP() {
		return dddTelefoneSP;
	}

	public void setDddTelefoneSP(StringProperty dddTelefoneSP) {
		this.dddTelefoneSP = dddTelefoneSP;
	}

	public StringProperty getNumeroTelefoneSP() {
		return numeroTelefoneSP;
	}

	public void setNumeroTelefoneSP(StringProperty numeroTelefoneSP) {
		this.numeroTelefoneSP = numeroTelefoneSP;
	}
	
	public static Funcionario converterParaFuncionario(FuncionarioDTO funcionarioDTO) {
		Funcionario funcionario = new Funcionario();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		Estado estado = new Estado();
		Cargo cargo = new Cargo();
		
		estado.setId(funcionarioDTO.getIdEstado());
		estado.setNome(funcionarioDTO.getNomeEstado());
		estado.setSigla(funcionarioDTO.getSiglaEstado());
		
		telefone.setId(funcionarioDTO.getIdTelefone());
		telefone.setDdd(funcionarioDTO.getDddTelefoneSP().getValue());
		telefone.setTelefone(funcionarioDTO.getNumeroTelefoneSP().getValue());		
		
		endereco.setId(funcionarioDTO.getIdEndereco());
		endereco.setTelefone(telefone);
		endereco.setEstado(estado);		
		endereco.setLogradouro(funcionarioDTO.getLogradouroEndereco());
		endereco.setCep(funcionarioDTO.getCepEndereco());
		endereco.setNumero(funcionarioDTO.getNumeroEndereco());
		endereco.setComplemento(funcionarioDTO.getComplementoEndereco());
		endereco.setBairro(funcionarioDTO.getBairroEndereco());
		endereco.setMunicipio(funcionarioDTO.getMunicipioEndereco());
				
		cargo.setId(funcionarioDTO.getIdCargo());
		cargo.setNome(funcionarioDTO.getNomeCargo());
		cargo.setSalario(funcionarioDTO.getSalarioCargo());
		
		funcionario.setId(funcionarioDTO.getIdFuncionario());
		funcionario.setNome(funcionarioDTO.getNomeFuncionario());
		funcionario.setMatricula(funcionarioDTO.getMatriculaFuncionario());
		funcionario.setCpf(funcionarioDTO.getCpfFuncionario());
		funcionario.setDataNascimento(funcionarioDTO.getDataNascimentoFuncionario());
		funcionario.setEmail(funcionarioDTO.getEmailFuncionario());
		funcionario.setEndereco(endereco);
		funcionario.setCargo(cargo);
		
		return funcionario;
		
	}

}
