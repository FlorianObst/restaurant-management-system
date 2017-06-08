package management;

import java.io.FileWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import application.BaseFunctionality;
import dbControl.DbControl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import management.Employee;
import order.Table;
import order.TableDAO;
import products.Products;
import products.ProductsDAO;
import waiter.Activity;
import waiter.ActivityDAO;
import waiter.Order;
import waiter.OrderDAO;
import waiter.TableStatus;
import waiter.TableStatusDAO;

/**
 *Handles functionality of fxml for Manager pages
 *<p>
 *Sources: 
 *http://www.swtestacademy.com/database-operations-javafx/
 *http://stackoverflow.com/questions/19490868/how-to-unselect-a-selected-table-row-upon-second-click-selection-in-javafx
 *http://stackoverflow.com/questions/10403838/prevent-an-accordion-in-javafx-from-collapsing
 *https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm
 *http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
 *http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
 *http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
 *https://examples.javacodegeeks.com/core-java/writeread-csv-files-in-java-example/
 *http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
 *
 *@author Florian Obst
 *@version Final
 */
public class MgmtController extends BaseFunctionality {
	// Employee Tab
	@FXML
	private Label userLbl1;
	@FXML
	private TextField searchText;
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee, Integer> empIdColumn;
	@FXML
	private TableColumn<Employee, String> empNameColumn;
	@FXML
	private TableColumn<Employee, String> empLastNameColumn;
	@FXML
	private TableColumn<Employee, String> empUsernameColumn;
	@FXML
	private TableColumn<Employee, String> empPasswordColumn;
	@FXML 
	private ComboBox<String> searchByBoxEmployee;
	@FXML
	private TextField editFirstNameText;
	@FXML
	private TextField editLastNameText;
	@FXML
	private TextField editUsernameText;
	@FXML
	private TextField editPasswordText;
	@FXML
	private Button addEmpBtn;
	@FXML
	private Button changeEmpBtn;
	@FXML
	private Button deleteEmpBtn;
	@FXML
	private Button exportBtn;
	@FXML
	private Button importBtn;
	@FXML
	private TableView<Activity> activityTable;
	@FXML
	private TableColumn<Activity, String> activityDateColumn;
	@FXML
	private TableColumn<Activity, String> activityColumn;

	// Order Tab
	@FXML
	private Button deleteOrdBtn;
	@FXML
	private Button exportOrderBtn;
	@FXML
	private Button importOrderBtn;
	@FXML
	private TableView<Order> ordersTable;
	@FXML
	private TableColumn<Order, Integer> ordIdColumn;
	@FXML
	private TableColumn<Order, String> tableNoColumn;
	@FXML
	private TableColumn<Order, String> orderTimeColumnn;
	@FXML
	private TableColumn<Order, Integer> totalBillColumn;
	@FXML
	private TableColumn<Order, String> commentsSpecialRequest;
	@FXML 
	private ComboBox<String> searchByBoxOrder;
	@FXML
	private TextField searchPromptText;
	@FXML
	private TableView<Table> subOrdersTable;
	@FXML
	private TableColumn<Table, Integer> subOrderID;
	@FXML
	private TableColumn<Table, String> productName;
	@FXML
	private TableColumn<Table, String> productCategory;
	@FXML
	private TableColumn<Table, Integer> productPrice;
	@FXML
	private TableColumn<Table, String> subOrderComment;
	@FXML
	private DatePicker orderDatePickerStart;
	@FXML
	private DatePicker orderDatePickerEnd;
	@FXML
	private TextField orderTimeStart;
	@FXML
	private TextField orderTimeEnd;
	@FXML
	private Button orderSearchbyTime;
	@FXML
	private Accordion orderAccordion;
	@FXML
	private TitledPane searchByKeywordOrder;
	@FXML
	private TitledPane searchByTimeOrder;

	private boolean searchByTimeOrderBool = false;

	// Menu Tab
	@FXML
	private TableView<Products> productsTable;
	@FXML
	private TableColumn<Products, Integer> productIdColumn;
	@FXML
	private TableColumn<Products, String> productCategoryColumn;
	@FXML
	private TableColumn<Products, String> productNameColumnn;
	@FXML
	private TableColumn<Products, Integer> productPriceColumn;
	@FXML 
	private ComboBox<String> searchByBoxProduct;
	@FXML
	private TextField searchPromptTextProduct;
	@FXML
	private TextField editProductCategory;
	@FXML
	private TextField editProductName;
	@FXML
	private TextField editProductPrice;
	@FXML
	private Button addProductBtn;
	@FXML
	private Button changeProductBtn;
	@FXML
	private Button deleteProductBtn;

	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	// INITIALIZE
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________

	/**
	 * Initialize.
	 * Creates fxml
	 */
	@FXML
	private void initialize() {

		// Initialize Employee table columns (Employee tab)
		empIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
		empNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		empUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().loginUsernameProperty());
		empPasswordColumn.setCellValueFactory(cellData -> cellData.getValue().loginPasswordProperty());

