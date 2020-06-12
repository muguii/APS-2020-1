package model.dao;

import java.sql.ResultSet;

public interface RelatoriosDao {
	
	
	public ResultSet vendasProduto();
	
	public ResultSet vendasFuncionario();
	
	public ResultSet vendasData();
	
	public ResultSet estoqueValidade();
	
	
}
