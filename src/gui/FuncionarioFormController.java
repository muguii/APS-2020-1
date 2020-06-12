package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listeners.DataChangeListener;
import gui.util.Alertas;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entidades.Cargo;
import model.entidades.Endereco;
import model.entidades.Estado;
import model.entidades.Funcionario;
import model.entidades.Telefone;
import model.excecoes.ValidacaoException;
import model.services.CargoService;
import model.services.EnderecoService;
import model.services.FuncionarioService;
import postgreBd.BdException;

public class FuncionarioFormController implements Initializable{

	private Funcionario entidade;
	private FuncionarioService funcionarioService;
	private EnderecoService enderecoService;
	private CargoService cargoService;
	private ObservableList<Estado> estadoObsList;
	private ObservableList<Cargo> cargoObsList;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	public void setFuncionario(Funcionario entidade) {
		this.entidade = entidade;
	}
	
	public void setServices(FuncionarioService funcionarioService, EnderecoService enderecoService, CargoService cargoService) {
		this.funcionarioService = funcionarioService;
		this.enderecoService = enderecoService;
		this.cargoService = cargoService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtMatricula;
	@FXML
	private TextField txtCpf;
	@FXML
	private DatePicker dpDataNascimento;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtLogradouro;
	@FXML
	private TextField txtCep;
	@FXML
	private TextField txtNumeroEndereco;
	@FXML
	private TextField txtComplemento;
	@FXML
	private ComboBox<Estado> comboBoxEstado;
	@FXML
	private TextField txtMunicipio;
	@FXML
	private TextField txtBairro;
	@FXML
	private TextField txtDdd;
	@FXML
	private TextField txtTelefone;
	@FXML
	private ComboBox<Cargo> comboBoxCargo;
	@FXML
	private Label labelErrorNome;
	@FXML
	private Label labelErrorMatricula;
	@FXML
	private Label labelErrorCpf;
	@FXML
	private Label labelErrorDataNascimento;
	@FXML
	private Label labelErrorLogradouro;
	@FXML
	private Label labelErrorCep;
	@FXML
	private Label labelErrorNumeroEndereco;
	@FXML
	private Label labelErrorMunicipio;
	@FXML
	private Label labelErrorBairro;
	@FXML
	private Label labelErrorDdd;
	@FXML
	private Label labelErrorTelefone;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (funcionarioService == null || enderecoService == null || cargoService == null) {
			throw new IllegalStateException("Services não instanciados.");
		}
		
		try {
			
			entidade = getFormDados();
			
			enderecoService.insertOrUpdate(entidade.getEndereco(), entidade.getEndereco().getTelefone());
			funcionarioService.insertOrUpdate(entidade);
			
			notifyDataChangeListeners();
			
			if (entidade.getId() == null) {
				Alertas.mostrarAlerta(null, null, "Funcionário adicionado com sucesso!", AlertType.INFORMATION);				
			} else {
				Alertas.mostrarAlerta(null, null, "Funcionário alterado com sucesso!", AlertType.INFORMATION);				
			}
			
			Utils.currentStage(event).close();
		}
		catch (ValidacaoException e) {
			setarMensagemDeErro(e.getErrors());
		} 		
		catch (BdException e) {
			Alertas.mostrarAlerta("Erro ao inserir/atualizar o Funcionário!", null, e.getMessage(), AlertType.ERROR);
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
		Restricoes.setTextFieldTamanhoMaximo(txtNome, 255);
		Restricoes.setTextFieldTamanhoMaximo(txtMatricula, 15);
		Restricoes.setTextFieldInteger(txtCpf);
		Restricoes.setTextFieldTamanhoMaximo(txtCpf, 11);
		Utils.formatDatePicker(dpDataNascimento, "dd/MM/yyyy");
		Restricoes.setTextFieldTamanhoMaximo(txtEmail, 100);
		Restricoes.setTextFieldTamanhoMaximo(txtLogradouro, 255);
		Restricoes.setTextFieldInteger(txtCep);
		Restricoes.setTextFieldTamanhoMaximo(txtCep, 8);
		Restricoes.setTextFieldTamanhoMaximo(txtNumeroEndereco, 50);
		Restricoes.setTextFieldTamanhoMaximo(txtComplemento, 255);
		Restricoes.setTextFieldTamanhoMaximo(txtMunicipio, 50);
		Restricoes.setTextFieldTamanhoMaximo(txtBairro, 50);
		Restricoes.setTextFieldInteger(txtDdd);
		Restricoes.setTextFieldTamanhoMaximo(txtDdd, 3);
		Restricoes.setTextFieldInteger(txtTelefone);
		Restricoes.setTextFieldTamanhoMaximo(txtTelefone, 9);
		
		inicializarComboBoxEstado();
		inicializarComboBoxCargo();		
	}
	//Estudar esse metodo novamente
	private void inicializarComboBoxEstado() {		
		Callback<ListView<Estado>, ListCell<Estado>> factory = lv -> new ListCell<Estado>() {
			@Override
			protected void updateItem(Estado estado, boolean empty) {
				super.updateItem(estado, empty);
				setText(empty ? "" : estado.getNome());
			}
		};
		comboBoxEstado.setCellFactory(factory);
		comboBoxEstado.setButtonCell(factory.call(null));	
	}
	//Estudar esse metodo novamente
	private void inicializarComboBoxCargo() {
		Callback<ListView<Cargo>, ListCell<Cargo>> factory = lv -> new ListCell<Cargo>() {
			@Override
			protected void updateItem(Cargo cargo, boolean empty) {
				super.updateItem(cargo, empty);
				setText(empty ? "" : cargo.getNome());
			}
		};
		comboBoxCargo.setCellFactory(factory);
		comboBoxCargo.setButtonCell(factory.call(null));	
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Funcionário não instanciado.");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());
		txtMatricula.setText(entidade.getMatricula());
		txtCpf.setText(entidade.getCpf());
		dpDataNascimento.setValue(entidade.getDataNascimento());
		txtEmail.setText(entidade.getEmail());

		if (entidade.getId() != null) {
			txtLogradouro.setText(entidade.getEndereco().getLogradouro());			
			txtCep.setText(entidade.getEndereco().getCep());
			txtNumeroEndereco.setText(entidade.getEndereco().getNumero());
			txtComplemento.setText(entidade.getEndereco().getComplemento());
			txtMunicipio.setText(entidade.getEndereco().getMunicipio());
			txtBairro.setText(entidade.getEndereco().getBairro());	
			txtDdd.setText(entidade.getEndereco().getTelefone().getDdd());
			txtTelefone.setText(entidade.getEndereco().getTelefone().getTelefone());
			
			comboBoxEstado.setValue(entidade.getEndereco().getEstado());
			comboBoxCargo.setValue(entidade.getCargo());					
		} else {
			comboBoxEstado.getSelectionModel().selectFirst();
			comboBoxCargo.getSelectionModel().selectFirst();		
		}		
		
	}
	
	private Funcionario getFormDados() {
		Funcionario funcionario = new Funcionario();			
		ValidacaoException exception = new ValidacaoException("Erro na validação.");				
		funcionario.setId(Utils.converterParaInt(txtId.getText()));
		
		if (txtNome.getText() == null || txtNome.getText().trim().contentEquals("")) {
			exception.addError("nome", "Campo obrigatório!");
		}		
		
		if (txtMatricula.getText() == null || txtMatricula.getText().trim().contentEquals("")) {
			exception.addError("matricula", "Campo obrigatório!");
		}
		
		if (txtCpf.getText() == null || txtCpf.getText().trim().contentEquals("")) {
			exception.addError("cpf", "Campo obrigatório!");
		}
		
		if (dpDataNascimento.getValue() == null) {
			exception.addError("dataNascimento", "Campo obrigatório!");
		}		
		
		if (txtLogradouro.getText() == null || txtLogradouro.getText().trim().contentEquals("")) {
			exception.addError("logradouro", "Campo obrigatório!");
		}
		
		if (txtCep.getText() == null || txtCep.getText().trim().contentEquals("")) {
			exception.addError("cep", "Campo obrigatório!");
		}
		
		if (txtNumeroEndereco.getText() == null || txtNumeroEndereco.getText().trim().contentEquals("")) {
			exception.addError("numero", "Campo obrigatório!");
		}		
		
		if (txtMunicipio.getText() == null || txtMunicipio.getText().trim().contentEquals("")) {
			exception.addError("municipio", "Campo obrigatório!");
		}
		
		if (txtBairro.getText() == null || txtBairro.getText().trim().contentEquals("")) {
			exception.addError("bairro", "Campo obrigatório!");
		}
		
		if (txtDdd.getText() == null || txtDdd.getText().trim().contentEquals("")) {
			exception.addError("ddd", "Campo obrigatório!");
		}
		
		if (txtTelefone.getText() == null || txtTelefone.getText().trim().contentEquals("")) {
			exception.addError("telefone", "Campo obrigatório!");
		}
		
		
		funcionario.setNome(txtNome.getText());
		funcionario.setMatricula(txtMatricula.getText());
		funcionario.setDataNascimento(dpDataNascimento.getValue());		
		funcionario.setEmail(txtEmail.getText());
		funcionario.setCpf(txtCpf.getText());

		funcionario.setEndereco(instanciarEndereco());
		funcionario.setCargo(comboBoxCargo.getValue());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return funcionario;
	}
	
	public void carregarObjetosRelacionados() {
		if (enderecoService == null || cargoService == null) {
			throw new IllegalStateException("Services não instanciados.");
		}
		
		List<Cargo> cargoList = cargoService.pesquisarTodos();
		cargoObsList = FXCollections.observableArrayList(cargoList);
		comboBoxCargo.setItems(cargoObsList);
		
		List<Estado> estadoList = enderecoService.pesquisarTodosEstados();
		estadoObsList = FXCollections.observableArrayList(estadoList);
		comboBoxEstado.setItems(estadoObsList);	
	}
	
	private Endereco instanciarEndereco() {
		Endereco endereco = new Endereco();
		Telefone telefone = new Telefone();
		
		if (entidade.getId() != null) {
			endereco.setId(entidade.getEndereco().getId());
			telefone.setId(entidade.getEndereco().getTelefone().getId());		
		}
		endereco.setLogradouro(txtLogradouro.getText());
		endereco.setCep(txtCep.getText());
		endereco.setNumero(txtNumeroEndereco.getText());
		endereco.setComplemento(txtComplemento.getText());
		endereco.setMunicipio(txtMunicipio.getText());
		endereco.setBairro(txtBairro.getText());		
		endereco.setEstado(comboBoxEstado.getValue());
		
		telefone.setDdd(txtDdd.getText());
		telefone.setTelefone(txtTelefone.getText());
		
		endereco.setTelefone(telefone);
		
		return endereco;
	}
	
	private void setarMensagemDeErro(Map<String, String> errors) {
		Set<String> fields = errors.keySet();		
				
		labelErrorNome.setText(fields.contains("nome") ? errors.get("nome") : "");
		labelErrorMatricula.setText(fields.contains("matricula") ? errors.get("matricula") : "");
		labelErrorCpf.setText(fields.contains("cpf") ? errors.get("cpf") : "");
		labelErrorDataNascimento.setText(fields.contains("dataNascimento") ? errors.get("dataNascimento") : "");
		labelErrorLogradouro.setText(fields.contains("logradouro") ? errors.get("logradouro") : "");
		labelErrorCep.setText(fields.contains("cep") ? errors.get("cep") : "");
		labelErrorNumeroEndereco.setText(fields.contains("numero") ? errors.get("numero") : "");
		labelErrorMunicipio.setText(fields.contains("municipio") ? errors.get("municipio") : "");
		labelErrorBairro.setText(fields.contains("bairro") ? errors.get("bairro") : "");
		labelErrorDdd.setText(fields.contains("ddd") ? errors.get("ddd") : "");
		labelErrorTelefone.setText(fields.contains("telefone") ? errors.get("telefone") : "");
	}
	
}
