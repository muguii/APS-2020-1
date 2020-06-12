package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Estoque;
import model.entidades.EstoqueDTO;
import model.services.EstoqueService;
import model.services.ProdutoService;
import postgreBd.BdException;

public class EstoqueListController implements Initializable, DataChangeListener{

	private EstoqueService estoqueService;
	private ObservableList<EstoqueDTO> obsList;
	
	
	public void setEstoqueService(EstoqueService estoqueService) {
		this.estoqueService = estoqueService;
	}
	
	@FXML
	private TableView<EstoqueDTO> tableViewEstoque;
	@FXML
	private TableColumn<EstoqueDTO, EstoqueDTO> tableColumnEditar;
	@FXML
	private TableColumn<EstoqueDTO, EstoqueDTO> tableColumnRemover;
	@FXML
	private TableColumn<EstoqueDTO, String> tableColumnCodigo;
	@FXML
	private TableColumn<EstoqueDTO, String> tableColumnNomeProduto;
	@FXML
	private TableColumn<EstoqueDTO, String> tableColumnUnidadeMedida;
	@FXML
	private TableColumn<EstoqueDTO, String> tableColumnQuantidade;
	@FXML
	private TableColumn<EstoqueDTO, LocalDate> tableColumnDataValidade;
	@FXML
	private TableColumn<EstoqueDTO, Double> tableColumnValorCompra;
	@FXML
	private TableColumn<EstoqueDTO, Double> tableColumnValorVenda;
	@FXML
	private TableColumn<EstoqueDTO, LocalDate> tableColumnDataFabricacao;
	@FXML
	private TableColumn<EstoqueDTO, LocalDate> tableColumnDataEntradaEstoque;
	@FXML
	private Button btAdicionar;
	@FXML
	private TextField txtPesquisa;
	@FXML
	private Button btPesquisar;
	
	@FXML
	public void onBtAdicionarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Estoque estoque = new Estoque();
		createDialogForm(estoque, "/gui/ProdutoForm.fxml", parentStage);
	}
	@FXML
	public void onBtPesquisarAction(ActionEvent event) {
		String produtoNome = txtPesquisa.getText();
		updateTableView(produtoNome);
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
		tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoProduto"));
		tableColumnNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tableColumnUnidadeMedida.setCellValueFactory(new PropertyValueFactory<>("unidadeMedidaProduto"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));
		tableColumnDataValidade.setCellValueFactory(new PropertyValueFactory<>("dataValidadeproduto"));
		Utils.formatTableColumnDate(tableColumnDataValidade);
		tableColumnValorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompraProduto"));
		tableColumnValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVendaProduto"));
		tableColumnDataFabricacao.setCellValueFactory(new PropertyValueFactory<>("dataFabricacaoProduto"));
		Utils.formatTableColumnDate(tableColumnDataFabricacao);
		tableColumnDataEntradaEstoque.setCellValueFactory(new PropertyValueFactory<>("dataEntradaEstoque"));
		Utils.formatTableColumnDate(tableColumnDataEntradaEstoque);
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewEstoque.prefHeightProperty().bind(stage.heightProperty());	
	}
	
	public void updateTableView(String nomeProduto) {
		if (estoqueService == null) {
			throw new IllegalStateException("Estoque Service não instanciado.");
		}
		
		List<EstoqueDTO> list = new ArrayList<EstoqueDTO>();
		
		if (nomeProduto == null) {
			list = estoqueService.pesquisarTodos();
			obsList = FXCollections.observableArrayList(list);
		} else {
			list = estoqueService.pesquisarPorNome(nomeProduto);
			obsList = FXCollections.observableArrayList(list);
		}
		
		tableViewEstoque.setItems(obsList);	
		
		initEditButtons();
		initRemoveButton();
	}
	
	private void initEditButtons() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<EstoqueDTO, EstoqueDTO>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(EstoqueDTO estoque, boolean empty) {
				super.updateItem(estoque, empty);
				if (estoque == null) {
					setGraphic(null);
					return;
				}			
				setGraphic(button);
				button.setOnAction(																	
					event -> createDialogForm(EstoqueDTO.converterParaEstoque(estoque), 
					"/gui/ProdutoForm.fxml", Utils.currentStage(event))
				);
			}
		});
	}
	
	private void initRemoveButton() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<EstoqueDTO, EstoqueDTO>() {
			private final Button button = new Button("Deletar");

			@Override
			protected void updateItem(EstoqueDTO estoque, boolean empty) {
				super.updateItem(estoque, empty);
				if (estoque == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removerProduto(EstoqueDTO.converterParaEstoque(estoque)));
			}
		});
	}
	
	private void removerProduto(Estoque estoque) {
		Optional<ButtonType> result = Alertas.mostrarConfirmacao("CONFIRMAÇÃO", "VOCÊ TEM CERTEZA QUE DESEJA DELETAR? \n\n"
				+ "Se este produto tiver alguma venda relacionada, irá apagar todos os registros de vendas deste produto!");

		if (result.get() == ButtonType.OK) {
			if (estoqueService == null) {
				throw new IllegalStateException("Estoque Service não instanciado.");
			}
			
			try {
				estoqueService.delete(estoque);
				updateTableView(null);
			} 
			catch (BdException e) {
				Alertas.mostrarAlerta("Erro ao deletar o produto do estoque!", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	private void createDialogForm(Estoque estoque, String caminho, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			Pane pane = loader.load();
		
			ProdutoFormController produtoFormController = loader.getController();
			produtoFormController.setProduto_Estoque(estoque);
			produtoFormController.setServices(estoqueService, new ProdutoService());
			produtoFormController.subscribeDataChangeListener(this);
			
			produtoFormController.updateFormData();
			
			Stage formStage = new Stage();
			formStage.setTitle("Dados do Produto");
			formStage.setScene(new Scene(pane));
		    formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
			
		}
		catch (IOException e) {
			Alertas.mostrarAlerta("IO Exception", "Erro ao carregar o formulário de cadastro de produto.", e.getMessage(), AlertType.ERROR);
		}
	}

}
