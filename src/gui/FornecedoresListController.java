package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.beans.binding.Bindings;
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
import model.entidades.Fornecedor;
import model.entidades.FornecedorDTO;
import model.services.EnderecoService;
import model.services.FornecedorService;
import postgreBd.BdException;

public class FornecedoresListController implements Initializable, DataChangeListener{

	private FornecedorService fornecedorService;
	private ObservableList<FornecedorDTO> obsList;
	
	public void setFornecedorService(FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}
	
	@FXML
	private TableView<FornecedorDTO> tableViewFornecedor;
	@FXML
	private TableColumn<FornecedorDTO, FornecedorDTO> tableColumnEditar;
	@FXML
	private TableColumn<FornecedorDTO, FornecedorDTO> tableColumnRemover;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnNomeFantasia;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnCnpj;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnEmail;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnLogradouro;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnNumeroEndereco;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnComplemento;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnBairro;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnMunicipio;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnEstado;
	@FXML
	private TableColumn<FornecedorDTO, String> tableColumnTelefone;
	@FXML
	private Button btAdicionar;
	@FXML
	private TextField txtPesquisa;
	@FXML
	private Button btPesquisar;
	
	@FXML
	public void onBtAdicionarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Fornecedor fornecedor = new Fornecedor();
		createDialogForm(fornecedor, "/gui/FornecedorForm.fxml", parentStage);
	}
	@FXML
	public void onBtPesquisarAction() {
		String fornecedorNomeFantasia = txtPesquisa.getText();		
		updateTableView(fornecedorNomeFantasia);
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
		tableColumnNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasiaFornecedor"));
		tableColumnCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpjFornecedor"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("emailFornecedor"));
		tableColumnLogradouro.setCellValueFactory(new PropertyValueFactory<>("logradouroEndereco"));
		tableColumnNumeroEndereco.setCellValueFactory(new PropertyValueFactory<>("numeroEndereco"));
		tableColumnComplemento.setCellValueFactory(new PropertyValueFactory<>("complementoEndereco"));
		tableColumnBairro.setCellValueFactory(new PropertyValueFactory<>("bairroEndereco"));
		tableColumnMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipioEndereco"));
		tableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("siglaEstado"));
		tableColumnTelefone.setCellValueFactory(cellData -> Bindings.concat(
				cellData.getValue().getDddTelefoneSP(), " ", cellData.getValue().getNumeroTelefoneSP())
		);
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewFornecedor.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView(String fornecedorNomeFantasia) {
		if (fornecedorService == null) {
			throw new IllegalStateException("Fornecedor Service não instanciado.");
		}
		
		List<FornecedorDTO> list = new ArrayList<FornecedorDTO>();
		
		if (fornecedorNomeFantasia == null) {
			list = fornecedorService.pesquisarTodos();
			obsList = FXCollections.observableArrayList(list);
		} else {
			list = fornecedorService.pesquisarPorNome(fornecedorNomeFantasia);
			obsList = FXCollections.observableArrayList(list);
		}
		
		tableViewFornecedor.setItems(obsList);
		
		initEditButtons();
		initRemoveButton();	
	}
	
	private void initEditButtons() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<FornecedorDTO, FornecedorDTO>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(FornecedorDTO fornecedorDTO, boolean empty) {
				super.updateItem(fornecedorDTO, empty);
				if (fornecedorDTO == null) {
					setGraphic(null);
					return;
				}			
				setGraphic(button);
				button.setOnAction(																	
					event -> createDialogForm(FornecedorDTO.converterParaFornecedor(fornecedorDTO), 
					"/gui/FornecedorForm.fxml", Utils.currentStage(event))
				);
			}
		});
	}
	
	private void initRemoveButton() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<FornecedorDTO, FornecedorDTO>() {
			private final Button button = new Button("Deletar");

			@Override
			protected void updateItem(FornecedorDTO fornecedor, boolean empty) {
				super.updateItem(fornecedor, empty);
				if (fornecedor == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removerProduto(FornecedorDTO.converterParaFornecedor(fornecedor)));
			}
		});
	}
	
	private void removerProduto(Fornecedor fornecedor) {
		Optional<ButtonType> result = Alertas.mostrarConfirmacao("Confirmação", "Você tem certeza que deseja deletar?");

		if (result.get() == ButtonType.OK) {
			if (fornecedorService == null) {
				throw new IllegalStateException("Produto Service não instanciado.");
			}
			
			try {
				fornecedorService.delete(fornecedor);
				updateTableView(null);
			} 
			catch (BdException e) {
				Alertas.mostrarAlerta("Erro ao deletar o funcionário!", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	private void createDialogForm(Fornecedor fornecedor, String caminho, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			Pane pane = loader.load();
		
			FornecedorFormController fornecedorFormController = loader.getController();
			fornecedorFormController.setFornecedor(fornecedor);
			fornecedorFormController.setServices(fornecedorService, new EnderecoService());
			fornecedorFormController.carregarObjetosRelacionados();
			fornecedorFormController.subscribeDataChangeListener(this);
			
			fornecedorFormController.updateFormData();
			
			Stage formStage = new Stage();
			formStage.setTitle("Dados do Fornecedor");
			formStage.setScene(new Scene(pane));
		    formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
			
		}
		catch (IOException e) {
			Alertas.mostrarAlerta("IO Exception", "Erro ao carregar o formulário de cadastro do fornecedor.", e.getMessage(), AlertType.ERROR);
		}
	}

}
