package model.entidades;

import java.time.LocalDate;

public class VendasProdutosDTO {
		
	private Integer idVendasProdutos;
	private Integer idVenda;
	private Double valorTotalVenda;
	private String formaPagamentoVenda;
	private LocalDate dataVenda;
	private Integer idFuncionario;
	private String nomeFuncionario;
	private String matriculaFuncionario;
	private String cpfFuncionario;
	private LocalDate dataNascimentoFuncionario;
	private String emailFuncionario;
	private Integer idProduto;
	private String codigoProduto;
	private String nomeProduto;
	private String unidadeMedidaProduto;
	private Double valorCompraProduto;
	private Double valorVendaProduto;
	private LocalDate dataFabricacaoProduto;
	private LocalDate dataValidadeproduto;
	private Integer quantidadeVendasProdutos;
	

	public VendasProdutosDTO() {		
	}

	
	public Integer getIdVendasProdutos() {
		return idVendasProdutos;
	}

	public void setIdVendasProdutos(Integer idVendasProdutos) {
		this.idVendasProdutos = idVendasProdutos;
	}

	public Integer getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(Integer idVenda) {
		this.idVenda = idVenda;
	}

	public Double getValorTotalVenda() {
		return valorTotalVenda;
	}

	public void setValorTotalVenda(Double valorTotalVenda) {
		this.valorTotalVenda = valorTotalVenda;
	}

	public String getFormaPagamentoVenda() {
		return formaPagamentoVenda;
	}

	public void setFormaPagamentoVenda(String formaPagamentoVenda) {
		this.formaPagamentoVenda = formaPagamentoVenda;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
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

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getUnidadeMedidaProduto() {
		return unidadeMedidaProduto;
	}

	public void setUnidadeMedidaProduto(String unidadeMedidaProduto) {
		this.unidadeMedidaProduto = unidadeMedidaProduto;
	}

	public Double getValorCompraProduto() {
		return valorCompraProduto;
	}

	public void setValorCompraProduto(Double valorCompraProduto) {
		this.valorCompraProduto = valorCompraProduto;
	}

	public Double getValorVendaProduto() {
		return valorVendaProduto;
	}

	public void setValorVendaProduto(Double valorVendaProduto) {
		this.valorVendaProduto = valorVendaProduto;
	}

	public LocalDate getDataFabricacaoProduto() {
		return dataFabricacaoProduto;
	}

	public void setDataFabricacaoProduto(LocalDate dataFabricacaoProduto) {
		this.dataFabricacaoProduto = dataFabricacaoProduto;
	}

	public LocalDate getDataValidadeproduto() {
		return dataValidadeproduto;
	}

	public void setDataValidadeproduto(LocalDate dataValidadeproduto) {
		this.dataValidadeproduto = dataValidadeproduto;
	}

	public Integer getQuantidadeVendasProdutos() {
		return quantidadeVendasProdutos;
	}

	public void setQuantidadeVendasProdutos(Integer quantidadeVendasProdutos) {
		this.quantidadeVendasProdutos = quantidadeVendasProdutos;
	}

	public Double getTotal() {
		Double valorProduto = getValorVendaProduto();
		Double total = valorProduto * getQuantidadeVendasProdutos();
		
		return total;
	}
	
	public static VendasProdutos converterParaVendasProdutos(VendasProdutosDTO vendasProdutosDTO) {
		VendasProdutos vendasProdutos = new VendasProdutos();
		
		vendasProdutos.setIdVenda(vendasProdutosDTO.getIdVenda());
		vendasProdutos.setIdProduto(vendasProdutosDTO.getIdProduto());
		vendasProdutos.setQuantidade(vendasProdutosDTO.getQuantidadeVendasProdutos());
		
		
		return vendasProdutos;		
		
		/*Funcionario funcionario = new Funcionario();
		Venda venda = new Venda();
		Produto produto = new Produto();
		
		funcionario.setId(vendasProdutosDTO.getIdFuncionario());
		funcionario.setNome(vendasProdutosDTO.getNomeFuncionario());
		funcionario.setMatricula(vendasProdutosDTO.getMatriculaFuncionario());
		funcionario.setCpf(vendasProdutosDTO.getCpfFuncionario());
		funcionario.setDataNascimento(vendasProdutosDTO.getDataNascimentoFuncionario());
		funcionario.setEmail(vendasProdutosDTO.getEmailFuncionario());
		
		venda.setId(vendasProdutosDTO.getIdVenda());
		venda.setFuncionario(funcionario);
		venda.setValorTotal(vendasProdutosDTO.getValorTotalVenda());
		venda.setFormaPagamento(vendasProdutosDTO.getFormaPagamentoVenda());
		venda.setDataVenda(vendasProdutosDTO.getDataVenda());
		
		produto.setId(vendasProdutosDTO.getIdProduto());
		produto.setCodigoProduto(vendasProdutosDTO.getCodigoProduto());
		produto.setNome(vendasProdutosDTO.getNomeProduto());
		produto.setUnidadeMedida(vendasProdutosDTO.getUnidadeMedidaProduto());
		produto.setValorCompra(vendasProdutosDTO.getValorCompraProduto());
		produto.setValorVenda(vendasProdutosDTO.getValorVendaProduto());
		produto.setDataFabricacao(vendasProdutosDTO.getDataFabricacaoProduto());
		produto.setDataValidade(vendasProdutosDTO.getDataValidadeproduto());
		
		vendasProdutos.setId(vendasProdutosDTO.getIdVendasProdutos());
		vendasProdutos.setVenda(venda);
		vendasProdutos.setProduto(produto);
		vendasProdutos.setQuantidade(vendasProdutosDTO.getQuantidadeVendasProdutos());*/
	
	}
	
}
