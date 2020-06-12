package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VendaProdutosDao;
import model.entidades.VendasProdutos;
import model.entidades.VendasProdutosDTO;

public class VendasProdutosService {
	
	private VendaProdutosDao dao;
	
	public VendasProdutosService() {
		dao = DaoFactory.criarVendaProdutoDao();
	}
	
	public void insert(VendasProdutos vendasProdutos) {
		dao.insert(vendasProdutos);
	}
	
	public void updateEstoque(VendasProdutos vendasProdutos) {
		dao.updateEstoque(vendasProdutos);
	}
	
	public Integer consultarQtdEstoque(Integer idProduto) {
		return dao.consultarEstoque(idProduto);
	}
	
	
	public Double atualizarValorTotal(VendasProdutosDTO vendasProdutosDTO) {
		return vendasProdutosDTO.getTotal();
	}

	public List<VendasProdutosDTO> pesquisarProdutosVenda(Integer idVenda) {
		return dao.pesquisarProdutosVenda(idVenda);
	}
	


}
