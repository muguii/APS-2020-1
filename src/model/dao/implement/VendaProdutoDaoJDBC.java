package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.VendaProdutosDao;
import model.entidades.VendasProdutos;
import model.entidades.VendasProdutosDTO;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class VendaProdutoDaoJDBC implements VendaProdutosDao{

	private Connection con;
	
	@Override
	public void insert(VendasProdutos vendaProdutos) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"INSERT INTO venda_produto (nCdVenda, nCdProduto, nNrQuantidade)"
					+ " VALUES (?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
					
			pstm.setInt(1, vendaProdutos.getIdVenda());
			pstm.setInt(2, vendaProdutos.getIdProduto());
			pstm.setInt(3, vendaProdutos.getQuantidade());
			
			int rowsAffected = pstm.executeUpdate();	
			
			if(rowsAffected > 0) {
				ResultSet rs = pstm.getGeneratedKeys();				
				if (rs.next()) {
					Integer id = rs.getInt(1);
					vendaProdutos.setId(id);
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
	public void updateEstoque(VendasProdutos vendaProdutos) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					  "UPDATE estoque SET "
					+ "nNrQuantidade = nNrQuantidade - ? "
					+ "WHERE nCdProduto = ?"
			);
					
			pstm.setInt(1, vendaProdutos.getQuantidade());
			pstm.setInt(2, vendaProdutos.getIdProduto());
			
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
	public Integer consultarEstoque(Integer idProduto) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT nNrQuantidade "
					+ "FROM estoque "
					+ "WHERE nCdProduto = ?;"
			);
					
			pstm.setInt(1, idProduto);
			rs = pstm.executeQuery();
			
			Integer qtdProduto;
			if(rs.next()) {
				qtdProduto = rs.getInt("nNrQuantidade");			
			} else {
				return 0;
			}

			return qtdProduto;
			
		} 
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		} 
		finally {
			BdConnection.closeConnection(con, pstm, rs);
		}
	}
	
	
	@Override
	public List<VendasProdutosDTO> pesquisarProdutosVenda(Integer idVenda) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT P.nCdProduto, P.sCdProduto, P.sNmProduto, P.dVlVenda, VP.nNrQuantidade, VP.nCdVendaProduto "
					+ "FROM venda_produto VP " 
					+ "INNER JOIN produto P ON VP.nCdProduto = P.nCdProduto "
					+ "WHERE VP.nCdVenda = ?;"
			);
			
			pstm.setInt(1, idVenda);			
			rs = pstm.executeQuery();
				
			List<VendasProdutosDTO> list = new ArrayList<VendasProdutosDTO>();
			
			while(rs.next()) {
				VendasProdutosDTO vendasProdutosDTO = instanciarvendasProdutosDTO(rs);
				list.add(vendasProdutosDTO);
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

	private VendasProdutosDTO instanciarvendasProdutosDTO(ResultSet rs) throws SQLException {
		VendasProdutosDTO vendasProdutosDTO = new VendasProdutosDTO();
		
		vendasProdutosDTO.setIdVendasProdutos(rs.getInt("nCdVendaProduto"));
		vendasProdutosDTO.setIdProduto(rs.getInt("nCdProduto"));
		vendasProdutosDTO.setCodigoProduto(rs.getString("sCdProduto"));
		vendasProdutosDTO.setNomeProduto(rs.getString("sNmProduto"));
		vendasProdutosDTO.setValorVendaProduto(rs.getDouble("dVlVenda"));
		vendasProdutosDTO.setQuantidadeVendasProdutos(rs.getInt("nNrQuantidade"));
		
		return vendasProdutosDTO;
		
	}

	

}
