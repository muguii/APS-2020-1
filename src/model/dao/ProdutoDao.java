package model.dao;

import java.util.List;

import model.entidades.Produto;

public interface ProdutoDao {
	
	void insert(Produto obj);
	void update(Produto obj);
	void deletarPorId(Integer id);
	List<Produto> pesquisarPorNome(String nome);
	List<Produto> pesquisarTodos();
	Produto pesquisarPorCodigo(String codigoProduto);
	
}