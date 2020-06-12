package model.dao;

import java.util.List;

import model.entidades.VendasProdutos;
import model.entidades.VendasProdutosDTO;

public interface VendaProdutosDao {
	
	void insert(VendasProdutos vendaProdutos);
	List<VendasProdutosDTO> pesquisarProdutosVenda(Integer idVenda);
	void updateEstoque(VendasProdutos vendasProdutos);
	Integer consultarEstoque(Integer idProduto);
}
