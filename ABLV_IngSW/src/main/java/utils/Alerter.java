package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerter {

	public Alerter() {}
	
	public static void showError(String message) {
		new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE).showAndWait();
	}
	
	public static void showSuccess(String message) {
		new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK).showAndWait();
	}
	
}
