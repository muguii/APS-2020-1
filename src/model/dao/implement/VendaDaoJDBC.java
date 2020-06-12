package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.VendaDao;
import model.entidades.Venda;
import model.entidades.VendaDTO;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class VendaDaoJDBC implements VendaDao{

	private Connection con;
	
	@Override
	public void insert(Venda venda) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					  "INSERT INTO venda (nCdFuncionario, dVlTotal, sDsFormaPagamento, tDtVenda) "
					+ "VALUES (?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
			
			pstm.setInt(1, venda.getFuncionario().getId());
			pstm.setDouble(2, venda.getValorTotal());
			pstm.setString(3, venda.getFormaPagamento());
			pstm.setDate(4, java.sql.Date.valueOf(venda.getDataVenda()));
			
			int rowsAffected = pstm.executeUpdate();	
			
			if(rowsAffected > 0) {
				ResultSet rs = pstm.getGeneratedKeys();				
				if (rs.next()) {
					Integer id = rs.getInt(1);
					venda.setId(id);
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
	public List<VendaDTO> pesquisarTodos() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT V.nCdVenda, V.nCdFuncionario, F.sNmFuncionario, V.dVlTotal, "
					+ "V.sDsFormaPagamento, V.tDtVenda "
					+ "FROM venda V " 
					+ "INNER JOIN funcionario F ON V.nCdFuncionario = F.nCdFuncionario;"
			);
			
			rs = pstm.executeQuery();
				
			List<VendaDTO> list = new ArrayList<VendaDTO>();
			
			while(rs.next()) {
				VendaDTO vendaDTO = instanciarVendaDTO(rs);
				list.add(vendaDTO);
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
	public List<VendaDTO> pesquisarPorNomeFuncionario(String nomeFuncionario) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT V.nCdVenda, V.nCdFuncionario, F.sNmFuncionario, V.dVlTotal, "
					+ "V.sDsFormaPagamento, V.tDtVenda "
					+ "FROM venda V " 
					+ "INNER JOIN funcionario F ON V.nCdFuncionario = F.nCdFuncionario "
					+ "WHERE UPPER(F.sNmFuncionario) LIKE UPPER(?);"
			);
			
			pstm.setString(1, '%' + nomeFuncionario + '%');					
			rs = pstm.executeQuery();
				
			List<VendaDTO> list = new ArrayList<VendaDTO>();
			
			while(rs.next()) {
				VendaDTO vendaDTO = instanciarVendaDTO(rs);
				list.add(vendaDTO);
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

	private VendaDTO instanciarVendaDTO(ResultSet rs) throws SQLException {
		VendaDTO vendaDTO = new VendaDTO();
		
		vendaDTO.setIdVenda(rs.getInt("nCdVenda"));
		vendaDTO.setValorTotalVenda(rs.getDouble("dVlTotal"));
		vendaDTO.setFormaPagamentoVenda(rs.getString("sDsFormaPagamento"));
		vendaDTO.setDataVenda(rs.getDate("tDtVenda").toLocalDate());
		vendaDTO.setIdFuncionario(rs.getInt("nCdFuncionario"));
		vendaDTO.setNomeFuncionario(rs.getString("sNmFuncionario"));
		
		return vendaDTO;
	}	

}
