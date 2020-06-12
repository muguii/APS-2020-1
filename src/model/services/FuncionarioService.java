package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.FuncionarioDao;
import model.entidades.Funcionario;
import model.entidades.FuncionarioDTO;

public class FuncionarioService {
	
	private FuncionarioDao dao;
	
	public FuncionarioService() {
		dao = DaoFactory.criarFuncionarioDao();
	}
	
	public void insertOrUpdate(Funcionario funcionario) {
		if (funcionario.getId() == null) {
			dao.insert(funcionario);
		} else {
			dao.update(funcionario);
		}
	}
	
	public void delete(Funcionario funcionario) {
		dao.deletarPorId(funcionario.getEndereco().getTelefone().getId());
	}
	
	public List<FuncionarioDTO> pesquisarTodos() {
		return dao.pesquisarTodos();
	}
	
	public List<FuncionarioDTO> pesquisarPorNome(String funcionarioNome) {
		return dao.pesquisarPorNome(funcionarioNome);
	}
	
	public List<Funcionario> pesquisaSimples() {
		return dao.pesquisaSimples();
	}
}
