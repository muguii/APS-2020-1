package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.RelatoriosDao;
import postgreBd.BdConnection;
import postgreBd.BdException;

public class RelatoriosDaoJDBC implements RelatoriosDao{

	private Connection con;
	
	@Override
	public ResultSet vendasProduto() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT P.sNmProduto AS Nome_Produto, "
					 + "Count(VP.nCdProduto) AS Quantidade_Vendida, "
					 + "SUM(P.dVlVenda) AS Receita_Bruta, " 
					 + "SUM(P.dVlVenda - P.dVlCompra) AS Receita_Liquida "
					 + "FROM produto P "
					 + "LEFT JOIN venda_produto VP ON P.nCdProduto = VP.nCdProduto "
					 + "GROUP BY P.sNmProduto "
					 + "ORDER BY Receita_Liquida DESC;"
			);
			
			rs = pstm.executeQuery();
			return rs;

		} 
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		} 
		finally {
			BdConnection.closeConnection(con);
		}
	}

	@Override
	public ResultSet vendasFuncionario() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT F.sNmFuncionario AS Nome_Funcionario, " 
					 + "SUM(V.dVlTotal) AS Valor_Total_de_Vendas " 
					 + "FROM funcionario F " 
					 + "INNER JOIN venda V ON F.nCdFuncionario = V.nCdFuncionario "
					 + "GROUP BY F.sNmFuncionario " 
					 + "ORDER BY Valor_Total_de_Vendas DESC;"
			);
			
			rs = pstm.executeQuery();
			return rs;

		} 
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		} 
		finally {
			BdConnection.closeConnection(con);
		}
	}

	@Override
	public ResultSet vendasData() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT E.tDtEntrada AS Data_Entrada, "
					+ "P.sNmProduto AS Nome_Produto, "
					+ "E.nNrQuantidade AS Quantidade, "
					+ "SUM(P.dvlcompra * E.nNrQuantidade) AS Valor_de_Compra_Total, "
					+ "SUM(P.dVlVenda * E.nNrQuantidade) AS Valor_de_Venda_Total, "
					+ "SUM((P.dVlVenda * E.nNrQuantidade) - (P.dvlcompra * E.nNrQuantidade)) AS Receita_Liquida "
					+ "FROM estoque E " 
					+ "INNER JOIN produto P ON E.nCdProduto = P.nCdProduto " 
					+ "GROUP BY E.tDtEntrada, P.sNmProduto, E.nNrQuantidade " 
					+ "ORDER BY E.tDtEntrada, P.sNmProduto ASC;"
			);
			
			rs = pstm.executeQuery();
			return rs;

		} 
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		} 
		finally {
			BdConnection.closeConnection(con);
		}
	}
	

	@Override
	public ResultSet estoqueValidade() {
		con = BdConnection.getConnection();
		PreparedStatement pstm = null;	
		ResultSet rs = null;
		
		try {
			pstm = con.prepareStatement(
					  "SELECT p.sNmProduto AS Nome_Produto, " 
					 + "E.nNrQuantidade AS Quantidade_Estoque, "
					 + "P.tDtValidade AS Data_Validade, " 
					 + "P.dVlCompra AS Valor_de_Compra, " 
					 + "P.dVlVenda Valor_de_Venda "
					 + "FROM produto P "
					 + "INNER JOIN estoque E ON P.nCdProduto = E.nCdProduto "
					 + "ORDER BY P.tDtValidade ASC;"
			);
			
			rs = pstm.executeQuery();
			return rs;

		} 
		catch (SQLException e) {
			throw new BdException(e.getMessage());
		} 
		finally {
			BdConnection.closeConnection(con);
		}
	}

}
