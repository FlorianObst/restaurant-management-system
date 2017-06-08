package login;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbControl.DbControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import management.Employee;
import management.EmployeeDAO;
import management.MgmtController;
import waiter.EmployeeController;

// all methods from the Interface Initializable have to be implemented
/**
 * Controls functionalities of the fxml file
 * <p>
 * Implements an interface
 * <p>
 * Source: https://www.youtube.com/watch?v=IGTL5mvYU54
 *
 * @author Florian Obst
 * @version Final
 */
public class LoginController implements Initializable {

	@FXML
	private Label connection;
	@FXML
	private TextField username;
	@FXML
	private TextField pw;
	
	// Instantiate
	public Login login = new Login();

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// Try to connect
			DbControl.dbConnect();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (DbControl.dbConnectionSuccess()) {
			// Prompt user
			// connection.setText("Enter Sign in details");
		} else {
			// UpdateS
			connection.setText("Incorrect details");
		}
	}
	
	/**
	 * Login mgmt.
	 *
	 * @param event the event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void LoginMgmt(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

		try {
			// checks if boolean returning method isLogin is correct with the
			// input UserName and Password
			if (login.managerLogin(username.getText(), pw.getText())) {
				// Hides the current windows
				((Node) actionEvent.getSource()).getScene().getWindow().hide();
				// Initiates primary stage
				Stage primaryStage = new Stage();
				// Initiates FMXLLoader
				FXMLLoader loader = new FXMLLoader();
				// Get manager root
				Pane root = loader.load(getClass().getResource("/management/MgmtEmp.fxml").openStream());
				// Access manager controller and set username
				MgmtController mgmtController = (MgmtController) loader.getController();
				mgmtController.SetManager(username.getText());
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} else {
				connection.setText(("Incorrect details"));
			}
		} catch (SQLException e) {
			connection.setText("Incorrect details");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Login emp.
	 *
	 * Tests whether user-name and password are correct and opens up main
	 *
	 * @param event the event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void LoginEmp(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

		try {
			// Check if details are correct
			if (login.empLogin(username.getText(), pw.getText())) {

				// Access employee Id
				Employee employee = EmployeeDAO.searchEmployee(username.getText(), pw.getText());
				int employeeId = employee.getEmployeeId();
				// Hides the current windows
				((Node) actionEvent.getSource()).getScene().getWindow().hide();
				// Initiates primary stage
				Stage primaryStage = new Stage();
				// Initiates FMXLLoader
				FXMLLoader loader = new FXMLLoader();
				// Get employee root
				Pane root = loader.load(getClass().getResource("/waiter/EmployeeView.fxml").openStream());
				// Access employee controller and set username
				EmployeeController employeeController = (EmployeeController) loader.getController();
				employeeController.SetWaiter(username.getText(), employeeId);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();

			} else {
				connection.setText(("Incorrect details"));
			}
		} catch (SQLException e) {
			connection.setText("Incorrect details");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
