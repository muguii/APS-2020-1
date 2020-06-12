package model.services;

import java.util.List;

import model.dao.CargoDao;
import model.dao.DaoFactory;
import model.entidades.Cargo;

public class CargoService {
	
	private CargoDao dao;
	
	public CargoService() {
		dao = DaoFactory.criarCargoDao();
	}	
	
	public void insertOrUpdate(Cargo cargo) {
		if (cargo.getId() == null) {
			dao.insert(cargo);
		} else {
			dao.update(cargo);
		}
	}
	
	public void delete(Cargo cargo) {
		dao.deletarPorId(cargo.getId());
	}
	
	public List<Cargo> pesquisarTodos() {
		return dao.pesquisarTodos();
	}	
	
}
