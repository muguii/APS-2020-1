package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.dao.TelefoneDao;
import model.entidades.Telefone;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class TelefoneDaoJDBC implements TelefoneDao{

	private Connection con;
	
	@Override
	public void insert(Telefone telefone) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"INSERT INTO telefone (sNrDdd, sNrTelefone) VALUES (?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
					
			pstm.setString(1, telefone.getDdd());
			pstm.setString(2, telefone.getTelefone());
			
			int rowsAffected = pstm.executeUpdate();	
			
			if(rowsAffected > 0) {
				ResultSet rs = pstm.getGeneratedKeys();				
				if (rs.next()) {
					Integer id = rs.getInt(1);
					telefone.setId(id);
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
	public void update(Telefone telefone) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"UPDATE telefone SET "
					+ "sNrDdd = ?, "
					+ "sNrTelefone = ? "
					+ "WHERE nCdTelefone = ?;");
					
			pstm.setString(1, telefone.getDdd());
			pstm.setString(2, telefone.getTelefone());
			pstm.setInt(3, telefone.getId());
			
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
			pstm = con.prepareStatement("DELETE FROM telefone WHERE nCdTelefone = ?;");
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

}
