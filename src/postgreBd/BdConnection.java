package postgreBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BdConnection {

	private static final String BDURL = "jdbc:postgresql://localhost:5432/APS2020-1_Dev";
	private static final String USUARIO = "AdminAPS";
	private static final String SENHA = "123456";
	private static final String DRIVERJDBC = "org.postgresql.Driver";


	public static Connection getConnection() {	
			try {
				Class.forName(DRIVERJDBC);
				return DriverManager.getConnection(BDURL, USUARIO, SENHA);
			} catch (SQLException e) {
				throw new BdException("Erro na conexão com o Banco de Dados. " + e.getMessage());
			} catch (ClassNotFoundException e) {
				throw new BdException("Erro na conexão com o Banco de Dados, classe não encontrada. " + e.getMessage());
			}
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new BdException("Erro ao fechar conexão com o Banco de Dados. " + e.getMessage());
			}
		}
	}

	public static void closeConnection(Connection con, PreparedStatement pstm) {
		closeConnection(con);
		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				throw new BdException("Erro ao fechar conexão com o Banco de Dados. " + e.getMessage());
			}
		}
	}

	public static void closeConnection(Connection con, PreparedStatement pstm, ResultSet rs) {
		closeConnection(con, pstm);
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new BdException("Erro ao fechar conexão com o Banco de Dados. " + e.getMessage());
			}
		}
	}
}
