package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VendaDao;
import model.entidades.Venda;
import model.entidades.VendaDTO;

public class VendaService {

	private VendaDao dao;
	
	public VendaService() {
		dao = DaoFactory.criarVendaDao();
	}
	
	public void insert(Venda venda) {
		dao.insert(venda);
	}
	
	public List<VendaDTO> pesquisarTodos() {
		return dao.pesquisarTodos();
	}
	
	public List<VendaDTO> pesquisarPorNomeFuncionario(String nomeFuncionario) {
		return dao.pesquisarPorNomeFuncionario(nomeFuncionario);
	}
}
