package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listeners.DataChangeListener;
import gui.util.Alertas;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Cargo;
import model.excecoes.ValidacaoException;
import model.services.CargoService;
import postgreBd.BdException;

public class CargoFormController implements Initializable{

	private Cargo entidade;
	private CargoService cargoService;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	
	public void setCargo(Cargo entidade) {
		this.entidade = entidade;
	}
	
	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	public void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtCargoNome;
	@FXML
	private TextField txtSalario;
	@FXML
	private Label labelErrorCargoNome;
	@FXML
	private Label labelErrorSalario;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (cargoService == null ) {
			throw new IllegalStateException("Cargo Service não instanciado.");
		}
		
		try {
			entidade = getFormDados();
			Integer id = entidade.getId();
			
			cargoService.insertOrUpdate(entidade);
			notifyDataChangeListeners();
			
			if (id == null) {
				Alertas.mostrarAlerta(null, null, "Cargo adicionado com sucesso!", AlertType.INFORMATION);				
			} else {
				Alertas.mostrarAlerta(null, null, "Cargo alterado com sucesso!", AlertType.INFORMATION);				
			}
			
			Utils.currentStage(event).close();
		}
		catch (ValidacaoException e) {
			setarMensagemDeErro(e.getErrors());
		} 		
		catch (BdException e) {
			Alertas.mostrarAlerta("Erro ao inserir Cargo!", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Restricoes.setTextFieldTamanhoMaximo(txtCargoNome, 50);
		Restricoes.setTextFieldDouble(txtSalario);
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Cargo não instanciado.");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtCargoNome.setText(entidade.getNome());
		
		Locale.setDefault(Locale.US);
		txtSalario.setText(String.format("%.2f", entidade.getSalario()));
	}
	
	private Cargo getFormDados() {
		Cargo cargo = new Cargo();
		
		ValidacaoException exception = new ValidacaoException("Erro na validação.");
		
		cargo.setId(Utils.converterParaInt(txtId.getText()));
		
		if (txtCargoNome.getText() == null || txtCargoNome.getText().trim().contentEquals("")) {
			exception.addError("nome", "Campo obrigátorio!");
		}
		cargo.setNome(txtCargoNome.getText());
		
		if (txtSalario.getText() == null || txtSalario.getText().trim().contentEquals("")) {
			exception.addError("salario", "Campo obrigátorio!");
		}
		cargo.setSalario(Utils.converterParaDouble(txtSalario.getText()));
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return cargo;
	}
		
	private void setarMensagemDeErro(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		labelErrorCargoNome.setText(fields.contains("nome") ? errors.get("nome") : "");
		labelErrorSalario.setText(fields.contains("salario") ? errors.get("salario") : "");
	}
	
}
