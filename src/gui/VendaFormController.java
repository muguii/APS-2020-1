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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.entidades.Funcionario;
import model.entidades.Produto;
import model.entidades.Venda;
import model.entidades.VendasProdutos;
import model.entidades.VendasProdutosDTO;
import model.excecoes.ValidacaoException;
import model.services.FuncionarioService;
import model.services.ProdutoService;
import model.services.VendaService;
import model.services.VendasProdutosService;
import postgreBd.BdException;

public class VendaFormController implements Initializable{
	
	private Venda entidade;
	private VendaService vendaService;
	private FuncionarioService funcionarioService;
	private ProdutoService produtoService;
	private VendasProdutosService vendasProdutosService;
	private ObservableList<Funcionario> funcionarioObsList;
	private ObservableList<VendasProdutosDTO> vendasProdutosObsList;
	private List<VendasProdutosDTO> vendasProdutosList = new ArrayList<VendasProdutosDTO>();	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	
	public void setServices(VendaService vendaService, FuncionarioService funcionarioService, ProdutoService produtoService, VendasProdutosService vendasProdutoService) {
		this.vendaService = vendaService;
		this.funcionarioService = funcionarioService;
		this.produtoService = produtoService;
		this.vendasProdutosService = vendasProdutoService;
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
	private TextField txtIdVenda;
	@FXML
	private ComboBox<Funcionario> comboBoxFuncionario;
	@FXML
	private TextField txtCodigoProduto;
	@FXML
	private TextField txtQuantidadeProduto;
	@FXML
	private TextField txtFormaPagamentoVenda;
	@FXML
	private DatePicker dpDataVenda;
	@FXML
	private TextField txtValorTotalVenda;
	@FXML
	private TableView<VendasProdutosDTO> tableViewProdutos;
	@FXML
	private TableColumn<VendasProdutosDTO, String> tableColumnNomeProduto;
	@FXML
	private TableColumn<VendasProdutosDTO, Integer> tableColumnQuantidadeVendida;
	@FXML
	private Label labelErrorFuncionario;
	@FXML
	private Label labelErrorCodigoProduto;
	@FXML
	private Label labelErrorQuantidade;
	@FXML
	private Label labelErrorFormaPagamento;
	@FXML
	private Label labelErrorDataVenda;
	@FXML
	private Button btIncluir;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (vendaService == null || vendasProdutosService == null) {
			throw new IllegalStateException("Services não instanciados.");
		}
		
		try { 
			if (vendasProdutosList.isEmpty()) {
				throw new NullPointerException("É necessário adicionar pelo menos um item a venda!");
			} 
			
			List<VendasProdutos> produtosList = new ArrayList<>();
			
			for (VendasProdutosDTO obj : vendasProdutosList) {
				VendasProdutos vendasProdutos = VendasProdutosDTO.converterParaVendasProdutos(obj);
				
				if (vendasProdutosService.consultarQtdEstoque(vendasProdutos.getIdProduto()) < vendasProdutos.getQuantidade()) {
					vendasProdutosList.clear();
					vendasProdutosObsList.clear();
					updateTableProdutos();
					throw new BdException("Quantidade insuficiente do produto " + obj.getNomeProduto() + " em estoque!");
					
				}

				produtosList.add(vendasProdutos);
			}
						
			entidade = getFormVenda();
			vendaService.insert(entidade);
					
			for (VendasProdutos obj : produtosList) {
				obj.setIdVenda(entidade.getId());
				
				vendasProdutosService.insert(obj);
				vendasProdutosService.updateEstoque(obj);								
			}
			
			notifyDataChangeListeners();

			Alertas.mostrarAlerta(null, null, "Venda adicionada com sucesso!", AlertType.INFORMATION);					
			Utils.currentStage(event).close();
		}
		catch (ValidacaoException e) {
			setarMensagemDeErro(e.getErrors());
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			Alertas.mostrarAlerta(null, null, e.getMessage(), AlertType.INFORMATION);
		}
		catch (BdException e) {
			e.printStackTrace();
			Alertas.mostrarAlerta("Erro ao inserir a Venda no Banco!", null, e.getMessage(), AlertType.ERROR);
		}
		catch (Exception e) {
			e.printStackTrace();
			Alertas.mostrarAlerta(null, null, e.getMessage(), AlertType.ERROR);
		}
	}
	@FXML
	public void onBtIncluirAction(ActionEvent event) {
		if (vendasProdutosService == null) {
			throw new IllegalStateException("VendasProdutos Service não instanciados.");
		}
		
		try {						
			vendasProdutosList.add(getFormDadosProduto());		
			updateTableProdutos();			
			int indiceList = vendasProdutosList.size() - 1;
			updateValorTotal(vendasProdutosList.get(indiceList));
			
		}
		catch (IllegalStateException e) {
			Alertas.mostrarAlerta(null, null, e.getMessage(), AlertType.WARNING);
		}
		catch (NullPointerException e) {
			Alertas.mostrarAlerta("Código Inválido", null, e.getMessage(), AlertType.WARNING);
		}
		catch (Exception e) {
			e.printStackTrace();
			Alertas.mostrarAlerta(null, null, e.getMessage(), AlertType.ERROR);
		} 		
	}
	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
		initializeNodes();		
	}

	private void initializeNodes() {
		Restricoes.setTextFieldInteger(txtQuantidadeProduto);
		Restricoes.setTextFieldTamanhoMaximo(txtFormaPagamentoVenda, 20);
		Utils.formatDatePicker(dpDataVenda, "dd/MM/yyyy");		
		dpDataVenda.setValue(LocalDate.now());
		tableColumnNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tableColumnQuantidadeVendida.setCellValueFactory(new PropertyValueFactory<>("quantidadeVendasProdutos"));
		txtValorTotalVenda.setText("0");
		
		inicializarComboBoxFuncionario();
	}
	//Estudar novamente esse metodo
	private void inicializarComboBoxFuncionario() {
		Callback<ListView<Funcionario>, ListCell<Funcionario>> factory = lv -> new ListCell<Funcionario>() {
			@Override
			protected void updateItem(Funcionario funcionario, boolean empty) {
				super.updateItem(funcionario, empty);
				setText(empty ? "" : funcionario.getNome());
			}
		};
		comboBoxFuncionario.setCellFactory(factory);
		comboBoxFuncionario.setButtonCell(factory.call(null));	
	}
	
	private Venda getFormVenda() {
		if ((txtCodigoProduto.getText() == null || txtFormaPagamentoVenda.getText().trim().contentEquals("") || txtQuantidadeProduto == null || txtQuantidadeProduto.getText().trim().contentEquals("")) && vendasProdutosList.isEmpty()) {			
			throw new NullPointerException("É necessário adicionar pelo menos um item a venda!");			
		}
		
		Venda venda = new Venda();		
		ValidacaoException exception = new ValidacaoException("Erro na validação.");						
				
		if (comboBoxFuncionario.getValue() == null) {
			exception.addError("funcionario", "*");
		}
		
		if (txtFormaPagamentoVenda.getText() == null || txtFormaPagamentoVenda.getText().trim().contentEquals("")) {
			exception.addError("formaPagamento", "*");
		}
		
		if (dpDataVenda.getValue()== null) {
			exception.addError("dataVenda", "*");
		}
				
		venda.setFormaPagamento(txtFormaPagamentoVenda.getText());
		venda.setDataVenda(dpDataVenda.getValue());
		venda.setValorTotal(Utils.converterParaDouble(txtValorTotalVenda.getText()));
		venda.setFuncionario(comboBoxFuncionario.getValue());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return venda;
	}
	
	private VendasProdutosDTO getFormDadosProduto() {
		if (txtCodigoProduto.getText() == null || txtCodigoProduto.getText().trim().contentEquals("")) {
			throw new IllegalStateException("Por favor, escolha um item para incluir a venda!");
		} 
		
		Produto produto = new Produto();	
		produto = produtoService.pesquisarPorCodigo(txtCodigoProduto.getText());
		
		if(produto == null) {
			throw new NullPointerException("Este item não existe, verifique se o código está correto!");
		}
		
		VendasProdutosDTO vendasProdutosDTO = new VendasProdutosDTO();

		vendasProdutosDTO.setIdProduto(produto.getId());
		vendasProdutosDTO.setCodigoProduto(produto.getCodigoProduto());
		vendasProdutosDTO.setNomeProduto(produto.getNome());
		vendasProdutosDTO.setValorVendaProduto(produto.getValorVenda());
		vendasProdutosDTO.setQuantidadeVendasProdutos(Utils.converterParaInt(txtQuantidadeProduto.getText()));
		
		return vendasProdutosDTO;
	}
	
	private void updateTableProdutos() {		
		vendasProdutosObsList = FXCollections.observableArrayList(vendasProdutosList);
		tableViewProdutos.setItems(vendasProdutosObsList);		
	}
	
	private void updateValorTotal(VendasProdutosDTO vendasProdutosDTO) {	
		Double valorAtual = Utils.converterParaDouble(txtValorTotalVenda.getText());
		valorAtual += vendasProdutosService.atualizarValorTotal(vendasProdutosDTO);
		Locale.setDefault(Locale.US);
		txtValorTotalVenda.setText(String.format("%.2f", valorAtual));
	}
		
	public void carregarObjetosRelacionados() {
		if (vendaService == null || vendasProdutosService == null) {
			throw new IllegalStateException("Services não instanciados.");
		}
		
		List<Funcionario> funcionarioList = funcionarioService.pesquisaSimples();
		funcionarioObsList = FXCollections.observableArrayList(funcionarioList);
		comboBoxFuncionario.setItems(funcionarioObsList);
	}

	
	private void setarMensagemDeErro(Map<String, String> errors) {
		Set<String> fields = errors.keySet();		
				
		labelErrorFuncionario.setText(fields.contains("funcionario") ? errors.get("funcionario") : "");
		labelErrorCodigoProduto.setText(fields.contains("codigoProduto") ? errors.get("codigoProduto") : "");
		labelErrorQuantidade.setText(fields.contains("quantidade") ? errors.get("quantidade") : "");
		labelErrorFormaPagamento.setText(fields.contains("formaPagamento") ? errors.get("formaPagamento") : "");
		labelErrorDataVenda.setText(fields.contains("dataVenda") ? errors.get("dataVenda") : "");
		
	}
}
