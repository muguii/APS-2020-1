package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.CargoDao;
import model.entidades.Cargo;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class CargoDaoJDBC implements CargoDao{

	private Connection con;
	
	@Override
	public void insert(Cargo cargo) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"INSERT INTO cargo (sNmCargo, dVlSalario) VALUES (?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
			
			pstm.setString(1, cargo.getNome());
			pstm.setDouble(2, cargo.getSalario());
			
			int rowsAffected = pstm.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = pstm.getGeneratedKeys();				
				if (rs.next()) {
					Integer id = rs.getInt(1);
					cargo.setId(id);
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
	public void update(Cargo cargo) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement("UPDATE cargo SET sNmCargo = ?, dVlSalario = ? WHERE nCdCargo = ?;");
			
			pstm.setString(1, cargo.getNome());
			pstm.setDouble(2, cargo.getSalario());
			pstm.setInt(3, cargo.getId());

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
			pstm = con.prepareStatement("DELETE FROM cargo WHERE nCdCargo = ?;");
			
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
	public List<Cargo> pesquisarTodos() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement("SELECT * FROM cargo ORDER BY sNmCargo ASC;");
			rs = pstm.executeQuery();
			
			List<Cargo> list = new ArrayList<Cargo>();
			
			while(rs.next()) {
				Cargo cargo = instanciarCargo(rs);
				list.add(cargo);
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

	private Cargo instanciarCargo(ResultSet rs) throws SQLException {
		Cargo cargo = new Cargo();
		
		cargo.setId(rs.getInt("nCdCargo"));
		cargo.setNome(rs.getString("sNmCargo"));
		cargo.setSalario(rs.getDouble("dVlSalario"));
		
		return cargo;
	}
	
	

}
