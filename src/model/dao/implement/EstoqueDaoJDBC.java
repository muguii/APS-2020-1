package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.EstoqueDao;
import model.entidades.Estoque;
import model.entidades.EstoqueDTO;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class EstoqueDaoJDBC implements EstoqueDao{
	
	private Connection con;
	
	@Override
	public void insert(Estoque estoque) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					  "INSERT INTO estoque (nCdProduto, nNrQuantidade, tDtEntrada) "
					+ "VALUES (?, ?, ?);"
			);
			
			pstm.setInt(1, estoque.getProduto().getId());
			pstm.setInt(2, estoque.getQuantidade());
			pstm.setDate(3, java.sql.Date.valueOf(estoque.getDataEntrada()));
			
			int rowsAffected = pstm.executeUpdate();	
			
			if (rowsAffected <= 0) {
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
	public void update(Estoque estoque) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					  "UPDATE estoque SET "
					+ "nCdProduto = ?, "
					+ "nNrQuantidade = ?, "
					+ "tDtEntrada = ? "
					+ "WHERE nCdEstoque = ?;"
			);
						
			pstm.setInt(1, estoque.getProduto().getId());
			pstm.setInt(2, estoque.getQuantidade());
			pstm.setDate(3, java.sql.Date.valueOf(estoque.getDataEntrada()));
			pstm.setInt(4, estoque.getId());
			
			int rowsAffected = pstm.executeUpdate();	
			
			if (rowsAffected <= 0) {
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
			pstm = con.prepareStatement("DELETE FROM produto WHERE nCdProduto = ?; "
					+ "DELETE FROM venda_produto WHERE nCdProduto = ?");
						
			pstm.setInt(1, id);
			pstm.setInt(2, id);
			
			int rowsAffected = pstm.executeUpdate();	
			
			if (rowsAffected <= 0) {
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
	public List<EstoqueDTO> pesquisarTodos() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT E.nCdEstoque, E.nNrQuantidade, E.tDtEntrada, "
					+ "P.nCdProduto, P.sCdProduto, P.sNmProduto, P.sDsUnidadeMedida, P.dVlCompra, "
					+ "P.dVlVenda, P.tDtFabricacao, P.tDtValidade "
					+ "FROM estoque E " 
					+ "LEFT JOIN produto P ON E.nCdProduto = P.nCdProduto;"
			);
			
			rs = pstm.executeQuery();
				
			List<EstoqueDTO> list = new ArrayList<EstoqueDTO>();
			
			while(rs.next()) {
				EstoqueDTO estoqueDTO = instanciarEstoqueDTO(rs);
				list.add(estoqueDTO);
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
	public List<EstoqueDTO> pesquisarPorNome(String nomeProduto) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT E.nCdEstoque, E.nNrQuantidade, E.tDtEntrada, "
					+ "P.nCdProduto, P.sCdProduto, P.sNmProduto, P.sDsUnidadeMedida, P.dVlCompra, "
					+ "P.dVlVenda, P.tDtFabricacao, P.tDtValidade "
					+ "FROM estoque E " 
					+ "LEFT JOIN produto P ON E.nCdProduto = P.nCdProduto "
					+ "WHERE UPPER(P.sNmProduto) LIKE UPPER(?);"
			);
						
			pstm.setString(1, '%' + nomeProduto + '%');
			rs = pstm.executeQuery();
			
			List<EstoqueDTO> list = new ArrayList<EstoqueDTO>();
			
			while(rs.next()) {
				EstoqueDTO estoqueDTO = instanciarEstoqueDTO(rs);
				list.add(estoqueDTO);
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

	private EstoqueDTO instanciarEstoqueDTO(ResultSet rs) throws SQLException {
		EstoqueDTO estoqueDTO = new EstoqueDTO();
		
		estoqueDTO.setIdEstoque(rs.getInt("nCdEstoque"));
		estoqueDTO.setQuantidadeEstoque(rs.getInt("nNrQuantidade"));
		estoqueDTO.setDataEntradaEstoque(rs.getDate("tDtEntrada").toLocalDate());
		estoqueDTO.setIdProduto(rs.getInt("nCdProduto"));
		estoqueDTO.setCodigoProduto(rs.getString("sCdProduto"));
		estoqueDTO.setNomeProduto(rs.getString("sNmProduto"));
		estoqueDTO.setUnidadeMedidaProduto(rs.getString("sDsUnidadeMedida").toUpperCase());
		estoqueDTO.setValorCompraProduto(rs.getDouble("dVlCompra"));
		estoqueDTO.setValorVendaProduto(rs.getDouble("dVlVenda"));
		estoqueDTO.setDataFabricacaoProduto(rs.getDate("tDtFabricacao").toLocalDate());
		estoqueDTO.setDataValidadeproduto(rs.getDate("tDtValidade").toLocalDate());
		
		return estoqueDTO;
	}
	
}