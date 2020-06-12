package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.EnderecoDao;
import model.entidades.Endereco;
import model.entidades.Estado;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class EnderecoDaoJDBC implements EnderecoDao{

	private Connection con;
	
	@Override
	public void insert(Endereco endereco) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"INSERT INTO endereco "
					+ "(nCdTelefone, nCdEstado, sNmMunicipio, sDsLogradouro, "
					+ "sNrEndereco, sDsComplemento, sNmBairro, sNrCep) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
					
			pstm.setInt(1, endereco.getTelefone().getId());
			pstm.setInt(2, endereco.getEstado().getId());
			pstm.setString(3, endereco.getMunicipio());
			pstm.setString(4, endereco.getLogradouro());
			pstm.setString(5, endereco.getNumero());
			pstm.setString(6, endereco.getComplemento());
			pstm.setString(7, endereco.getBairro());
			pstm.setString(8, endereco.getCep());
			
			int rowsAffected = pstm.executeUpdate();	
			
			if(rowsAffected > 0) {
				ResultSet rs = pstm.getGeneratedKeys();				
				if (rs.next()) {
					Integer id = rs.getInt(1);
					endereco.setId(id);
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
	public void update(Endereco endereco) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"UPDATE endereco SET "
					+ "nCdTelefone = ?, "
					+ "nCdEstado = ?, "
					+ "sNmMunicipio = ?, "
					+ "sDsLogradouro = ?, "
					+ "sNrEndereco = ?, "
					+ "sDsComplemento = ?, "
					+ "sNmBairro = ?, "
					+ "sNrCep = ? "
					+ "WHERE nCdEndereco = ?;"
			);
			
			pstm.setInt(1, endereco.getTelefone().getId());
			pstm.setInt(2, endereco.getEstado().getId());
			pstm.setString(3, endereco.getMunicipio());
			pstm.setString(4, endereco.getLogradouro());
			pstm.setString(5, endereco.getNumero());
			pstm.setString(6, endereco.getComplemento());
			pstm.setString(7, endereco.getBairro());
			pstm.setString(8, endereco.getCep());
			pstm.setInt(9, endereco.getId());
			
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
			pstm = con.prepareStatement("DELETE FROM endereco WHERE nCdEndereco = ?;");
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

	public List<Estado> pesquisarTodosEstados(){
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement("SELECT * FROM estado ORDER BY sNmEstado ASC;");
			rs = pstm.executeQuery();
			
			List<Estado> list = new ArrayList<Estado>();
			
			while(rs.next()) {
				Estado estado = instanciarEstado(rs);
				list.add(estado);				
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
	
	private Estado instanciarEstado(ResultSet rs) throws SQLException  {
		Estado estado = new Estado();
		
		estado.setId(rs.getInt("nCdEstado"));
		estado.setNome(rs.getString("sNmEstado"));
		estado.setSigla(rs.getString("sSgEstado"));
		
		return estado;
	}
}
