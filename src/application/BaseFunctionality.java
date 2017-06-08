package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BaseFunctionality {

	/**
	 * Sign out.
	 *
	 * @param event the event
	 */
	@FXML
	public void signOut(ActionEvent event) {
		try {
			// save again to incorporate special requests
			// updateSpecialRequest();

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/login/LoginView.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets()
			.add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
