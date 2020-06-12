package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.VendaDTO;
import model.services.FuncionarioService;
import model.services.ProdutoService;
import model.services.VendaService;
import model.services.VendasProdutosService;

public class VendasListController implements Initializable, DataChangeListener{

	private VendaService vendaService;
	private ObservableList<VendaDTO> obsList;
	
	public void setVendaService(VendaService vendaService) {
		this.vendaService = vendaService;
	}
	
	
	@FXML
	private TableView<VendaDTO> tableViewVenda;
	@FXML
	private TableColumn<VendaDTO, VendaDTO> tableColumnDetalhes;
	@FXML
	private TableColumn<VendaDTO, Integer> tableColumnIdVenda;
	@FXML
	private TableColumn<VendaDTO, String> tableColumnNomeFuncionario;
	@FXML
	private TableColumn<VendaDTO, Double> tableColumnValorTotal;
	@FXML
	private TableColumn<VendaDTO, String> tableColumnFormaPagamento;
	@FXML
	private TableColumn<VendaDTO, LocalDate> tableColumnDataVenda;
	@FXML
	private TextField txtVendasTotal;
	@FXML
	private Button btAdicionar;
	@FXML
	private TextField txtPesquisa;
	@FXML
	private Button btPesquisar;
	
	@FXML
	public void onBtAdicionarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createDialogForm("/gui/VendaForm.fxml", parentStage);
	}
	@FXML
	public void onBtPesquisarAction() {
		String funcionarioNome = txtPesquisa.getText();		
		updateTableView(funcionarioNome);
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();		
	}
	
	@Override
	public void onDataChanged() {
		updateTableView(null);
	}
	
	
	private void initializeNodes() {
		tableColumnIdVenda.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
		tableColumnNomeFuncionario.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
		tableColumnValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotalVenda"));
		tableColumnFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamentoVenda"));
		tableColumnDataVenda.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
		Utils.formatTableColumnDate(tableColumnDataVenda);
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewVenda.prefHeightProperty().bind(stage.heightProperty());	
	}
	
	public void updateTableView(String funcionarioNome) {
		if (vendaService == null) {
			throw new IllegalStateException("Services não instanciados.");
		}
		
		List<VendaDTO> list = new ArrayList<VendaDTO>();
		
		if (funcionarioNome == null) {
			list = vendaService.pesquisarTodos();
			obsList = FXCollections.observableArrayList(list);
		} else {
			list = vendaService.pesquisarPorNomeFuncionario(funcionarioNome);
			obsList = FXCollections.observableArrayList(list);
		}
		
		tableViewVenda.setItems(obsList);
		updateValorTotal(list);
		initDetailsButton();
	}

	private void initDetailsButton() {
		tableColumnDetalhes.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnDetalhes.setCellFactory(param -> new TableCell<VendaDTO, VendaDTO>() {
			Button detailsButton = new Button("Detalhes");

				@Override
				protected void updateItem(VendaDTO vendaDTO, boolean empty) {
					super.updateItem(vendaDTO, empty);
					if (vendaDTO == null) {
						setGraphic(null);
						return;
					}
					setGraphic(detailsButton);
					detailsButton.setOnAction(																	
						event -> carregarVendaDetalhes(vendaDTO.getIdVenda(), "/gui/VendaDetalhes.fxml", Utils.currentStage(event))
					);
				}			
		});
	}
	
	private void updateValorTotal(List<VendaDTO> list) {
		Double total = 0.0;
		for (VendaDTO obj : list) {
			total += obj.getValorTotalVenda();
		}
		
		Locale.setDefault(Locale.US);
		txtVendasTotal.setText(String.format("%.2f", total));
	}
	
	
	private void createDialogForm(String caminho, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			Pane pane = loader.load();
		
			VendaFormController vendaFormController = loader.getController();
			vendaFormController.setServices(vendaService, new FuncionarioService(), new ProdutoService(), new VendasProdutosService());
			vendaFormController.carregarObjetosRelacionados();
			vendaFormController.subscribeDataChangeListener(this);
			
			Stage formStage = new Stage();
			formStage.setTitle("Dados da Venda");
			formStage.setScene(new Scene(pane));
		    formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
			
		}
		catch (IOException e) {
			Alertas.mostrarAlerta("IO Exception", "Erro ao carregar o formulário de cadastro de venda.", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void carregarVendaDetalhes(Integer idVenda, String caminho, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			Pane pane = loader.load();		
			
			VendaDetalhesController vendaDetalhesController = loader.getController();
			vendaDetalhesController.setService(new VendasProdutosService());
			vendaDetalhesController.loadData(idVenda);
			
			Stage formStage = new Stage();
			formStage.setTitle("Detalhes da Venda");
			formStage.setScene(new Scene(pane));
		    formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
			
		}
		catch (IOException e) {
			Alertas.mostrarAlerta("IO Exception", "Erro ao carregar o formulário de cadastro de venda.", e.getMessage(), AlertType.ERROR);
		}
	}
	

}
