package model.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String codigoProduto;
	private String nome;
	private String unidadeMedida;
	private Double valorCompra;
	private Double valorVenda;
	private LocalDate dataFabricacao;
	private LocalDate dataValidade;
	
	private List<VendasProdutos> vendasProdutos = new ArrayList<VendasProdutos>();
	
	
	public Produto() {		
	}	

	public Produto(Integer id, String codigoProduto, String nome, String unidadeMedida, Double valorCompra,
			Double valorVenda, LocalDate dataFabricacao, LocalDate dataValidade) {
		this.id = id;
		this.codigoProduto = codigoProduto;
		this.nome = nome;
		this.unidadeMedida = unidadeMedida;
		this.valorCompra = valorCompra;
		this.valorVenda = valorVenda;
		this.dataFabricacao = dataFabricacao;
		this.dataValidade = dataValidade;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(Double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public Double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}

	public LocalDate getDataFabricacao() {
		return dataFabricacao;
	}

	public void setDataFabricacao(LocalDate dataFabricacao) {
		this.dataFabricacao = dataFabricacao;
	}

	public LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}

	public List<VendasProdutos> getVendasProdutos() {
		return vendasProdutos;
	}
	

}
