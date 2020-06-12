package model.dao;

import model.entidades.Telefone;

public interface TelefoneDao {
	
	void insert(Telefone telefone);
	void update(Telefone telefone);
	void deletarPorId(Integer id);
	
}
