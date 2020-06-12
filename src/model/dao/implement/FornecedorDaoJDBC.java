package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import model.dao.FornecedorDao;
import model.entidades.Fornecedor;
import model.entidades.FornecedorDTO;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class FornecedorDaoJDBC implements FornecedorDao{

	private Connection con;
	
	@Override
	public void insert(Fornecedor fornecedor) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"INSERT INTO fornecedor "
					+ "(nCdEndereco, sNmFantasia, sNrCnpj, sDsEmail) "
					+ "VALUES (?, ?, ?, ?);"
			);
						
			pstm.setInt(1, fornecedor.getEndereco().getId());
			pstm.setString(2, fornecedor.getNomeFantasia());
			pstm.setString(3, fornecedor.getCnpj());
			pstm.setString(4, fornecedor.getEmail());			
						
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
	public void update(Fornecedor fornecedor) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"UPDATE fornecedor SET "
					+ "nCdEndereco = ?, "
					+ "sNmFantasia = ?, "
					+ "sNrCnpj = ?, "
					+ "sDsEmail = ? "
					+ "WHERE nCdFornecedor = ?;"
			);
			
			pstm.setInt(1, fornecedor.getEndereco().getId());					
			pstm.setString(2, fornecedor.getNomeFantasia());
			pstm.setString(3, fornecedor.getCnpj());
			pstm.setString(4, fornecedor.getEmail());
			pstm.setInt(5, fornecedor.getId());
			
			
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
	public List<FornecedorDTO> pesquisarTodos() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT F.nCdFornecedor, F.sNmFantasia, F.sNrCnpj, F.sDsEmail, E.nCdEndereco, E.sDsLogradouro, "
					+ "E.sNrCep, E.sNrEndereco, E.sDsComplemento, E.sNmBairro, E.sNmMunicipio, "
					+ "ES.nCdEstado, ES.sNmEstado, ES.sSgEstado, T.nCdTelefone, T.sNrDdd, T.sNrTelefone "
					+ "FROM fornecedor F "
					+ "INNER JOIN endereco E ON F.nCdEndereco = E.nCdEndereco "
					+ "INNER JOIN estado ES ON E.nCdEstado = ES.nCdEstado "
					+ "INNER JOIN telefone T ON E.nCdTelefone = T.nCdTelefone;"
			);
			
			rs = pstm.executeQuery();
						
			List<FornecedorDTO> list = new ArrayList<FornecedorDTO>();
			
			while(rs.next()) {
				FornecedorDTO fornecedorDTO = instanciarFornecedorDTO(rs);				
				list.add(fornecedorDTO);
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
	public List<FornecedorDTO> pesquisarPorNome(String nomeFantasia) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT F.nCdFornecedor, F.sNmFantasia, F.sNrCnpj, F.sDsEmail, E.nCdEndereco, E.sDsLogradouro, "
					+ "E.sNrCep, E.sNrEndereco, E.sDsComplemento, E.sNmBairro, E.sNmMunicipio, "
					+ "ES.nCdEstado, ES.sNmEstado, ES.sSgEstado, T.nCdTelefone, T.sNrDdd, T.sNrTelefone "
					+ "FROM fornecedor F "
					+ "INNER JOIN endereco E ON F.nCdEndereco = E.nCdEndereco "
					+ "INNER JOIN estado ES ON E.nCdEstado = ES.nCdEstado "
					+ "INNER JOIN telefone T ON E.nCdTelefone = T.nCdTelefone "
					+ "WHERE UPPER(F.sNmFantasia) LIKE UPPER(?);"
			);
			
			pstm.setString(1, '%' + nomeFantasia + '%');		
			rs = pstm.executeQuery();
						
			List<FornecedorDTO> list = new ArrayList<FornecedorDTO>();
			
			while(rs.next()) {			
				FornecedorDTO fornecedorDTO = instanciarFornecedorDTO(rs);				
				list.add(fornecedorDTO);
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
	
	private FornecedorDTO instanciarFornecedorDTO(ResultSet rs) throws SQLException {
		FornecedorDTO fornecedorDTO = new FornecedorDTO();
		
		fornecedorDTO.setIdFornecedor(rs.getInt("nCdFornecedor"));
		fornecedorDTO.setNomeFantasiaFornecedor(rs.getString("sNmFantasia"));
		fornecedorDTO.setCnpjFornecedor(rs.getString("sNrCnpj"));
		fornecedorDTO.setEmailFornecedor(rs.getString("sDsEmail"));
		
		fornecedorDTO.setIdEndereco(rs.getInt("nCdEndereco"));
		fornecedorDTO.setLogradouroEndereco(rs.getString("sDsLogradouro"));
		fornecedorDTO.setCepEndereco(rs.getString("sNrCep"));
		fornecedorDTO.setNumeroEndereco(rs.getString("sNrEndereco"));
		fornecedorDTO.setComplementoEndereco(rs.getString("sDsComplemento"));
		fornecedorDTO.setBairroEndereco(rs.getString("sNmBairro"));
		fornecedorDTO.setMunicipioEndereco(rs.getString("sNmMunicipio"));
		
		fornecedorDTO.setIdEstado(rs.getInt("nCdEstado"));
		fornecedorDTO.setNomeEstado(rs.getString("sNmEstado"));
		fornecedorDTO.setSiglaEstado(rs.getString("sSgEstado"));
		
		fornecedorDTO.setIdTelefone(rs.getInt("nCdTelefone"));
		fornecedorDTO.setDddTelefoneSP(new SimpleStringProperty(rs.getString("sNrDdd")));
		fornecedorDTO.setNumeroTelefoneSP(new SimpleStringProperty(rs.getString("sNrTelefone")));
		
		return fornecedorDTO;
		
	}

}
