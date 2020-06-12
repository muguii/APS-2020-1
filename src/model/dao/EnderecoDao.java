package model.dao;

import java.util.List;

import model.entidades.Endereco;
import model.entidades.Estado;

public interface EnderecoDao {
	
	void insert(Endereco endereco);
	void update(Endereco endereco);
	void deletarPorId(Integer id);
	List<Estado> pesquisarTodosEstados();

}
