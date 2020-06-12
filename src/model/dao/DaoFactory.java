package model.dao;

import model.dao.implement.CargoDaoJDBC;
import model.dao.implement.EnderecoDaoJDBC;
import model.dao.implement.EstoqueDaoJDBC;
import model.dao.implement.FornecedorDaoJDBC;
import model.dao.implement.FuncionarioDaoJDBC;
import model.dao.implement.ProdutoDaoJDBC;
import model.dao.implement.RelatoriosDaoJDBC;
import model.dao.implement.TelefoneDaoJDBC;
import model.dao.implement.VendaDaoJDBC;
import model.dao.implement.VendaProdutoDaoJDBC;

public class DaoFactory {
			
	public static ProdutoDao criarProdutoDao() {
		return new ProdutoDaoJDBC();
	}

	public static CargoDao criarCargoDao() {
		return new CargoDaoJDBC();
	}
	
	public static FuncionarioDao criarFuncionarioDao() {
		return new FuncionarioDaoJDBC();
	}
	
	public static EnderecoDao criarEnderecoDao() {
		return new EnderecoDaoJDBC();
	}
	
	public static TelefoneDao criarTelefoneDao() {
		return new TelefoneDaoJDBC();
	}
	
	public static EstoqueDao criarEstoqueDao() {
		return new EstoqueDaoJDBC();
	}
	
	public static FornecedorDao criarFornecedorDao() {
		return new FornecedorDaoJDBC();
	}
	
	public static VendaDao criarVendaDao() {
		return new VendaDaoJDBC();
	}
	
	public static VendaProdutosDao criarVendaProdutoDao() {
		return new VendaProdutoDaoJDBC();
	}
	
	public static RelatoriosDao criarRelatoriosDao() {
		return new RelatoriosDaoJDBC();
	}
}
