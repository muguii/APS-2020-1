package model.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venda implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Double valorTotal;
	private String formaPagamento;
	private LocalDate dataVenda;
	private Funcionario funcionario;
	
	private List<VendasProdutos> vendasProdutos = new ArrayList<VendasProdutos>();
	
	public Venda() {		
	}
	
	public Venda(Integer id, Double valorTotal, String formaPagamento, LocalDate dataVenda, Funcionario funcionario) {

		this.id = id;
		this.valorTotal = valorTotal;
		this.formaPagamento = formaPagamento;
		this.dataVenda = dataVenda;
		this.funcionario = funcionario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}	
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<VendasProdutos> getVendasProdutos() {
		return vendasProdutos;
	}

}
