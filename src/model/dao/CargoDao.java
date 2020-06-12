package model.dao;

import java.util.List;

import model.entidades.Cargo;


public interface CargoDao {
	
	void insert(Cargo cargo);
	void update(Cargo cargo);
	void deletarPorId(Integer id);
	List<Cargo> pesquisarTodos();
	
}
