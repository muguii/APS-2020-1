package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EstoqueDao;
import model.entidades.Estoque;
import model.entidades.EstoqueDTO;

public class EstoqueService {
	
	private EstoqueDao dao;
	
	public EstoqueService() {
		dao = DaoFactory.criarEstoqueDao();
	}
	
	public void insertOrUpdate(Estoque estoque) {
		if (estoque.getId() == null) {
			dao.insert(estoque);
		} else {
			dao.update(estoque);
		}
	}
	
	public void delete(Estoque estoque) {
		dao.deletarPorId(estoque.getProduto().getId());
	}
	
	public List<EstoqueDTO> pesquisarTodos() {
		return dao.pesquisarTodos();
	}
	
	public List<EstoqueDTO> pesquisarPorNome(String nomeProduto) {
		return dao.pesquisarPorNome(nomeProduto);
	}
	
}
