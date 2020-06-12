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
import model.entidades.Funcionario;
import model.entidades.FuncionarioDTO;
import model.services.CargoService;
import model.services.EnderecoService;
import model.services.FuncionarioService;
import postgreBd.BdException;

public class FuncionariosListController implements Initializable, DataChangeListener{

	private FuncionarioService funcionarioService;
	private ObservableList<FuncionarioDTO> obsList;
	
	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}
	
	
	@FXML
	private TableView<FuncionarioDTO> tableViewFuncionario;
	@FXML
	private TableColumn<FuncionarioDTO, FuncionarioDTO> tableColumnEditar;
	@FXML
	private TableColumn<FuncionarioDTO, FuncionarioDTO> tableColumnRemover;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnMatricula;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnNome;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnCargo;
	@FXML
	private TableColumn<FuncionarioDTO, LocalDate> tableColumnDataNascimento;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnCpf;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnEmail;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnLogradouro;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnNumeroEndereco;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnComplemento;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnBairro;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnMunicipio;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnEstado;
	@FXML
	private TableColumn<FuncionarioDTO, String> tableColumnTelefone;
	@FXML
	private Button btAdicionar;
	@FXML
	private TextField txtPesquisa;
	@FXML
	private Button btPesquisar;
	
	@FXML
	public void onBtAdicionarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Funcionario funcionario = new Funcionario();
		createDialogForm(funcionario, "/gui/FuncionarioForm.fxml", parentStage);
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
		tableColumnMatricula.setCellValueFactory(new PropertyValueFactory<>("matriculaFuncionario"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
		tableColumnCargo.setCellValueFactory(new PropertyValueFactory<>("nomeCargo"));
		tableColumnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimentoFuncionario"));
		Utils.formatTableColumnDate(tableColumnDataNascimento);
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpfFuncionario"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("emailFuncionario"));
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
		tableViewFuncionario.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView(String funcionarioNome) {
		if (funcionarioService == null) {
			throw new IllegalStateException("Funcionário Service não instanciado.");
		}
		
		List<FuncionarioDTO> list = new ArrayList<FuncionarioDTO>();
		
		if (funcionarioNome == null) {
			list = funcionarioService.pesquisarTodos();
			obsList = FXCollections.observableArrayList(list);
		} else {
			list = funcionarioService.pesquisarPorNome(funcionarioNome);
			obsList = FXCollections.observableArrayList(list);
		}
		
		tableViewFuncionario.setItems(obsList);
		
		initEditButtons();
		initRemoveButton();	
	}
	
	private void initEditButtons() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<FuncionarioDTO, FuncionarioDTO>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(FuncionarioDTO funcionarioDTO, boolean empty) {
				super.updateItem(funcionarioDTO, empty);
				if (funcionarioDTO == null) {
					setGraphic(null);
					return;
				}			
				setGraphic(button);
				button.setOnAction(																	
					event -> createDialogForm(FuncionarioDTO.converterParaFuncionario(funcionarioDTO), 
					"/gui/FuncionarioForm.fxml", Utils.currentStage(event))
				);
			}
		});
	}
	
	private void initRemoveButton() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<FuncionarioDTO, FuncionarioDTO>() {
			private final Button button = new Button("Deletar");

			@Override
			protected void updateItem(FuncionarioDTO funcionarioDTO, boolean empty) {
				super.updateItem(funcionarioDTO, empty);
				if (funcionarioDTO == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removerProduto(FuncionarioDTO.converterParaFuncionario(funcionarioDTO)));
			}
		});
	}
	
	private void removerProduto(Funcionario funcionario) {
		Optional<ButtonType> result = Alertas.mostrarConfirmacao("CONFIRMAÇÃO", "VOCÊ TEM CERTEZA QUE DESEJA DELETAR? \n\n"
				+ "Se este funcionário tiver vendas efetuadas, todas as vendas serão apagadas!");

		if (result.get() == ButtonType.OK) {
			if (funcionarioService == null) {
				throw new IllegalStateException("Produto Service não instanciado.");
			}
			
			try {
				funcionarioService.delete(funcionario);
				updateTableView(null);
			} 
			catch (BdException e) {
				Alertas.mostrarAlerta("Erro ao deletar o funcionário!", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	private void createDialogForm(Funcionario funcionario, String caminho, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			Pane pane = loader.load();
		
			FuncionarioFormController funcionarioFormController = loader.getController();
			funcionarioFormController.setFuncionario(funcionario);
			funcionarioFormController.setServices(funcionarioService, new EnderecoService(), new CargoService());
			funcionarioFormController.carregarObjetosRelacionados();
			funcionarioFormController.subscribeDataChangeListener(this);
			
			funcionarioFormController.updateFormData();
			
			Stage formStage = new Stage();
			formStage.setTitle("Dados do Funcionário");
			formStage.setScene(new Scene(pane));
		    formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
			
		}
		catch (IOException e) {
			Alertas.mostrarAlerta("IO Exception", "Erro ao carregar o formulário de cadastro do funcionário.", e.getMessage(), AlertType.ERROR);
		}
	}
}
