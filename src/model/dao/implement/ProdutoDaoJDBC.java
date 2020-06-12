package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.ProdutoDao;
import model.entidades.Produto;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class ProdutoDaoJDBC implements ProdutoDao {

	private Connection con;
	
	@Override
	public void insert(Produto produto) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"INSERT INTO produto "
					+ "(sCdProduto, sNmProduto, sDsUnidadeMedida, tDtValidade, "
					+ "dVlCompra, dVlVenda, tDtFabricacao) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
			
			pstm.setString(1, produto.getCodigoProduto());
			pstm.setString(2, produto.getNome());
			pstm.setString(3, produto.getUnidadeMedida());				
			pstm.setDate(4, java.sql.Date.valueOf(produto.getDataValidade()));
			
			if (produto.getValorCompra() == null) {
				pstm.setDouble(5, 0.0);
			} else {
				pstm.setDouble(5, produto.getValorCompra());			
			}
			
			pstm.setDouble(6, produto.getValorVenda());
			pstm.setDate(7, java.sql.Date.valueOf(produto.getDataFabricacao()));
		
			
			int rowsAffected = pstm.executeUpdate();	
			
			if(rowsAffected > 0) {
				ResultSet rs = pstm.getGeneratedKeys();				
				if (rs.next()) {
					Integer id = rs.getInt(1);
					produto.setId(id);
				}
				BdConnection.closeConnection(null, null, rs);
			} 
			else {
				throw new BdException("Nenhuma linha afetada!");
			}
		} 
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		} 
		finally {
			BdConnection.closeConnection(con, pstm);
		}
			
	}

	@Override
	public void update(Produto produto) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;
		
		try {
			pstm = con.prepareStatement(
					  "UPDATE produto SET "
					+ "sCdProduto = ?, "
					+ "sNmProduto = ?, "
					+ "sDsUnidadeMedida = ?, "
					+ "dVlCompra = ?, "
					+ "dVlVenda = ?, "
					+ "tDtFabricacao = ?, "
					+ "tDtValidade = ? "
					+ "WHERE nCdProduto = ?"
			);			
			
			pstm.setString(1, produto.getCodigoProduto());
			pstm.setString(2, produto.getNome());
			pstm.setString(3, produto.getUnidadeMedida());
			pstm.setDouble(4, produto.getValorCompra());
			pstm.setDouble(5, produto.getValorVenda());
			pstm.setDate(6, java.sql.Date.valueOf(produto.getDataFabricacao()));
			pstm.setDate(7, java.sql.Date.valueOf(produto.getDataValidade()));	
			pstm.setInt(8, produto.getId());			
			
			int rowsAffected = pstm.executeUpdate();
			
			if(rowsAffected <= 0) {
				throw new BdException("Nenhuma linha afetada!");
			}
		}
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		}
		finally {
			BdConnection.closeConnection(con, pstm);
		}

	}

	@Override
	public void deletarPorId(Integer id) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;
		
		try {
			pstm = con.prepareStatement("DELETE FROM produto WHERE nCdProduto = ?;");
			pstm.setInt(1, id);
			
			int rowsAffected = pstm.executeUpdate();
			
			if(rowsAffected <= 0) {
				throw new BdException("Nenhuma linha afetada!");
			}
			
		}
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		}
		finally {
			BdConnection.closeConnection(con, pstm);
		}
	}
	
	@Override
	public List<Produto> pesquisarTodos() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement("SELECT * FROM produto ORDER BY sNmProduto ASC;");
			rs = pstm.executeQuery();
			
			List<Produto> list = new ArrayList<Produto>();
			
			while(rs.next()) {
				Produto produto = instanciarProduto(rs);
				list.add(produto);				
			}
			return list;
			
		}
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		}
		finally {
			BdConnection.closeConnection(con, pstm, rs);
		}
	}
	
	@Override
	public List<Produto> pesquisarPorNome(String nome) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			
			pstm = con.prepareStatement("SELECT * FROM produto WHERE UPPER(sNmProduto) LIKE UPPER(?);");
			pstm.setString(1,'%' + nome + '%'); 
			rs = pstm.executeQuery();
			
			List<Produto> list = new ArrayList<Produto>();
			
			while(rs.next()) {			
				Produto produto = instanciarProduto(rs);
				list.add(produto);				
			}			
			
			return list;
			
		}
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		}
		finally {
			BdConnection.closeConnection(con, pstm, rs);
		}
	}
	
	@Override
	public Produto pesquisarPorCodigo(String codigoProduto) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement("SELECT * FROM produto WHERE sCdProduto = ?;");
			pstm.setString(1, codigoProduto);
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				Produto produto = instanciarProduto(rs);
				return produto;
			}
			return null;
			
		}
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		}
		finally {
			BdConnection.closeConnection(con, pstm, rs);
		}
	}						
	
	private Produto instanciarProduto(ResultSet rs) throws SQLException {
		Produto produto = new Produto();
		
		produto.setId(rs.getInt("nCdProduto"));
		produto.setCodigoProduto(rs.getString("sCdProduto"));
		produto.setNome(rs.getString("sNmProduto"));
		produto.setUnidadeMedida(rs.getString("sDsUnidadeMedida").toUpperCase());
		produto.setValorCompra(rs.getDouble("dVlCompra"));
		produto.setValorVenda(rs.getDouble("dVlVenda"));
		if (rs.getDate("tDtFabricacao") != null) {
			produto.setDataFabricacao(rs.getDate("tDtFabricacao").toLocalDate());			
		} else {
			produto.setDataFabricacao(null);
		}
		if (rs.getDate("tDtValidade") != null) {
			produto.setDataValidade(rs.getDate("tDtValidade").toLocalDate());
		}else {
			produto.setDataValidade(null);
		}
		return produto;
		
	}

}
