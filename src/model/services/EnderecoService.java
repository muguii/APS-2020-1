package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EnderecoDao;
import model.dao.TelefoneDao;
import model.entidades.Endereco;
import model.entidades.Estado;
import model.entidades.Telefone;

public class EnderecoService {
	
	private EnderecoDao endDao;
	private TelefoneDao telDao;
	
	public EnderecoService() {
		endDao = DaoFactory.criarEnderecoDao();
		telDao = DaoFactory.criarTelefoneDao();
	}
	
	public void insertOrUpdate(Endereco endereco, Telefone telefone) {
		if (telefone.getId() == null && endereco.getId() == null) {
			telDao.insert(telefone);
			endDao.insert(endereco);
		} else if (telefone.getId() != null && endereco.getId() != null) {
			telDao.update(telefone);
			endDao.update(endereco);
		}  else {
			throw new IllegalStateException("Não foi possível concluir a ação.");
		}				
	}
	
	public List<Estado> pesquisarTodosEstados() {
		return endDao.pesquisarTodosEstados();
	}
}
