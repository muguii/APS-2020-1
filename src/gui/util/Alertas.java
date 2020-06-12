package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alertas {

	public static void mostrarAlerta(String titulo, String header, String conteudo, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(conteudo);
		alert.show();
	}
	
	public static Optional<ButtonType> mostrarConfirmacao(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		return alert.showAndWait();
	}
	
}