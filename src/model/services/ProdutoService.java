package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entidades.Produto;

public class ProdutoService {
	
	private ProdutoDao dao;
	
	public ProdutoService() {	
		dao = DaoFactory.criarProdutoDao();
	}

	public void insertOrUpdate(Produto produto) {
		if (produto.getId() == null) {
			dao.insert(produto);
		} else {		
			dao.update(produto);
		}
	
	}
	
	public void delete(Produto produto) {
		dao.deletarPorId(produto.getId());
	}	
	
	public List<Produto> pesquisarTodos() {		
		return dao.pesquisarTodos();
	}
	
	public List<Produto> pesquisarPorNome(String produtoNome) {
		return dao.pesquisarPorNome(produtoNome);
	}		
	
	public Produto pesquisarPorCodigo(Produto produto) {
		return dao.pesquisarPorCodigo(produto.getCodigoProduto());
	}
	
	public Produto pesquisarPorCodigo(String codigoProduto) {
		return dao.pesquisarPorCodigo(codigoProduto);
	}
}
