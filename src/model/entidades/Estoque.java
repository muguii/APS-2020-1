package model.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Estoque implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Produto produto;
	private Integer quantidade;
	private LocalDate dataEntrada;
	
	public Estoque() {		
	}
	
	public Estoque(Integer id, Produto produto, Integer quantidade, LocalDate dataEntrada) {

		this.id = id;
		this.produto = produto;
		this.quantidade = quantidade;
		this.dataEntrada = dataEntrada;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
		
}
