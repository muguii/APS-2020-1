package model.entidades;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FornecedorDTO {
	
	private Integer idFornecedor;
	private String nomeFantasiaFornecedor;
	private String cnpjFornecedor;
	private String emailFornecedor;
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
	
	public FornecedorDTO() {
	}
	
	public FornecedorDTO(Fornecedor fornecedor) {
		idFornecedor = fornecedor.getId();
		nomeFantasiaFornecedor = fornecedor.getNomeFantasia();
		cnpjFornecedor = fornecedor.getCnpj();
		emailFornecedor = fornecedor.getEmail();
		idEndereco = fornecedor.getEndereco().getId();
		logradouroEndereco = fornecedor.getEndereco().getLogradouro();
		cepEndereco = fornecedor.getEndereco().getCep();
		numeroEndereco = fornecedor.getEndereco().getNumero();
		complementoEndereco = fornecedor.getEndereco().getComplemento();
		bairroEndereco = fornecedor.getEndereco().getBairro();
		municipioEndereco = fornecedor.getEndereco().getMunicipio();
		idEstado = fornecedor.getEndereco().getEstado().getId();
		nomeEstado = fornecedor.getEndereco().getEstado().getNome();
		siglaEstado = fornecedor.getEndereco().getEstado().getSigla();
		idTelefone = fornecedor.getEndereco().getTelefone().getId();
		dddTelefoneSP =  new SimpleStringProperty(fornecedor.getEndereco().getTelefone().getDdd());
		numeroTelefoneSP =  new SimpleStringProperty(fornecedor.getEndereco().getTelefone().getTelefone());		
	}

	public Integer getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getNomeFantasiaFornecedor() {
		return nomeFantasiaFornecedor;
	}

	public void setNomeFantasiaFornecedor(String nomeFantasiaFornecedor) {
		this.nomeFantasiaFornecedor = nomeFantasiaFornecedor;
	}

	public String getCnpjFornecedor() {
		return cnpjFornecedor;
	}

	public void setCnpjFornecedor(String cnpjFornecedor) {
		this.cnpjFornecedor = cnpjFornecedor;
	}

	public String getEmailFornecedor() {
		return emailFornecedor;
	}

	public void setEmailFornecedor(String emailFornecedor) {
		this.emailFornecedor = emailFornecedor;
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
	
	public static Fornecedor converterParaFornecedor(FornecedorDTO fornecedorDTO) {
		Fornecedor fornecedor = new Fornecedor();
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		Estado estado = new Estado();
		
		estado.setId(fornecedorDTO.getIdEstado());
		estado.setNome(fornecedorDTO.getNomeEstado());
		estado.setSigla(fornecedorDTO.getSiglaEstado());
		
		telefone.setId(fornecedorDTO.getIdTelefone());
		telefone.setDdd(fornecedorDTO.getDddTelefoneSP().getValue());
		telefone.setTelefone(fornecedorDTO.getNumeroTelefoneSP().getValue());
		
		endereco.setId(fornecedorDTO.getIdEndereco());
		endereco.setTelefone(telefone);
		endereco.setEstado(estado);
		endereco.setLogradouro(fornecedorDTO.getLogradouroEndereco());
		endereco.setCep(fornecedorDTO.getCepEndereco());
		endereco.setNumero(fornecedorDTO.getNumeroEndereco());
		endereco.setComplemento(fornecedorDTO.getComplementoEndereco());
		endereco.setBairro(fornecedorDTO.getBairroEndereco());
		endereco.setMunicipio(fornecedorDTO.getMunicipioEndereco());
		
		fornecedor.setId(fornecedorDTO.getIdFornecedor());
		fornecedor.setEndereco(endereco);
		fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasiaFornecedor());
		fornecedor.setCnpj(fornecedorDTO.getCnpjFornecedor());
		fornecedor.setEmail(fornecedorDTO.getEmailFornecedor());
		
		return fornecedor;		
	}
	
}
