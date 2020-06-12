package model.entidades;

import java.time.LocalDate;

public class VendaDTO {

	private Integer idVenda;
	private Double valorTotalVenda;
	private String formaPagamentoVenda;
	private LocalDate dataVenda;
	private Integer idFuncionario;
	private String nomeFuncionario;
	
	public VendaDTO() {
	}
	
	public VendaDTO(Venda venda) {
		idVenda = venda.getId();
		valorTotalVenda = venda.getValorTotal();
		formaPagamentoVenda = venda.getFormaPagamento();
		dataVenda = venda.getDataVenda();
		idFuncionario = venda.getFuncionario().getId();
		nomeFuncionario = venda.getFuncionario().getNome();
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
	
	public static Venda converterParaVenda(VendaDTO vendaDTO) {
		Venda venda = new Venda();
		Funcionario funcionario = new Funcionario();
		
		venda.setId(vendaDTO.getIdVenda());
		venda.setValorTotal(vendaDTO.getValorTotalVenda());
		venda.setFormaPagamento(vendaDTO.getFormaPagamentoVenda());
		venda.setDataVenda(vendaDTO.getDataVenda());
		
		funcionario.setId(vendaDTO.getIdFuncionario());
		funcionario.setNome(vendaDTO.getNomeFuncionario());
		venda.setFuncionario(funcionario);
		
		return venda;
	}
	
}
