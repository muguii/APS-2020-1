package model.dao;

import java.util.List;

import model.entidades.Funcionario;
import model.entidades.FuncionarioDTO;

public interface FuncionarioDao {
	
	void insert(Funcionario funcionario);
	void update(Funcionario funcionario);
	void deletarPorId(Integer id);
	List<FuncionarioDTO> pesquisarPorNome(String nome);
	List<FuncionarioDTO> pesquisarTodos();
	List<Funcionario> pesquisaSimples();
}
