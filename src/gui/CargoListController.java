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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Cargo;
import model.services.CargoService;
import postgreBd.BdException;

public class CargoListController implements Initializable, DataChangeListener{

	private CargoService cargoService;
	private ObservableList<Cargo> obsList;
	
	
	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}
	
	
	@FXML
	private TableView<Cargo> tableViewCargo;
	@FXML
	private TableColumn<Cargo, Cargo> tableColumnEditar;
	@FXML
	private TableColumn<Cargo, Cargo> tableColumnRemover;
	@FXML
	private TableColumn<Cargo, String> tableColumnCargoNome;
	@FXML
	private TableColumn<Cargo, Double> tableColumnSalario;
	@FXML
	private Button btAdicionar;
	
	@FXML
	public void onBtAdicionarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Cargo cargo = new Cargo();
		createDialogForm(cargo, "/gui/CargoForm.fxml", parentStage);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	@Override
	public void onDataChanged() {
		updateTableView();
	}
	
	private void initializeNodes() {
		tableColumnCargoNome.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nome"));
		tableColumnSalario.setCellValueFactory(new PropertyValueFactory<Cargo, Double>("salario"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewCargo.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (cargoService == null) {
			throw new IllegalStateException("Cargo Service não instanciado.");
		}
		
		List<Cargo> list = new ArrayList<Cargo>();
		list = cargoService.pesquisarTodos();
		obsList = FXCollections.observableArrayList(list);
		tableViewCargo.setItems(obsList);
		initEditButtons();
		initRemoveButton();	
	}
	
	private void initEditButtons() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<Cargo, Cargo>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Cargo cargo, boolean empty) {
				super.updateItem(cargo, empty);
				if (cargo == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
					event -> createDialogForm(cargo, "/gui/CargoForm.fxml", Utils.currentStage(event))
				);
			}
		});
	}
	
	private void initRemoveButton() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Cargo, Cargo>() {
			private final Button button = new Button("Deletar");

			@Override
			protected void updateItem(Cargo cargo, boolean empty) {
				super.updateItem(cargo, empty);
				if (cargo == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removerCargo(cargo));
			}
		});
	}
	
	private void removerCargo(Cargo cargo) {
		Optional<ButtonType> result = Alertas.mostrarConfirmacao("CONFIRMAÇÃO", "VOCÊ TEM CERTEZA QUE DESEJA DELETAR? \n\n" 
							+ "Se algum funcionário tiver este cargo, irá apagar o funcionário e todas as suas vendas realizadas!");

		if (result.get() == ButtonType.OK) {
			if (cargoService == null) {
				throw new IllegalStateException("Produto Service não instanciado.");
			}
			
			try {
				cargoService.delete(cargo);
				updateTableView();
			} 
			catch (BdException e) {
				Alertas.mostrarAlerta("Erro ao deletar o cargo!", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
	}	
	
	private void createDialogForm(Cargo cargo, String caminho, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			Pane pane = loader.load();
			
			CargoFormController cargoFormController = loader.getController();
			cargoFormController.setCargo(cargo);
			cargoFormController.setCargoService(cargoService);
			cargoFormController.subscribeDataChangeListener(this);
			
			cargoFormController.updateFormData();
			
			Stage formStage = new Stage();
			formStage.setTitle("Dados do Cargo");
			formStage.setScene(new Scene(pane));
		    formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
			
		}
		catch (IOException e) {
			e.printStackTrace();
			Alertas.mostrarAlerta("IO Exception", "Erro ao carregar o formulário", e.getMessage(), AlertType.ERROR);
		}
	}			
	
}
