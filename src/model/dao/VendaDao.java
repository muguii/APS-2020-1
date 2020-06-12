package model.dao;

import java.util.List;

import model.entidades.Venda;
import model.entidades.VendaDTO;

public interface VendaDao {
	
	void insert(Venda venda);
	List<VendaDTO> pesquisarPorNomeFuncionario(String nomeFuncionario);
	List<VendaDTO> pesquisarTodos();

}
