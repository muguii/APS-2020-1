package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entidades.VendasProdutosDTO;
import model.services.VendasProdutosService;

public class VendaDetalhesController  implements Initializable {

	
	private VendasProdutosService vendasProdutosService;
	private ObservableList<VendasProdutosDTO> obsList;
	
	public void setService(VendasProdutosService vendasProdutosService) {
		this.vendasProdutosService = vendasProdutosService;
	}
	
	@FXML
	private TableView<VendasProdutosDTO> tableViewDetalhesVenda;
	@FXML
	private TableColumn<VendasProdutosDTO, VendasProdutosDTO> tableColumnCodigoProduto;
	@FXML
	private TableColumn<VendasProdutosDTO, VendasProdutosDTO> tableColumnNomeProduto;
	@FXML
	private TableColumn<VendasProdutosDTO, VendasProdutosDTO> tableColumnQuantidade;
	@FXML
	private TableColumn<VendasProdutosDTO, VendasProdutosDTO> tableColumnValor;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		tableColumnCodigoProduto.setCellValueFactory(new PropertyValueFactory<>("codigoProduto"));
		tableColumnNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeVendasProdutos"));
		tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valorVendaProduto"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDetalhesVenda.prefHeightProperty().bind(stage.heightProperty());
	}
	
	
	
	public void loadData(Integer idVenda) {
		List<VendasProdutosDTO> list = new ArrayList<VendasProdutosDTO>();
		
		list = vendasProdutosService.pesquisarProdutosVenda(idVenda);
		obsList = FXCollections.observableArrayList(list);
		
		tableViewDetalhesVenda.setItems(obsList);
	}

	
}
