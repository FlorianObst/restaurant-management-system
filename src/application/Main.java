
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *<h1>Restaurant Management System</h1>
 *This program implements a restaurant management system.
 *Orders and other components can be managed by two types 
 *of accounts: manager and employee accounts.
 *The system is based on a SQL database
 *<p>
 *
 *@author Florian Obst
 *@version Final
 */
public class Main extends Application {

	/*
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// When launching, login is opened and fed into primary scene
			Parent root = FXMLLoader.load(getClass().getResource("/login/LoginView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
