package model.entidades;

import java.time.LocalDate;

public class EstoqueDTO {

	private Integer idEstoque;
	private Integer quantidadeEstoque;
	private LocalDate dataEntradaEstoque;
	private Integer idProduto;
	private String codigoProduto;
	private String nomeProduto;
	private String unidadeMedidaProduto;
	private Double valorCompraProduto;
	private Double valorVendaProduto;
	private LocalDate dataFabricacaoProduto;
	private LocalDate dataValidadeproduto;
	
	public EstoqueDTO() {
	}
	
	public EstoqueDTO(Estoque estoque) {
		idEstoque = estoque.getId();
		quantidadeEstoque = estoque.getQuantidade();
		dataEntradaEstoque = estoque.getDataEntrada();
		idProduto = estoque.getProduto().getId();
		codigoProduto = estoque.getProduto().getCodigoProduto();
		nomeProduto = estoque.getProduto().getNome();
		unidadeMedidaProduto = estoque.getProduto().getUnidadeMedida();
		valorCompraProduto = estoque.getProduto().getValorCompra();
		valorVendaProduto = estoque.getProduto().getValorVenda();
		dataFabricacaoProduto = estoque.getProduto().getDataFabricacao();
		dataValidadeproduto = estoque.getProduto().getDataValidade();
	}

	public Integer getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Integer idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public LocalDate getDataEntradaEstoque() {
		return dataEntradaEstoque;
	}

	public void setDataEntradaEstoque(LocalDate dataEntradaEstoque) {
		this.dataEntradaEstoque = dataEntradaEstoque;
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
	
	public static Estoque converterParaEstoque(EstoqueDTO estoqueDTO) {
		Estoque estoque = new Estoque();
		Produto produto = new Produto();
		
		estoque.setId(estoqueDTO.getIdEstoque());
		estoque.setProduto(produto);
		estoque.setQuantidade(estoqueDTO.getQuantidadeEstoque());
		estoque.setDataEntrada(estoqueDTO.getDataEntradaEstoque());
		
		produto.setId(estoqueDTO.getIdProduto());
		produto.setCodigoProduto(estoqueDTO.getCodigoProduto());
		produto.setNome(estoqueDTO.getNomeProduto());
		produto.setUnidadeMedida(estoqueDTO.getUnidadeMedidaProduto());
		produto.setValorCompra(estoqueDTO.getValorCompraProduto());
		produto.setValorVenda(estoqueDTO.getValorVendaProduto());
		produto.setDataFabricacao(estoqueDTO.getDataFabricacaoProduto());
		produto.setDataValidade(estoqueDTO.getDataValidadeproduto());

		return estoque;	
	}
	
}
