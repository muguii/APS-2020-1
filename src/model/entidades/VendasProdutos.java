package model.entidades;

import java.io.Serializable;

public class VendasProdutos implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer idProduto;
	private Integer idVenda;
	private Produto produto;
	private Venda venda;
	
	private Integer quantidade;

		
	public VendasProdutos() {		
	}

	public VendasProdutos(Integer id, Integer idProduto, Produto produto, Integer idVenda, Venda venda,
			Integer quantidade) {
		this.id = id;
		this.idProduto = idProduto;
		this.produto = produto;
		this.idVenda = idVenda;
		this.venda = venda;
		this.quantidade = quantidade;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(Integer idVenda) {
		this.idVenda = idVenda;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	

}
