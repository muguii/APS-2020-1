package model.services;

import java.sql.ResultSet;

import model.dao.DaoFactory;
import model.dao.RelatoriosDao;

public class RelatoriosService {
	
	private RelatoriosDao dao;
	
	public RelatoriosService() {
		dao = DaoFactory.criarRelatoriosDao();		
	}
	
	public ResultSet vendasProduto() {
		return dao.vendasProduto();
	}
	
	public ResultSet vendasFuncionario() {
		return dao.vendasFuncionario();
	}
	
	public ResultSet vendasData() {
		return dao.vendasData();
	}
	
	public ResultSet estoqueValidade() {
		return dao.estoqueValidade();
	}
	
}
