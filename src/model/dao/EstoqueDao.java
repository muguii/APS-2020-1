package model.dao;

import java.util.List;

import model.entidades.Estoque;
import model.entidades.EstoqueDTO;

public interface EstoqueDao {
	
	void insert(Estoque estoque);
	void update(Estoque estoque);
	void deletarPorId(Integer id);
	List<EstoqueDTO> pesquisarPorNome(String nome);
	List<EstoqueDTO> pesquisarTodos();

}
