package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.FornecedorDao;
import model.entidades.Fornecedor;
import model.entidades.FornecedorDTO;

public class FornecedorService {
	
	private FornecedorDao dao;
	
	public FornecedorService() {
		dao = DaoFactory.criarFornecedorDao();
	}
	
	public void insertOrUpdate(Fornecedor fornecedor) {
		if (fornecedor.getId() == null) {
			dao.insert(fornecedor);
		} else {
			dao.update(fornecedor);
		}
	}
	
	public void delete(Fornecedor fornecedor) {
		dao.deletarPorId(fornecedor.getEndereco().getTelefone().getId());
	}
	
	public List<FornecedorDTO> pesquisarTodos(){
		return dao.pesquisarTodos();
	}
	
	public List<FornecedorDTO> pesquisarPorNome(String nomeFantasia) {
		return dao.pesquisarPorNome(nomeFantasia);
	}
}