		// Initialize Employee activity table columns (Employee tab)
		activityDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateAndTimeProperty());
		activityColumn.setCellValueFactory(cellData -> cellData.getValue().activityProperty());

		// Initialize Orders table columns (Orders tab)
		ordIdColumn.setCellValueFactory(cellData -> cellData.getValue().OrderIdProperty().asObject());
		// tableNoColumn.setCellValueFactory(cellData -> cellData.getValue().tableNumberStatusProperty().asObject());
		// Only the modified "copy" of tablenumber column is shown; including tablenumbers as strings and status for takeaway
		tableNoColumn.setCellValueFactory(cellData -> cellData.getValue().tableNumberStatusProperty());
		orderTimeColumnn.setCellValueFactory(cellData -> cellData.getValue().orderTimeProperty());
		totalBillColumn.setCellValueFactory(cellData -> cellData.getValue().totalBillProperty().asObject());
		commentsSpecialRequest.setCellValueFactory(cellData -> cellData.getValue().specialRequestsProperty());

		// Initialize Suborders table columns (Orders tab)
		subOrderID.setCellValueFactory(cellData -> cellData.getValue().sUbOrderIdProperty().asObject());
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		productCategory.setCellValueFactory(cellData -> cellData.getValue().productCategoryProperty());
		productPrice.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
		subOrderComment.setCellValueFactory(cellData -> cellData.getValue().commentsProperty());

		// Initialize Products table colums (Menu tab)
		productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
		productCategoryColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		productNameColumnn.setCellValueFactory(cellData -> cellData.getValue().productCategoryProperty());
		productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());

		// Populate drop down/ ComboBoxes when page is opened
		populateDropDownMenuEmployees(); // Employees
		populateDropDownMenuOrders();	// Orders
		populateDropDownMenuProducts(); // Menu

		// Fill TableView
		try {
			// Populate TableView based on search filters
			searchEmployees(); // Employess
			searchOrders(); // Orders
			searchProducts(); // Menu
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// Employeee-TableView: Enable selecting and de-selecting on second click (Employees Tab) 
		// Source: http://stackoverflow.com/questions/19490868/how-to-unselect-a-selected-table-row-upon-second-click-selection-in-javafx
		employeeTable.setRowFactory(new Callback<TableView<Employee>, TableRow<Employee>>() {  
			@Override  
			public TableRow<Employee> call(TableView<Employee> tableView2) {  
				final TableRow<Employee> row = new TableRow<>();  
				// Use MouseEvent
				row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {  
					@Override  
					public void handle(MouseEvent event) {  
						final int index = row.getIndex();  
						// Apply to employeeTable
						if (index >= 0 && index < employeeTable.getItems().size() && employeeTable.getSelectionModel().isSelected(index)  ) {
							employeeTable.getSelectionModel().clearSelection();
							event.consume();  
						}  
					}  
				}); 
				return row;  
			}  
		});

		// Products-TableView: Enable selecting and de-selecting on second click (Menu Tab) 
		productsTable.setRowFactory(new Callback<TableView<Products>, TableRow<Products>>() {  
			@Override  
			public TableRow<Products> call(TableView<Products> tableView2) {  
				final TableRow<Products> row = new TableRow<>();  
				// Use MouseEvent
				row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {  
					@Override  
					public void handle(MouseEvent event) { 
						// Apply to productTable
						final int index = row.getIndex();  
						if (index >= 0 && index < productsTable.getItems().size() && productsTable.getSelectionModel().isSelected(index)  ) {
							productsTable.getSelectionModel().clearSelection();
							event.consume();  
						}  
					}  
				}); 
				return row;  
			}  
		});

		// Detect whether row is selected
		isRowSelectedDetectorEmployee(); // Employee
		isRowSelectedDetectorOrder(); // Order
		isRowSelectedDetectorProducts(); // Menu

		// Default button status: active add, inactive delete/ update
		deactivaeUpdateDeleteExportEmployee(); // Employee
		deactivaeUpdateDeleteProducts(); // Menu
		deactivateBtnsOrder(); // Order

		// Disable row selection for Suborders table
		subOrdersTable.setSelectionModel(null); // Order

		// Accordion
		orderAccordion.setExpandedPane(searchByKeywordOrder); // Order

		// create an accordion, ensuring the currently expanded pane can not be clicked on to collapse.
		// Source: http://stackoverflow.com/questions/10403838/prevent-an-accordion-in-javafx-from-collapsing
		orderAccordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
			@Override 
			public void changed(ObservableValue<? extends TitledPane> property, final TitledPane oldPane, final TitledPane newPane) {
				if (oldPane != null) oldPane.setCollapsible(true);
				if (newPane != null) Platform.runLater(new Runnable() { @Override public void run() { 
					newPane.setCollapsible(false); 
					try {
						negateSearchByTimeOrderBool();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}});
			}
		});
		for (TitledPane pane: orderAccordion.getPanes()) pane.setAnimated(false);
		orderAccordion.setExpandedPane(orderAccordion.getPanes().get(0));

		// Set string in TableView
		ordersTable.setPlaceholder(new Label("No orders available")); // Order
		subOrdersTable.setPlaceholder(new Label("Please select an order")); // Order
		productsTable.setPlaceholder(new Label("Please add menu items")); // Menu
		employeeTable.setPlaceholder(new Label("Please add employee details")); // Employee
		activityTable.setPlaceholder(new Label("No activities available")); // Employee

		// Make Menu price text field always numeric 
		editProductPrice.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					editProductPrice.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		// Disable selecting end day before start day (Order)
		// Source: https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm
		final Callback<DatePicker, DateCell> dayCellFactory = 
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(
								orderDatePickerStart.getValue().plusDays(0))
								) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}   
					}
				};
			}
		};
		// Add restriction to end date
		orderDatePickerEnd.setDayCellFactory(dayCellFactory);

		// Hide weeks for date pickers
		orderDatePickerStart.setShowWeekNumbers(false);
		orderDatePickerEnd.setShowWeekNumbers(false);
	}

	/**
	 * Sets the manager.
	 *
	 * @param user the user
	 */
	public void SetManager(String user) {
		userLbl1.setText(user);
	}

	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	//________________________________________________________________________________________EMPLOYEE TAB 
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________

	// SEARCH 

	/**
	 * Search all employees.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// @FXML
	private void searchEmployees() throws SQLException, ClassNotFoundException {
		// Get all Employees information
		ObservableList<Employee> empData = EmployeeDAO.searchEmployees();
		// Populate Employees on TableView
		populateEmployees(empData);

		// SOURCE: http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		// 1. ObservableList --> FilteredList (initially display all data)
		FilteredList<Employee> filteredSearch = new FilteredList<>(empData, p -> true);

		// 2. Set the filter whenever  filter changes
		searchText.textProperty().addListener((observable, oldValue, newValue) -> {
			// Find which filter was selected
			String selectedSearchFilter = geSelectedSearchFilter();
			// For each character stroke: following function will be called --> update search filter:
			filteredSearch.setPredicate(employee -> {
				// If searchText empty --> display all employees.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// convert to lower case
				String lowerCaseSearchText = newValue.toLowerCase();
				// Compare the search keywords and employee data based on selected search filter
				if(selectedSearchFilter.equals("Employee ID")) {
					if(Integer.toString(employee.getEmployeeId()).toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}
				else if (selectedSearchFilter.equals("Employee first name")) {
					if(employee.getFirstName().toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}
				else if (selectedSearchFilter.equals("Employee last name")) {
					if(employee.getLastName().toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}
				else if(selectedSearchFilter.equals("Employee Username")) {
					if(employee.getloginUsername().toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}	
				}
				// else
				return false;
			});
		});
		// 3. FilteredList --> SortedList 
		SortedList<Employee> sortedSearch = new SortedList<>(filteredSearch);
		// 4. Bind SL comp to TV comp
		sortedSearch.comparatorProperty().bind(employeeTable.comparatorProperty());
		// 5. Add data
		employeeTable.setItems(sortedSearch);
		// Several choices
		employeeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**
	 * Populate drop down menu employees
	 */
	private void populateDropDownMenuEmployees() {
		// Source: http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
		ObservableList<String> options = 
				FXCollections.observableArrayList(
						"Employee ID",
						"Employee last name",
						"Employee first name",
						"Employee Username"
						);
		// Populate searchByBoxEmployee with options
		searchByBoxEmployee.setItems(options);
		// Set default value
		searchByBoxEmployee.setValue("Employee ID");
	}

	/**
	 * Get selected search filter.
	 *
	 * @return the string
	 */
	private String geSelectedSearchFilter() {
		return searchByBoxEmployee.getValue();
	}

	/**
	 * Populate Employees for TableView
	 *
	 * @param empData the emp data
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateEmployees(ObservableList<Employee> empData) throws ClassNotFoundException {
		employeeTable.setItems(empData);
	}

	// ROW SELECTION
	/**
	 * Determines whether or not a row is selected based on which further method is called
	 */
	private void isRowSelectedDetectorEmployee() {
		// Source: http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
		employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				try {
					// If a row is selected --> display the content in Edit-TextFields AND show activity
					displaySelection();
					populateActivity();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			} else {
				// If no row selected --> empty the Edit-TextFields
				emptyBoxes();
				activityTable.getItems().clear();
			}
		});
	}

	/**
	 * Display selection.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// Display the content in Edit-TextFields
	private void displaySelection() throws SQLException, ClassNotFoundException {
		// Access the elements from selected row
		Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

		editFirstNameText.setText(selectedEmployee.getFirstName());
		editLastNameText.setText(selectedEmployee.getLastName());
		editUsernameText.setText(selectedEmployee.getloginUsername());
		editPasswordText.setText((String) selectedEmployee.getloginPassword());
		// update the modify button
		deactivateAdd();
	}

	/**
	 * Empty the Edit-TextFields.
	 */
	private void emptyBoxes() {
		editFirstNameText.clear();
		editLastNameText.clear();
		editUsernameText.clear();
		editPasswordText.clear();

		// Update the insert button
		deactivaeUpdateDeleteExportEmployee();
	}

	/**
	 * Activate/ deactivate buttons
	 */
	private void deactivaeUpdateDeleteExportEmployee() {
		// deactivate
		changeEmpBtn.setDisable(true);
		deleteEmpBtn.setDisable(true);
		exportBtn.setDisable(true);
		// activate add button
		addEmpBtn.setDisable(false);
	}

	/**
	 * Deactivate add.
	 */
	private void deactivateAdd() {
		// activate add button
		addEmpBtn.setDisable(true);
		// deactivate
		changeEmpBtn.setDisable(false);
		deleteEmpBtn.setDisable(false);
		exportBtn.setDisable(false);
	}

	// UPDATE

	/**
	 * Check if employee input correct.
	 *
	 * @return the int
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private int checkIfEmployeeInputCorrect() throws ClassNotFoundException, SQLException {
		if((!editFirstNameText.getText().isEmpty()) && (!editLastNameText.getText().isEmpty()) && (!editUsernameText.getText().isEmpty()) && (!editPasswordText.getText().isEmpty())) {
			// Get the user name
			String userName = editUsernameText.getText();
			// Check if product already exists:
			// Access all products
			ObservableList<Employee> employeeList = EmployeeDAO.searchEmployees();
			// boolean to check if already exists
			boolean addNewEmployee = true;
			// Loop through OL
			for (Employee empoyee : employeeList) {
				String existingEmp = empoyee.getloginUsername();
				if(userName.equalsIgnoreCase(existingEmp)) {
					addNewEmployee = false;
				}
			}
			// only if none was same --> add
			if(addNewEmployee) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return 2;
		}
	}


	/**
	 * Update employee.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void updateEmployee() throws SQLException, ClassNotFoundException {
		try {
			if (checkIfEmployeeInputCorrect() == 0 || checkIfEmployeeInputCorrect() == 1) {
				// Access updated values from edit boxes
				String newFirstName = editFirstNameText.getText();
				String newLastName = editLastNameText.getText();
				String newUserName = editUsernameText.getText();
				String newPassword = editPasswordText.getText();

				// Create Employee object to access employeeId
				Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
				int employeeId = selectedEmployee.getEmployeeId();

				// Update employee in DB
				EmployeeDAO.updateEmployee(employeeId, newFirstName, newLastName, newUserName, newPassword);
				// Update the displayed Employees again with changes
				searchEmployees();
				// clear edit fields
				emptyBoxes();

			} /*else if(checkIfEmployeeInputCorrect() == 1) {
				// Show alert
				showAlertInformation("Failure: " + editUsernameText.getText() + " has already been inserted");

			} */else if(checkIfEmployeeInputCorrect() == 2) {
				// Show alert
				showAlertInformation("All field required for new entry");
				// clear edit fields
				emptyBoxes();
			}

		} catch (SQLException e) {
			throw e;
		}
	}

	// INSERT
	/**
	 * Insert employee.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void insertEmployee() throws SQLException, ClassNotFoundException {
		try {

			if((!editFirstNameText.getText().isEmpty()) && (!editLastNameText.getText().isEmpty()) && (!editUsernameText.getText().isEmpty()) && (!editPasswordText.getText().isEmpty())) {
				// Get the user name
				String userName = editUsernameText.getText();
				// Check if product already exists:
				// Access all products
				ObservableList<Employee> employeeList = EmployeeDAO.searchEmployees();
				// boolean to check if already exists
				boolean addNewEmployee = true;
				// Loop through OL
				for (Employee empoyee : employeeList) {
					String existingEmp = empoyee.getloginUsername();
					if(userName.equalsIgnoreCase(existingEmp)) {
						addNewEmployee = false;
					}
				}
				// only if none was same --> add
				if(addNewEmployee) {
					// Insert employee in DB
					EmployeeDAO.addEmp(editFirstNameText.getText(), editLastNameText.getText(), editUsernameText.getText(), editPasswordText.getText());
					// Update the displayed Employees again with changes
					searchEmployees();
					// resultArea.setText("Employee inserted! \n");
					// Clear edit fields
					emptyBoxes();
				} else {
					// Show alert
					showAlertInformation("Failure: " + userName + " has already been inserted");
				}
				// clear edit fields
				emptyBoxes();
			} else {
				// Show alert
				showAlertInformation("All field required for new entry");
				// clear edit fields
				emptyBoxes();
			}	
		} catch (SQLException e) {
			// resultArea.setText("Problem occurred while inserting employee " + e);
			throw e;
		}
	}

	// DELETE
	/**
	 * Delete employee.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void deleteEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			ObservableList<Employee> deleteRows = employeeTable.getSelectionModel().getSelectedItems();
			for (Employee selectedEmployee : deleteRows) {
				// delete
				EmployeeDAO.deleteEmpWithId(selectedEmployee.getEmployeeId());
				// Update the displayed Employees again with changes 
			}

			searchEmployees();
			// resultArea.setText("Employee deleted!\n");
		} catch (SQLException e) {
			// resultArea.setText("Problem occurred while deleting employee " + e);
			throw e;
		}
	}
	
	// IMPORT
	/**
	 * Import employee data.
	 *
	 * @param event the event
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void importEmployeeData(ActionEvent event) throws ClassNotFoundException, SQLException {
		// Notification popup
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Confirmation");
		alert.setContentText("Please upload CSV file with comma seperated data in (Format: id,Name,Last Name,Username,Password)");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				DbControl.dbImport();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				initialize();
			}
		}
	}

	// EXPORT
	/**
	 * Export employee data
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void export(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// Source: https://examples.javacodegeeks.com/core-java/writeread-csv-files-in-java-example/
			// Establish connection
			DbControl.dbConnect();
			// Store selected rows in oberservable list
			ObservableList<Employee> exportRows = employeeTable.getSelectionModel().getSelectedItems();
			// Open file chooser
			FileChooser fileChooser = new FileChooser();
			// Specify which file types you want
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
			try {
				String chosenPath = fileChooser.showSaveDialog(null).getAbsolutePath();
				// Open file writer
				FileWriter filewriter = new FileWriter(chosenPath);

				for (Employee employee : exportRows) {
					int employeeId = employee.getEmployeeId();

					Employee employee2 = EmployeeDAO.searchEmployeeWithId(employeeId);

					filewriter.append(Integer.toString(employee2.getEmployeeId()));
					filewriter.append(',');
					filewriter.append(employee2.getFirstName());
					filewriter.append(',');
					filewriter.append(employee2.getLastName());
					filewriter.append(',');
					/*filewriter.append(employee2.getmail());
					filewriter.append(',');*/
					filewriter.append(employee2.getloginUsername());
					filewriter.append(',');
					filewriter.append(employee2.getloginUsername());
					filewriter.append('\n');
				}
				// Flush and close
				filewriter.flush();
				filewriter.close();
				// Disconnect
				DbControl.dbCloseConnection();
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ACTIVITY 
	/**
	 * Populate activity.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateActivity() throws SQLException, ClassNotFoundException {
		// Get all items that are selected
		ObservableList<Employee> selectedEmployeeList = employeeTable.getSelectionModel().getSelectedItems();
		// Count number of elements in observableList
		int selectedEmployeeListSize = selectedEmployeeList.size();
		// if == 1 --> populate table
		if(selectedEmployeeListSize == 1) {
			// Loop
			for (Employee selectedEmployee : selectedEmployeeList) {
				int employeeId = selectedEmployee.getEmployeeId();
				ObservableList<Activity> table = ActivityDAO.searchActivity(employeeId);
				// Populate
				activityTable.setItems(table);
			}
		} else {
			// Clear
			activityTable.getItems().clear();

		}
	}

	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	//___________________________________________________________________________________________ORDER TAB 
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________

	// SEARCH 

	/**
	 * Search orders.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void searchOrders() throws SQLException, ClassNotFoundException {
		// Get all Orders information
		ObservableList<Order> orderData = OrderDAO.searchOrders();
		// Populate Orders on TableView
		populateOrders(orderData);

		// SOURCE: http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		// 1. ObservableList --> FilteredList (initially display all data)
		FilteredList<Order> filteredSearch = new FilteredList<>(orderData, p -> true);

		// 2. Set the filter whenever  filter changes
		searchPromptText.textProperty().addListener((observable, oldValue, newValue) -> {
			// Find which filter was selected
			String selectedSearchFilter = getSelectedSearchFilter();
			// For each character stroke: following function will be called --> update search filter:
			filteredSearch.setPredicate(order -> {
				// If searchText empty --> display all employees.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// convert to lower case
				String lowerCaseSearchText = newValue.toLowerCase();
				// Compare the search keywords and employee data based on selected search filter
				if(selectedSearchFilter.equals("Order ID")) {
					if(Integer.toString(order.getOrderId()).toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}
				else if (selectedSearchFilter.equals("Table Number")) {
					if(Integer.toString(order.getTableNumber()).toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}
				else if (selectedSearchFilter.equals("Total Bill")) {
					if(Integer.toString(order.getTotalBill()).toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}
				// else
				return false;
			});
		});
		// 3. FilteredList --> SortedList 
		SortedList<Order> sortedSearch = new SortedList<>(filteredSearch);
		// 4. Bind SL comp to TV comp
		sortedSearch.comparatorProperty().bind(ordersTable.comparatorProperty());
		// 5. Add data
		ordersTable.setItems(sortedSearch);
		// Several choices
		ordersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**
	 * Listens to which search criteria selection has been made
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void selectCategoryMadeTables() throws ClassNotFoundException, SQLException {
		searchOrders();
	}

	/**
	 * Populate drop down menu orders.
	 */
	private void populateDropDownMenuOrders() {
		// Source: http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
		ObservableList<String> options = 
				FXCollections.observableArrayList(
						"Order ID",
						"Table Number",
						"Total Bill"
						);
		// Populate search box with options
		searchByBoxOrder.setItems(options);
		// Set default value
		searchByBoxOrder.setValue("Order ID");
	}

	/**
	 * Search by time (Order)
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	// Perform the search by time search by time
	private void searchTimeOrder() throws ClassNotFoundException, SQLException {
		// Get start and end
		String start = String.valueOf(orderDatePickerStart.getValue()) + " " + orderTimeStart.getText();
		String end = String.valueOf(orderDatePickerEnd.getValue()) + " " + orderTimeEnd.getText();
		ObservableList<Order> orderList = OrderDAO.searchTime(start, end, 3);
		// Populate table
		populateOrders(orderList);
	}

	/**
	 * Call search by time
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void searchTimeBtnCallOrder() throws ClassNotFoundException, SQLException {
		// Perform time search
		searchTimeOrder();
	}

	/**
	 * Negate search by time order bool.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private void negateSearchByTimeOrderBool() throws ClassNotFoundException, SQLException{
		if(searchByTimeOrderBool) {
			searchByTimeOrderBool = false;
			searchPromptText.clear();
			subOrdersTable.getItems().clear();
			searchOrders();
		}
		else if(!searchByTimeOrderBool) {
			searchByTimeOrderBool = true;
			searchPromptText.clear();
			subOrdersTable.getItems().clear();
			searchOrders();
		}
	}

	/**
	 * Gets the selected search filter/ category.
	 *
	 * @return the selected search filter
	 */
	private String getSelectedSearchFilter() {
		return searchByBoxOrder.getValue();
	}

	/**
	 * Populate Orders for TableView
	 *
	 * @param ordData the ord data
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateOrders(ObservableList<Order> ordData) throws ClassNotFoundException {
		// Set items to the employeeTable
		ordersTable.setItems(ordData);
	}

	/**
	 * Populate sub orders TableView
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateSubOrders() throws SQLException, ClassNotFoundException {
		// Get OL
		ObservableList<Order> selectedOrderList = ordersTable.getSelectionModel().getSelectedItems();
		// Get Size
		int selectedOrderListSize = selectedOrderList.size();
		// If == 1
		if(selectedOrderListSize == 1) {

			for (Order selectedOrder : selectedOrderList) {
				int orderId = selectedOrder.getOrderId();
				ObservableList<Table> table = TableDAO.searchSubOrders(orderId);
				// Set items
				subOrdersTable.setItems(table);
			}
		} else {
			subOrdersTable.getItems().clear();

		}
	}

	// ROW SELECTION
	/**
	 * Determines whether or not a row is selected based on which further method is called
	 */
	private void isRowSelectedDetectorOrder() {
		// Source: http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
		ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				try {
					// If a row is selected --> display the content in Edit-TextFields
					populateSubOrders();
					activateBtnsOrder();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			} else {
				// If no row selected --> empty the Edit-TextFields
				// emptyBoxes();
			}
		});
	}

	// activate btns
	private void activateBtnsOrder() {
		deleteOrdBtn.setDisable(false);
		exportOrderBtn.setDisable(false);
	}

	// activate btns
	private void deactivateBtnsOrder() {
		deleteOrdBtn.setDisable(true);
		exportOrderBtn.setDisable(true);
	}

	// DELETE 

	/**
	 * Delete order.
	 *
	 * @param event the event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void deleteOrder(ActionEvent event) throws SQLException, ClassNotFoundException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Confirmation");
		alert.setContentText("Please confirm deletion");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){

			try {
				ObservableList<Order> deleteOrders = ordersTable.getSelectionModel().getSelectedItems();
				for (Order selectedOrder : deleteOrders) {

					// Update TableStatus:
					// table number of selected order
					// if table number == 0:
					// get status of table based on table number
					// if order id == status
					// update table status
					int tableNumber = selectedOrder.getTableNumber();
					if(tableNumber != 0) {
						TableStatus tableStatus = TableStatusDAO.searchTableStatus(tableNumber);
						if(selectedOrder.getOrderId() == tableStatus.getOrderId()) {
							TableStatusDAO.updateOrderId(0, tableNumber);
						}
					}
					// Delete Order
					OrderDAO.deleteOrderWithId(selectedOrder.getOrderId());
					// Delete corresponding suborders
					TableDAO.deleteSubOrderWithOrderId(selectedOrder.getOrderId());
					// Clear suborder table
					subOrdersTable.getItems().clear();
					// disable delete btn
					deactivateBtnsOrder();
				}
				// Update the displayed Orders again with changes 
				searchOrders();
			} catch (ClassNotFoundException | SQLException e) {
				throw e;
			}
		}
	}

	/**
	 * Export order.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// EXPORT
	@FXML
	private void exportOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
		// Source: Source: https://examples.javacodegeeks.com/core-java/writeread-csv-files-in-java-example/
		try {
			// Steps:
			// Access the selected items
			// Loop through selected items:
			// Get selected order Id
			// Get corresponding suborders
			// Loop through corresponding suborders
			// Added suborders to CSV
			// Exit suborder loop
			// Add order 

			// Establish connection
			DbControl.dbConnect();
			// Store selected rows in oberservable list
			ObservableList<Order> exportRows = ordersTable.getSelectionModel().getSelectedItems();
			// Open file chooser
			FileChooser fileChooser = new FileChooser();
			// Specify which file types you want
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
			try {
				String chosenPath = fileChooser.showSaveDialog(null).getAbsolutePath();
				// Open file writer
				FileWriter filewriter = new FileWriter(chosenPath);

				for (Order order : exportRows) {
					int orderId = order.getOrderId();
					ObservableList<Table> tableData = TableDAO.searchSubOrders(orderId);
					// Append suborders
					for(Table table : tableData) {
						filewriter.append(Integer.toString(orderId));
						filewriter.append(',');
						//filewriter.append(Integer.toString(order.getTableNumber()));
						//filewriter.append(Integer.toString(table.getTableNumber()));
						//filewriter.append(',');
						filewriter.append(Integer.toString(table.getSubOrderId()));
						filewriter.append(',');
						filewriter.append(table.getProductName());
						filewriter.append(',');
						filewriter.append(table.getProductCategory());
						filewriter.append(',');
						filewriter.append(Integer.toString(table.getProductPrice()));
						filewriter.append(',');
						filewriter.append(table.getComments());
						filewriter.append('\n');
					}
					// Append Orders
					String total = "Total: ";
					filewriter.append(total);
					filewriter.append(Integer.toString(orderId));
					filewriter.append(',');
					filewriter.append(Integer.toString(order.getTableNumber()));
					filewriter.append(',');
					filewriter.append(order.getOrderTime());
					filewriter.append(',');
					filewriter.append(Integer.toString(order.getTotalBill()));
					filewriter.append(',');
					filewriter.append(order.getSpecialRequests());
					filewriter.append('\n');
				}
				// Flush and close
				filewriter.flush();
				filewriter.close();
				// Disconnect
				DbControl.dbCloseConnection();
				// resultArea.setText("CSV exported");
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// IMPORT
	/**
	 * Import order.
	 *
	 * @param event the event
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void importOrder(ActionEvent event) throws ClassNotFoundException, SQLException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Confirmation");
		alert.setContentText("Please upload CSV file with comma seperated data\nFormat:\n1.List all corresponding suborders in format:[price,product,category,comment]\n"
				+ "2.Mark end of each order with keyword [Total:,] + [special request]\n"
				+ "3.Repeat steps 1 and 2 for additional orders");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				DbControl.dbImportOrder();
				// Update the displayed Orders again with changes 
				searchOrders();
			} catch (Exception e) {
				// resultArea.setText("Problem occurred while importing data " + e);
				e.printStackTrace();
			} finally {
				initialize();
			}
		}
	}

	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________MENU TAB 
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________

	// SEARCH
	/**
	 * Search products.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// @FXML
	private void searchProducts() throws SQLException, ClassNotFoundException {
		try {
			// Get all Products information
			ObservableList<Products> proData = ProductsDAO.searchProducts();
			// Populate Products on TableView
			populateProducts(proData);
			// SOURCE: http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
			// 1. ObservableList --> FilteredList (initially display all data)
			FilteredList<Products> filteredSearch = new FilteredList<>(proData, p -> true);
			// 2. Set the filter whenever  filter changes
			searchPromptTextProduct.textProperty().addListener((observable, oldValue, newValue) -> {
				// Find which filter was selected
				String selectedSearchFilter = getSelectedSearchFilterProducts();
				// For each character stroke: following function will be called --> update search filter:
				filteredSearch.setPredicate(product -> {
					// If searchText empty --> display all employees.
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					// convert to lower case
					String lowerCaseSearchText = newValue.toLowerCase();
					// Compare the search keywords and employee data based on selected search filter
					if(selectedSearchFilter.equals("Product ID")) {
						if(Integer.toString(product.getProductId()).toLowerCase().contains(lowerCaseSearchText)) {
							return true;
						}
					}
					else if (selectedSearchFilter.equals("Product Name")) {
						if(product.getProductName().toLowerCase().contains(lowerCaseSearchText)) {
							return true;
						}
					}
					else if (selectedSearchFilter.equals("Category")) {
						if(product.getProductCategory().toLowerCase().contains(lowerCaseSearchText)) {
							return true;
						}
					}
					else if(selectedSearchFilter.equals("Price")) {
						if(Integer.toString(product.getProductPrice()).toLowerCase().contains(lowerCaseSearchText)) {
							return true;
						}	
					}

					// else
					return false;
				});
			});
			// 3. FilteredList --> SortedList 
			SortedList<Products> sortedSearch = new SortedList<>(filteredSearch);
			// 4. Bind SL comp to TV comp
			sortedSearch.comparatorProperty().bind(productsTable.comparatorProperty());
			// 5. Add data
			productsTable.setItems(sortedSearch);
			// Several choices
			productsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Populate drop down menu products.
	 */
	private void populateDropDownMenuProducts() {
		// Source: http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
		ObservableList<String> options = 
				FXCollections.observableArrayList(
						"Product ID",
						"Product Name",
						"Category",
						"Price"
						);
		// Populate search box with options
		searchByBoxProduct.setItems(options);
		// Set default value
		searchByBoxProduct.setValue("Product Name");
	}

	/**
	 * Gets the selected search filter products
	 *
	 * @return the selected search filter products
	 */
	private String getSelectedSearchFilterProducts() {
		return searchByBoxProduct.getValue();
	}

	/**
	 * Populate products for Tableview
	 *
	 * @param proData the pro data
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateProducts(ObservableList<Products> proData) throws ClassNotFoundException {
		// Set items to the productsTable
		productsTable.setItems(proData);
	}

	// ROW SELECTION
	/**
	 * Determines whether or not a row is selected based on which further method is called
	 */
	private void isRowSelectedDetectorProducts() {
		// Source: http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
		productsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				try {
					// If a row is selected --> display the content in Edit-TextFields
					displaySelectionProducts();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			} else {
				// If no row selected --> empty the Edit-TextFields
				emptyBoxesProducts();
			}
		});
	}

	/**
	 * Display the content in Edit-TextFields
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void displaySelectionProducts() throws SQLException, ClassNotFoundException {
		try {
			// Access the elements from selected row
			Products selectedProduct = productsTable.getSelectionModel().getSelectedItem();

			editProductCategory.setText(selectedProduct.getProductCategory());
			editProductName.setText(selectedProduct.getProductName());
			editProductPrice.setText(Integer.toString(selectedProduct.getProductPrice()));

			// update the modify button
			deactivateAddProducts();

		} catch (Exception e) {
			// resultArea.setText("Problem occurred while accessing an employee.\n" + e);
		}
	}

	/**
	 * Empty boxes products.
	 */
	// Empty the Edit-TextFields
	private void emptyBoxesProducts() {
		editProductCategory.clear();
		editProductName.clear();
		editProductPrice.clear();
		// Update the insert button
		deactivaeUpdateDeleteProducts();
	}

	/**
	 * Deactivae update delete products.
	 */
	// Activate/ deactivate buttons
	private void deactivaeUpdateDeleteProducts() {
		// deactivate
		changeProductBtn.setDisable(true);
		deleteProductBtn.setDisable(true);
		// activate add button
		addProductBtn.setDisable(false);
	}

	/**
	 * Deactivate add products.
	 */
	private void deactivateAddProducts() {
		// activate add button
		addProductBtn.setDisable(true);
		// deactivate
		changeProductBtn.setDisable(false);
		deleteProductBtn.setDisable(false);
	}

	// UPDATE

	/**
	 * Update product.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void updateProduct() throws SQLException, ClassNotFoundException {
		try {
			if((!editProductName.getText().isEmpty()) && (!editProductName.getText().isEmpty()) && (!editProductPrice.getText().isEmpty())) {
				// Get the product name
				// Access updated values from edit boxes
				String productCategory = editProductCategory.getText();
				String productName = editProductName.getText();
				String productPrice = editProductPrice.getText();
				// Check if product already exists:
				// Access all products
				// ObservableList<Products> productList = ProductsDAO.searchProducts();
				// boolean to check if already exists
				boolean addNewProduct = true;
				/*
				// Loop through OL
				for (Products product : productList) {
					String existingProduct = product.getProductName();
					if(productName.equalsIgnoreCase(existingProduct)) {
						addNewProduct = false;
					}
				}
				// only if none was same --> add */
				if(addNewProduct) {
					// Insert product in DB
					// Create Products object to access productId
					Products selectedProduct = productsTable.getSelectionModel().getSelectedItem();
					int productId = selectedProduct.getProductId();
					// Update product in DB
					ProductsDAO.updateProduct(Integer.valueOf(productId), productCategory, productName, Integer.valueOf(productPrice));
					// Update the displayed Products again with changes
					searchProducts();
				} else {
					// Show alert
					showAlertInformation("Failure: " + productName + " has already been inserted");
				}
				// clear edit fields
				emptyBoxesProducts();
			} else {
				// Show alert
				showAlertInformation("All field required for new entry");
				// clear edit fields
				emptyBoxesProducts();
			}
		}catch (SQLException e) {
			//resultArea.setText("Problem occurred while inserting employee " + e);
			throw e;
		}
	}

	// INSERT
	/**
	 * Insert product.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void insertProduct() throws SQLException, ClassNotFoundException {
		try {
			if((!editProductName.getText().isEmpty()) && (!editProductName.getText().isEmpty()) && (!editProductPrice.getText().isEmpty())) {
				// Get the product name
				String productName = editProductName.getText();
				// Check if product already exists:
				// Access all products
				ObservableList<Products> productList = ProductsDAO.searchProducts();
				// boolean to check if already exists
				boolean addNewProduct = true;
				// Loop through OL
				for (Products product : productList) {
					String existingProduct = product.getProductName();
					if(productName.equalsIgnoreCase(existingProduct)) {
						addNewProduct = false;
					}
				}
				// only if none was same --> add
				if(addNewProduct) {
					// Insert product in DB
					ProductsDAO.insertProduct(editProductCategory.getText(), editProductName.getText(), Integer.valueOf(editProductPrice.getText()));
					// Update the displayed Products again with changes
					searchProducts();
				} else {
					// Show alert
					showAlertInformation("Failure: " + productName + " has already been inserted");
				}
				// clear edit fields
				emptyBoxesProducts();
			} else {
				// Show alert
				showAlertInformation("All field required for new entry");
				// clear edit fields
				emptyBoxesProducts();
			}

		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Show alert information.
	 *
	 * @param message the message
	 */
	private void showAlertInformation(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Confirmation");
		alert.setContentText(message);
		alert.showAndWait();
	}

	// DELETE
	/**
	 * Delete product.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void deleteProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			ObservableList<Products> deleteRows = productsTable.getSelectionModel().getSelectedItems();
			for (Products selectedProducts : deleteRows) {
				// delete
				ProductsDAO.deleteProductWithId(selectedProducts.getProductId());
			}
			// Update the displayed Employees again with changes
			searchProducts();
		} catch (SQLException e) {
			throw e;
		}
	}
}