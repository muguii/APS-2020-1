package gui;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Estoque;
import model.entidades.Produto;
import model.excecoes.ValidacaoException;
import model.services.EstoqueService;
import model.services.ProdutoService;
import postgreBd.BdException;

public class ProdutoFormController implements Initializable {

	private Estoque entidade;
	private ProdutoService produtoService;
	private EstoqueService estoqueService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	
	public void setProduto_Estoque(Estoque entidade) {
		this.entidade = entidade;
	}
	
	public void setServices(EstoqueService estoqueService, ProdutoService produtoService) {
		this.estoqueService = estoqueService;
		this.produtoService = produtoService;
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
	private TextField txtNomeProduto;
	@FXML
	private TextField txtCodigoProduto;
	@FXML
	private TextField txtUnidadeMedida;
	@FXML
	private TextField txtQuantidade;
	@FXML
	private DatePicker dpDataValidade;
	@FXML
	private TextField txtValorCompra;
	@FXML
	private TextField txtValorVenda;	
	@FXML
	private DatePicker dpDataFabricacao;
	@FXML
	private DatePicker dpDataEntradaEstoque;
	@FXML
	private Label labelErrorCodigoProduto;
	@FXML
	private Label labelErrorNomeProduto;
	@FXML
	private Label labelErrorQuantidade;
	@FXML
	private Label labelErrorDataValidade;
	@FXML
	private Label labelErrorValorVenda;
	@FXML
	private Label labelErrorDataFabricacao;
	@FXML
	private Label labelErrorDataEntradaEstoque;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;	
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (produtoService == null || estoqueService == null) {
			throw new IllegalStateException("Services não instanciados.");
		}
		
		try {
			entidade = getFormDados();
			
			produtoService.insertOrUpdate(entidade.getProduto());
			estoqueService.insertOrUpdate(entidade);
			
			notifyDataChangeListeners();
			
			if (entidade.getId() == null) {
				Alertas.mostrarAlerta(null, null, "Produto adicionado com sucesso!", AlertType.INFORMATION);				
			} else {
				Alertas.mostrarAlerta(null, null, "Produto alterado com sucesso!", AlertType.INFORMATION);				
			}
			
			Utils.currentStage(event).close();
		}  
		catch (ValidacaoException e) {
			setarMensagemDeErro(e.getErrors());
		} 		
		catch (BdException e) {
			Alertas.mostrarAlerta("Erro ao inserir o produto no estoque!", null, e.getMessage(), AlertType.ERROR);

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
		Restricoes.setTextFieldTamanhoMaximo(txtCodigoProduto, 25);
		Restricoes.setTextFieldTamanhoMaximo(txtNomeProduto, 255);
		Restricoes.setTextFieldTamanhoMaximo(txtUnidadeMedida, 10);
		Restricoes.setTextFieldInteger(txtQuantidade);
		Utils.formatDatePicker(dpDataValidade, "dd/MM/yyyy");
		Restricoes.setTextFieldDouble(txtValorCompra);
		Restricoes.setTextFieldDouble(txtValorVenda);
		Utils.formatDatePicker(dpDataFabricacao, "dd/MM/yyyy");
		Utils.formatDatePicker(dpDataEntradaEstoque, "dd/MM/yyyy");
	}
	
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Produto_Estoque não instanciado.");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtQuantidade.setText(String.valueOf(entidade.getQuantidade()));
		dpDataEntradaEstoque.setValue(LocalDate.now());					
		
		if (entidade.getId() != null) {
			txtCodigoProduto.setText(entidade.getProduto().getCodigoProduto());
			txtNomeProduto.setText(entidade.getProduto().getNome());
			txtUnidadeMedida.setText(entidade.getProduto().getUnidadeMedida());
			dpDataValidade.setValue(entidade.getProduto().getDataValidade());
			Locale.setDefault(Locale.US);
			txtValorCompra.setText(String.format("%.2f", entidade.getProduto().getValorCompra()));
			txtValorVenda.setText(String.format("%.2f", entidade.getProduto().getValorVenda()));
			dpDataFabricacao.setValue(entidade.getProduto().getDataFabricacao());
		}
		
	}
	
	private Estoque getFormDados() {
		Estoque estoque = new Estoque();
		
		ValidacaoException exception = new ValidacaoException("Erro na validação.");		
		
		estoque.setId(Utils.converterParaInt(txtId.getText()));
		

		if (txtCodigoProduto.getText() == null || txtCodigoProduto.getText().trim().contentEquals("")) {
			exception.addError("codigoProduto", "Campo obrigatório!");
		}		
		
		if (txtNomeProduto.getText() == null || txtNomeProduto.getText().trim().contentEquals("")) {
			exception.addError("nomeProduto", "Campo obrigatório!");
		}
		
		if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().contentEquals("")) {
			exception.addError("quantidade", "Campo obrigatório!");
		}
		estoque.setQuantidade(Utils.converterParaInt(txtQuantidade.getText()));
		
		if (dpDataValidade.getValue() == null) {
			exception.addError("dataValidade", "Campo obrigatório!");
		}
		
		if (txtValorVenda.getText() == null || txtValorVenda.getText().trim().contentEquals("")) {
			exception.addError("valorVenda", "Campo obrigatório!");
		}
		
		if (dpDataFabricacao.getValue() == null) {
			exception.addError("dataFabricacao", "Campo obrigatório!");
		}
		
		if (dpDataEntradaEstoque.getValue() == null) {
			exception.addError("dataEntradaEstoque", "Campo obrigatório!");
		}
		
		estoque.setDataEntrada(dpDataEntradaEstoque.getValue());
		estoque.setProduto(instanciarProduto());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return estoque;
	}	
	
	private Produto instanciarProduto() {
		Produto produto = new Produto();
		
		if (entidade.getId() != null) {
			produto.setId(entidade.getProduto().getId());			
		}
		produto.setCodigoProduto(txtCodigoProduto.getText());
		produto.setNome(txtNomeProduto.getText());
		produto.setUnidadeMedida(txtUnidadeMedida.getText());
		produto.setValorCompra(Utils.converterParaDouble(txtValorCompra.getText()));
		produto.setValorVenda(Utils.converterParaDouble(txtValorVenda.getText()));
		produto.setDataFabricacao(dpDataFabricacao.getValue());
		produto.setDataValidade(dpDataValidade.getValue());
		
		return produto;
	}
	
	private void setarMensagemDeErro(Map<String, String> errors) {
		Set<String> fields = errors.keySet();	
		
		labelErrorCodigoProduto.setText(fields.contains("codigoProduto") ? errors.get("codigoProduto") : "");
		labelErrorNomeProduto.setText(fields.contains("nomeProduto") ? errors.get("nomeProduto") : "");
		labelErrorQuantidade.setText(fields.contains("quantidade") ? errors.get("quantidade") : "");
		labelErrorDataValidade.setText(fields.contains("dataValidade") ? errors.get("dataValidade") : "");
		labelErrorValorVenda.setText(fields.contains("valorVenda") ? errors.get("valorVenda") : "");
		labelErrorDataFabricacao.setText(fields.contains("dataFabricacao") ? errors.get("dataFabricacao") : "");
		labelErrorDataEntradaEstoque.setText(fields.contains("dataEntradaEstoque") ? errors.get("dataEntradaEstoque") : "");
		
	}	
}
