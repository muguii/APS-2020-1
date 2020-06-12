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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entidades.Endereco;
import model.entidades.Estado;
import model.entidades.Fornecedor;
import model.entidades.Telefone;
import model.excecoes.ValidacaoException;
import model.services.EnderecoService;
import model.services.FornecedorService;
import postgreBd.BdException;

public class FornecedorFormController implements Initializable{

	private Fornecedor entidade;
	private FornecedorService fornecedorService;
	private EnderecoService enderecoService;
	private ObservableList<Estado> estadoObsList;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	public void setFornecedor(Fornecedor entidade) {
		this.entidade = entidade;
	}
	
	public void setServices(FornecedorService fornecedorService, EnderecoService enderecoService) {
		this.fornecedorService = fornecedorService;
		this.enderecoService = enderecoService;
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
	private TextField txtNomeFantasia;
	@FXML
	private TextField txtCnpj;
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
	private Label labelErrorNomeFantasia;
	@FXML
	private Label labelErrorCnpj;
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
		if (fornecedorService == null || enderecoService == null) {
			throw new IllegalStateException("Services não instanciados.");
		}
		
		try {
			
			entidade = getFormDados();
			
			enderecoService.insertOrUpdate(entidade.getEndereco(), entidade.getEndereco().getTelefone());
			fornecedorService.insertOrUpdate(entidade);
			
			notifyDataChangeListeners();
			
			if (entidade.getId() == null) {
				Alertas.mostrarAlerta(null, null, "Fornecedor adicionado com sucesso!", AlertType.INFORMATION);				
			} else {
				Alertas.mostrarAlerta(null, null, "Fornecedor alterado com sucesso!", AlertType.INFORMATION);				
			}
			
			Utils.currentStage(event).close();
		}
		catch (ValidacaoException e) {
			setarMensagemDeErro(e.getErrors());
		} 		
		catch (BdException e) {
			Alertas.mostrarAlerta("Erro ao inserir/atualizar o Fornecedor!", null, e.getMessage(), AlertType.ERROR);
		}
		
	}	
	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		Restricoes.setTextFieldTamanhoMaximo(txtNomeFantasia, 255);
		Restricoes.setTextFieldInteger(txtCnpj);
		Restricoes.setTextFieldTamanhoMaximo(txtCnpj, 20);
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
	}
	
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
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Fornecedor não instanciado.");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtNomeFantasia.setText(entidade.getNomeFantasia());
		txtCnpj.setText(entidade.getCnpj());
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
		} else {
			comboBoxEstado.getSelectionModel().selectFirst();
		}
		
	}
	
	private Fornecedor getFormDados() {
		Fornecedor fornecedor = new Fornecedor();
		ValidacaoException exception = new ValidacaoException("Erro na validação.");
		fornecedor.setId(Utils.converterParaInt(txtId.getText()));
		
		
		if (txtNomeFantasia.getText() == null || txtNomeFantasia.getText().trim().contentEquals("")) {
			exception.addError("nomeFantasia", "Campo obrigatório!");
		}
		
		if (txtCnpj.getText() == null || txtCnpj.getText().trim().contentEquals("")) {
			exception.addError("cnpj", "Campo obrigatório!");
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
		
		
		fornecedor.setNomeFantasia(txtNomeFantasia.getText());
		fornecedor.setCnpj(txtCnpj.getText());
		fornecedor.setEmail(txtEmail.getText());
		
		fornecedor.setEndereco(instanciarEndereco());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return fornecedor;
	}
	
	public void carregarObjetosRelacionados() {
		if (enderecoService == null) {
			throw new IllegalStateException("Endereço Service não instanciado.");
		}
		
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
				
		labelErrorNomeFantasia.setText(fields.contains("nomeFantasia") ? errors.get("nomeFantasia") : "");
		labelErrorCnpj.setText(fields.contains("cnpj") ? errors.get("cnpj") : "");		
		labelErrorLogradouro.setText(fields.contains("logradouro") ? errors.get("logradouro") : "");
		labelErrorCep.setText(fields.contains("cep") ? errors.get("cep") : "");
		labelErrorNumeroEndereco.setText(fields.contains("numero") ? errors.get("numero") : "");
		labelErrorMunicipio.setText(fields.contains("municipio") ? errors.get("municipio") : "");
		labelErrorBairro.setText(fields.contains("bairro") ? errors.get("bairro") : "");
		labelErrorDdd.setText(fields.contains("ddd") ? errors.get("ddd") : "");
		labelErrorTelefone.setText(fields.contains("telefone") ? errors.get("telefone") : "");
	}
	
}
