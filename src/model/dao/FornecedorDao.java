package model.dao;

import java.util.List;

import model.entidades.Fornecedor;
import model.entidades.FornecedorDTO;


public interface FornecedorDao {
	
	void insert(Fornecedor fornecedor);
	void update(Fornecedor fornecedor);
	void deletarPorId(Integer id);
	List<FornecedorDTO> pesquisarPorNome(String nomeFantasia);
	List<FornecedorDTO> pesquisarTodos();
	
}
