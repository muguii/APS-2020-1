package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import model.dao.FuncionarioDao;
import model.entidades.Funcionario;
import model.entidades.FuncionarioDTO;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class FuncionarioDaoJDBC implements FuncionarioDao{

	private Connection con;
	
	@Override
	public void insert(Funcionario funcionario) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"INSERT INTO funcionario "
					+ "(nCdEndereco, nCdCargo, sNmFuncionario, sNrMatricula, sNrCpf, "
					+ "tDtNascimento, sDsEmail) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?);"
			);
						
			pstm.setInt(1, funcionario.getEndereco().getId());
			pstm.setInt(2, funcionario.getCargo().getId());
			pstm.setString(3, funcionario.getNome());
			pstm.setString(4, funcionario.getMatricula());
			pstm.setString(5, funcionario.getCpf());
			pstm.setDate(6, java.sql.Date.valueOf(funcionario.getDataNascimento()));
			pstm.setString(7, funcionario.getEmail());
			
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
	public void update(Funcionario funcionario) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		
		try {
			pstm = con.prepareStatement(
					"UPDATE funcionario SET "
					+ "nCdEndereco = ?, "
					+ "nCdCargo = ?, "
					+ "sNmFuncionario = ?, "
					+ "sNrMatricula = ?, "
					+ "sNrCpf = ?, "
					+ "tDtNascimento = ?, "
					+ "sDsEmail = ? "
					+ "WHERE nCdFuncionario = ?;"
			);
						
			pstm.setInt(1, funcionario.getEndereco().getId());
			pstm.setInt(2, funcionario.getCargo().getId());
			pstm.setString(3, funcionario.getNome());
			pstm.setString(4, funcionario.getMatricula());
			pstm.setString(5, funcionario.getCpf());
			pstm.setDate(6, java.sql.Date.valueOf(funcionario.getDataNascimento()));
			pstm.setString(7, funcionario.getEmail());
			pstm.setInt(8, funcionario.getId());
			
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
	public List<Funcionario> pesquisaSimples() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					"SELECT * FROM funcionario;"
			);
					
			rs = pstm.executeQuery();
			
			List<Funcionario> list = new ArrayList<Funcionario>();
			
			while(rs.next()) {							
				Funcionario funcionario = instanciarFuncionario(rs);			
				list.add(funcionario);
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
	public List<FuncionarioDTO> pesquisarTodos() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					"SELECT F.nCdFuncionario, F.sNrMatricula, F.sNmFuncionario, C.nCdCargo, C.sNmCargo, C.dVlSalario, "
					+ "F.tDtNascimento, F.sNrCpf, F.sDsEmail, E.nCdEndereco, E.sDsLogradouro, E.sNrCep, E.sNrEndereco, "
					+ "E.sDsComplemento, E.sNmBairro, E.sNmMunicipio, ES.nCdEstado, ES.sNmEstado, ES.sSgEstado, "
					+ "T.nCdTelefone, T.sNrDdd, T.sNrTelefone "
					+ "FROM funcionario F "
					+ "INNER JOIN cargo C ON F.nCdCargo = C.nCdCargo "
					+ "INNER JOIN endereco E ON F.nCdEndereco = E.nCdEndereco "
					+ "INNER JOIN estado ES ON E.nCdEstado = ES.nCdEstado "
					+ "INNER JOIN telefone T ON E.nCdTelefone = T.nCdTelefone;"
			);
					
			rs = pstm.executeQuery();
			
			List<FuncionarioDTO> list = new ArrayList<FuncionarioDTO>();
			
			while(rs.next()) {							
				FuncionarioDTO funcionarioDTO = instanciarFuncionarioDTO(rs);			
				list.add(funcionarioDTO);
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
	public List<FuncionarioDTO> pesquisarPorNome(String nome) {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					"SELECT F.nCdFuncionario, F.sNrMatricula, F.sNmFuncionario, C.nCdCargo, C.sNmCargo, C.dVlSalario, "
					+ "F.tDtNascimento, F.sNrCpf, F.sDsEmail, E.nCdEndereco, E.sDsLogradouro, E.sNrCep, E.sNrEndereco, "
					+ "E.sDsComplemento, E.sNmBairro, E.sNmMunicipio, ES.nCdEstado, ES.sNmEstado, ES.sSgEstado, "
					+ "T.nCdTelefone, T.sNrDdd, T.sNrTelefone "
					+ "FROM funcionario F "
					+ "INNER JOIN cargo C ON F.nCdCargo = C.nCdCargo "
					+ "INNER JOIN endereco E ON F.nCdEndereco = E.nCdEndereco "
					+ "INNER JOIN estado ES ON E.nCdEstado = ES.nCdEstado "
					+ "INNER JOIN telefone T ON E.nCdTelefone = T.nCdTelefone "
					+ "WHERE UPPER(F.sNmFuncionario) LIKE UPPER(?);"
			);
			
			pstm.setString(1, '%' + nome + '%');					
			rs = pstm.executeQuery();
			
			List<FuncionarioDTO> list = new ArrayList<FuncionarioDTO>();
			
			while(rs.next()) {							
				FuncionarioDTO funcionarioDTO = instanciarFuncionarioDTO(rs);			
				list.add(funcionarioDTO);
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

	private FuncionarioDTO instanciarFuncionarioDTO(ResultSet rs) throws SQLException {		
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		
		funcionarioDTO.setIdFuncionario(rs.getInt("nCdFuncionario"));
		funcionarioDTO.setNomeFuncionario(rs.getString("sNmFuncionario"));
		funcionarioDTO.setMatriculaFuncionario(rs.getString("sNrMatricula"));
		funcionarioDTO.setCpfFuncionario(rs.getString("sNrCpf"));
		funcionarioDTO.setDataNascimentoFuncionario(rs.getDate("tDtnascimento").toLocalDate());
		funcionarioDTO.setEmailFuncionario(rs.getString("sDsEmail"));
		
		funcionarioDTO.setIdEndereco(rs.getInt("nCdEndereco"));
		funcionarioDTO.setLogradouroEndereco(rs.getString("sDsLogradouro"));
		funcionarioDTO.setCepEndereco(rs.getString("sNrCep"));
		funcionarioDTO.setNumeroEndereco(rs.getString("sNrEndereco"));
		funcionarioDTO.setComplementoEndereco(rs.getString("sDsComplemento"));
		funcionarioDTO.setBairroEndereco(rs.getString("sNmBairro"));
		funcionarioDTO.setMunicipioEndereco(rs.getString("sNmMunicipio"));
		
		funcionarioDTO.setIdEstado(rs.getInt("nCdEstado"));
		funcionarioDTO.setNomeEstado(rs.getString("sNmEstado"));
		funcionarioDTO.setSiglaEstado(rs.getString("sSgEstado"));
		
		funcionarioDTO.setIdTelefone(rs.getInt("nCdTelefone"));
		funcionarioDTO.setDddTelefoneSP(new SimpleStringProperty(rs.getString("sNrDdd")));
		funcionarioDTO.setNumeroTelefoneSP(new SimpleStringProperty(rs.getString("sNrTelefone")));
		
		funcionarioDTO.setIdCargo(rs.getInt("nCdCargo"));
		funcionarioDTO.setNomeCargo(rs.getString("sNmCargo"));
		funcionarioDTO.setSalarioCargo(rs.getDouble("dVlSalario"));
		
		return funcionarioDTO;
	}
	
	private Funcionario instanciarFuncionario(ResultSet rs) throws SQLException {
		Funcionario funcionario = new Funcionario();
		
		funcionario.setId(rs.getInt("nCdFuncionario"));
		funcionario.setNome(rs.getString("sNmFuncionario"));
		funcionario.setMatricula(rs.getString("sNrMatricula"));
		funcionario.setCpf(rs.getString("sNrCpf"));
		funcionario.setDataNascimento(rs.getDate("tDtNascimento").toLocalDate());
		funcionario.setEmail(rs.getString("sDsEmail"));
		
		return funcionario;
	}
	

}
