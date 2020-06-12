package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.CargoService;
import model.services.EstoqueService;
import model.services.FornecedorService;
import model.services.FuncionarioService;
import model.services.RelatoriosService;
import model.services.VendaService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import postgreBd.BdException;

public class DashboardController implements Initializable {

	RelatoriosService relatoriosService;
	
	@FXML
	private MenuItem menuItemEstoque;
	@FXML
	private MenuItem menuItemVendas;
	@FXML
	private MenuItem menuItemFuncionarios;
	@FXML
	private MenuItem menuItemFornecedores;
	@FXML
	private MenuItem menuItemCargos;
	@FXML
	private MenuItem menuItemRelatorioVendasProduto;
	@FXML
	private MenuItem menuItemRelatorioVendasFuncionario;
	@FXML
	private MenuItem menuItemRelatorioVendasDataEntrada;
	@FXML
	private MenuItem menuItemRelatorioEstoqueValidade;

	@FXML
	public void onMenuItemEstoqueAction() {
		carregarTelaEstoque("/gui/EstoqueList.fxml");
	}	
	@FXML
	public void onMenuItemVendasAction() {
		carregarTelaVendas("/gui/VendasList.fxml");
	}	
	@FXML
	public void onMenuItemFuncionariosAction() {
		carregarTelaFuncionarios("/gui/FuncionariosList.fxml");
	}	
	@FXML
	public void onMenuItemFornecedoresAction() {
		carregarTelaFornecedores("/gui/FornecedoresList.fxml");
	}	
	@FXML
	public void onMenuItemCargosAction() {
		carregarTelaCargos("/gui/CargosList.fxml");
	}	
	@FXML
	public void onmenuItemRelatorioVendasProduto() {	
		try {
			ResultSet rs = relatoriosService.vendasProduto();
			
			JRResultSetDataSource relatorioResult = new JRResultSetDataSource(rs);
			JasperPrint jasperPrint = JasperFillManager.fillReport("Relatorios/VendasProdutos.jasper", new HashMap<>(), relatorioResult);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setVisible(true);
			jasperViewer.toFront();
		}
		catch (BdException e) {
			e.printStackTrace();
		}
		catch (JRException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void onmenuItemRelatorioVendasFuncionario() {
		try {
			ResultSet rs = relatoriosService.vendasFuncionario();
			
			JRResultSetDataSource relatorioResult = new JRResultSetDataSource(rs);
			JasperPrint jasperPrint = JasperFillManager.fillReport("Relatorios/VendasFuncionario.jasper", new HashMap<>(), relatorioResult);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setVisible(true);
			jasperViewer.toFront();
		}
		catch (BdException e) {
			e.printStackTrace();
		}
		catch (JRException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void onmenuItemRelatorioVendasDataEntrada() {
		try {
			ResultSet rs = relatoriosService.vendasData();
			
			JRResultSetDataSource relatorioResult = new JRResultSetDataSource(rs);
			JasperPrint jasperPrint = JasperFillManager.fillReport("Relatorios/VendasData.jasper", new HashMap<>(), relatorioResult);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setVisible(true);
			jasperViewer.toFront();
		}
		catch (BdException e) {
			e.printStackTrace();
		}
		catch (JRException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void onmenuItemRelatorioEstoqueValidade() {
		try {
			ResultSet rs = relatoriosService.estoqueValidade();
			
			JRResultSetDataSource relatorioResult = new JRResultSetDataSource(rs);
			JasperPrint jasperPrint = JasperFillManager.fillReport("Relatorios/EstoqueValidade.jasper", new HashMap<>(), relatorioResult);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setVisible(true);
			jasperViewer.toFront();
		}
		catch (BdException e) {
			e.printStackTrace();
		}
		catch (JRException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		relatoriosService = new RelatoriosService();		
	}	
	
	/*ESTUDAR ESSE METODO NOVAMENTE
	private synchronized <T> void carregarTela(String caminho, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//Aula 272
			T controller = loader.getController();
			initializingAction.accept(controller);
						
		} catch (IOException e) {
			Alertas.mostrarAlerta("IOExcecao", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}*/
	
	private void carregarTelaEstoque(String caminho) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			EstoqueListController estoqueListController = loader.getController();
			estoqueListController.setEstoqueService(new EstoqueService());
			estoqueListController.updateTableView(null);
			
		} 
		catch (IOException e) {
			Alertas.mostrarAlerta("IOExcecao", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}

	private void carregarTelaVendas(String caminho) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			VBox newVBox = (VBox) loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			VendasListController vendasListController  = loader.getController();
			vendasListController.setVendaService(new VendaService());
			vendasListController.updateTableView(null);
			
		} 
		catch (IOException e) {
			Alertas.mostrarAlerta("IOExcecao", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void carregarTelaFuncionarios(String caminho) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			VBox newVBox = (VBox) loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			FuncionariosListController funcionarioListController = loader.getController();
			funcionarioListController.setFuncionarioService(new FuncionarioService());
			funcionarioListController.updateTableView(null);

			
		} 
		catch (IOException e) {
			Alertas.mostrarAlerta("IOExcecao", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void carregarTelaFornecedores(String caminho) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			FornecedoresListController fornecedoresListController = loader.getController();
			fornecedoresListController.setFornecedorService(new FornecedorService());
			fornecedoresListController.updateTableView(null);
			
		} 
		catch (IOException e) {
			Alertas.mostrarAlerta("IOExcecao", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}	
	
	private void carregarTelaCargos(String caminho) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			CargoListController cargoListController = loader.getController();
			cargoListController.setCargoService(new CargoService());
			cargoListController.updateTableView();
			
		} 
		catch (IOException e) {
			Alertas.mostrarAlerta("IOExcecao", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}	
	
}
