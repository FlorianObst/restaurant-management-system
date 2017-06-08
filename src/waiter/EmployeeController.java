
package waiter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import application.BaseFunctionality;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import order.OrderController;
import order.Table;
import order.TableDAO;

/**
 *Handles functionality of fxml for Manager pages
 *<p>
 *Sources: 
 *https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm
 *http://stackoverflow.com/questions/10403838/prevent-an-accordion-in-javafx-from-collapsing
 *http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
 *
 *@author Florian Obst
 *@version Final
 */
public class EmployeeController extends BaseFunctionality {

	// Table tab
	@FXML
	private Label userLbl;
	@FXML
	private Label userId;
	@FXML
	private TextField searchPromptText;
	@FXML
	private TextField newProductNameText;
	@FXML
	private TextField tableText;
	@FXML
	private TextField productNameText;
	@FXML
	private TextField commentsText;
	@FXML
	private TableView<Order> ordersTable;
	@FXML
	private TableColumn<Order, Integer> ordIdColumn;
	@FXML
	private TableColumn<Order, Integer> tableNoColumn;
	@FXML
	private TableColumn<Order, String> orderTimeColumnn;
	@FXML
	private TableColumn<Order, String> commentsSpecialRequest;
	@FXML
	private TableColumn<Order, Integer> totalBillColumn; 
	@FXML
	private Button knopf1, knopf2, knopf3, knopf4, knopf5, knopf6, knopf7, knopf8, knopf9, knopf10, knopf11, knopf12;
	@FXML
	private Label tableOrderlbl1, tableOrderlbl2, tableOrderlbl3, tableOrderlbl4, tableOrderlbl5, tableOrderlbl6, tableOrderlbl7, tableOrderlbl8, tableOrderlbl9,tableOrderlbl10, tableOrderlbl11, tableOrderlbl12;
	@FXML
	private Label tableBilllbl1, tableBilllbl2, tableBilllbl3, tableBilllbl4, tableBilllbl5, tableBilllbl6, tableBilllbl7, tableBilllbl8, tableBilllbl9, tableBilllbl10, tableBilllbl11, tableBilllbl12;
	@FXML
	private Label tableOrderValue1, tableOrderValue2, tableOrderValue3, tableOrderValue4, tableOrderValue5, tableOrderValue6, tableOrderValue7, tableOrderValue8, tableOrderValue9, tableOrderValue10, tableOrderValue11, tableOrderValue12;
	@FXML
	private  Label tableBillValue1, tableBillValue2,  tableBillValue3, tableBillValue4, tableBillValue5, tableBillValue6, tableBillValue7, tableBillValue8, tableBillValue9, tableBillValue10, tableBillValue11, tableBillValue12;
	@FXML
	private ComboBox<String> searchByBox;
	@FXML
	private DatePicker tablesDatePickerStart;
	@FXML
	private DatePicker tablesDatePickerEnd;
	@FXML
	private TextField tablesTimeStart;
	@FXML
	private TextField tablesTimeEnd;
	@FXML
	private Button tablesSearchbyTime;
	@FXML
	private Accordion tablesAccordion;
	@FXML
	private TitledPane searchByKeywordTables;
	@FXML
	private TitledPane searchByTimeTables;
	@FXML
	private Button deleteOrdBtn;

	private boolean searchByTimeTablesBool = false;

	// Takeaway tab
	@FXML
	private TextField searchPromptTextTakeaway;
	@FXML
	private ComboBox<String> searchByBoxTakeaway;
	@FXML
	private Button deleteTakewayBtn;
	@FXML
	private Button closeTakeawayBtn;
	@FXML
	private TableView<Order> takeawayTable;
	@FXML
	private TableColumn<Order, Integer> takeawaydIdColumn;
	@FXML
	private TableColumn<Order, String> takewayTimeColumnn;
	@FXML
	private TableColumn<Order, Integer> takeawayTotalBillColumn;
	@FXML
	private TableColumn<Order, String> takeawaySpecialRequestColumn;
	@FXML
	private TableColumn<Order, String> takeawayTableNoColumn;
	@FXML 
	private ComboBox<String> searchByBoxOrder;
	@FXML
	private TextField searchPromptTextTakeawy;
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
	private DatePicker takeawayDatePickerStart;
	@FXML
	private DatePicker takeawayDatePickerEnd;
	@FXML
	private TextField takeawayTimeStart;
	@FXML
	private TextField takeawayTimeEnd;
	@FXML
	private Button takeawaySearchbyTime;
	@FXML
	private Accordion takeawayAccordion;
	@FXML
	private TitledPane searchByKeywordTakeaway;
	@FXML
	private TitledPane searchByTimeTakeaway;
	private boolean searchByTimeTakeawayBool = false;

	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	// INITIALIZE
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________

	/**
	 * Initialize.
	 */
	@FXML
	private void initialize() {
		// Initialize table buttons
		initializeTableButtons();

		// Initialize Orders table columns (Tables tab)
		ordIdColumn.setCellValueFactory(cellData -> cellData.getValue().OrderIdProperty().asObject());
		tableNoColumn.setCellValueFactory(cellData -> cellData.getValue().tableNumberProperty().asObject());
		orderTimeColumnn.setCellValueFactory(cellData -> cellData.getValue().orderTimeProperty());
		totalBillColumn.setCellValueFactory(cellData -> cellData.getValue().totalBillProperty().asObject());
		commentsSpecialRequest.setCellValueFactory(cellData -> cellData.getValue().specialRequestsProperty());

		// Initialize Takeaway table columns (Takeaway tab)
		takeawaydIdColumn.setCellValueFactory(cellData -> cellData.getValue().OrderIdProperty().asObject());
		takewayTimeColumnn.setCellValueFactory(cellData -> cellData.getValue().orderTimeProperty());
		takeawayTotalBillColumn.setCellValueFactory(cellData -> cellData.getValue().totalBillProperty().asObject());
		takeawaySpecialRequestColumn.setCellValueFactory(cellData -> cellData.getValue().specialRequestsProperty());
		// Only the modified "copy" of tablenumber column is shown; including tablenumbers as strings and status for takeaway
		takeawayTableNoColumn.setCellValueFactory(cellData -> cellData.getValue().tableNumberStatusProperty());
		// takeawayTableNoColumn.setCellValueFactory(cellData -> cellData.getValue().tableNumberProperty().asObject());

		// Initialize Suborders table columns (Takeaway tab)
		subOrderID.setCellValueFactory(cellData -> cellData.getValue().sUbOrderIdProperty().asObject());
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		productCategory.setCellValueFactory(cellData -> cellData.getValue().productCategoryProperty());
		productPrice.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
		subOrderComment.setCellValueFactory(cellData -> cellData.getValue().commentsProperty());

		// Populate drop down/ ComboBoxes when page is opened
		populateDropDownMenuTakeaway(); // Takeaway
		populateDropDownMenuTable(); // Table

		// Fill the tableView
		try {
			// Populate TableView based on search filters
			searchOrders(); // Tables
			searchTakeaway(); // Takeaway

			// Depict tables
			determineStatusOfTableDisplay(); // Tables
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// Detect whether row is selected
		isRowSelectedDetectorTakeaway(); // Takeaway
		isRowSelectedDetectorTable();

		// Disable row selection for Suborders table
		subOrdersTable.setSelectionModel(null); // Takeaway

		// Default button status: 
		closeTakeawayBtn.setDisable(true); // Takeaway
		deleteOrdBtn.setDisable(true); // Tables

		// Disable selecting end day before start day
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
								tablesDatePickerStart.getValue().plusDays(0))
								) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}   
					}
				};
			}
		};
		// Add restriction to end date
		tablesDatePickerEnd.setDayCellFactory(dayCellFactory);

		// Disable selecting end day before start day
		// Source: https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm
		final Callback<DatePicker, DateCell> dayCellFactory2 = 
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(
								takeawayDatePickerStart.getValue().plusDays(0))
								) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}   
					}
				};
			}
		};
		// Add restriction to end date
		takeawayDatePickerEnd.setDayCellFactory(dayCellFactory2);

		// Hide weeks for date pickers
		tablesDatePickerStart.setShowWeekNumbers(false);
		tablesDatePickerEnd.setShowWeekNumbers(false);
		takeawayDatePickerStart.setShowWeekNumbers(false);
		takeawayDatePickerEnd.setShowWeekNumbers(false);

		// Accordion
		tablesAccordion.setExpandedPane(searchByKeywordTables);
		takeawayAccordion.setExpandedPane(searchByKeywordTakeaway);

		// create an accordion, ensuring the currently expanded pane can not be clicked on to collapse.
		// Source: http://stackoverflow.com/questions/10403838/prevent-an-accordion-in-javafx-from-collapsing
		tablesAccordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
			@Override 
			public void changed(ObservableValue<? extends TitledPane> property, final TitledPane oldPane, final TitledPane newPane) {
				if (oldPane != null) oldPane.setCollapsible(true);
				if (newPane != null) Platform.runLater(new Runnable() { @Override public void run() { 
					newPane.setCollapsible(false); 
					try {
						negateSearchByTimeTablesBool();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}});
			}
		});
		for (TitledPane pane: tablesAccordion.getPanes()) pane.setAnimated(false);
		tablesAccordion.setExpandedPane(tablesAccordion.getPanes().get(0));

		// create an accordion, ensuring the currently expanded pane can not be clicked on to collapse.
		takeawayAccordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
			@Override 
			public void changed(ObservableValue<? extends TitledPane> property, final TitledPane oldPane, final TitledPane newPane) {
				if (oldPane != null) oldPane.setCollapsible(true);
				if (newPane != null) Platform.runLater(new Runnable() { @Override public void run() { 
					newPane.setCollapsible(false); 
					try {
						negateSearchByTimeTakeawayBool();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}});
			}
		});
		for (TitledPane pane: takeawayAccordion.getPanes()) pane.setAnimated(false);
		takeawayAccordion.setExpandedPane(takeawayAccordion.getPanes().get(0));

		// Set string in TableView
		subOrdersTable.setPlaceholder(new Label("Please select an order"));
		ordersTable.setPlaceholder(new Label("Please open an order by clicking on a table"));
		takeawayTable.setPlaceholder(new Label("No takeaway orders"));
	}


	/**
	 * Determine status of table display.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	// Determine status of the displayed tables
	private void determineStatusOfTableDisplay() throws ClassNotFoundException, SQLException {
		ObservableList<TableStatus> tableStatusList = TableStatusDAO.searchTableStatuses();
		// Loop through each tableStatus
		for(TableStatus tableStatus : tableStatusList) {
			// Get order Id and tn
			int orderId = tableStatus.getOrderId();
			int tableNumber = tableStatus.getTableNumber();
			if(orderId != 0) {
				// populate
				// Access corresponding order
				Order order = OrderDAO.searchTable(orderId);
				// Access corresponding bill
				int bill = order.getTotalBill();
				// Populate the buttons
				populateOccupiedTables(tableNumber, orderId, bill);
			} else {
				// Clear
				clearUnoccupiedTables(tableNumber);
			}

		}
	}

	/**
	 * Initialize buttons
	 */
	private void initializeTableButtons() {
		knopf1.setOnAction((event) -> {
			checkTableStatus(event, 1);
		});
		knopf2.setOnAction((event) -> {
			checkTableStatus(event, 2);
		});
		knopf3.setOnAction((event) -> {
			checkTableStatus(event, 3);
		});
		knopf4.setOnAction((event) -> {
			checkTableStatus(event, 4);
		});
		knopf5.setOnAction((event) -> {
			checkTableStatus(event, 5);
		});
		knopf6.setOnAction((event) -> {
			checkTableStatus(event, 6);
		});
		knopf7.setOnAction((event) -> {
			checkTableStatus(event, 7);
		});
		knopf8.setOnAction((event) -> {
			checkTableStatus(event, 8);
		});
		knopf9.setOnAction((event) -> {
			checkTableStatus(event, 9);
		});
		knopf10.setOnAction((event) -> {
			checkTableStatus(event, 10);
		});
		knopf11.setOnAction((event) -> {
			checkTableStatus(event, 11);
		});
		knopf12.setOnAction((event) -> {
			checkTableStatus(event, 12);
		});
	}

	/**
	 * Populate occupied tables.
	 *
	 * @param tableNumber the table number
	 * @param orderId the order id
	 * @param bill the bill
	 */
	private  void populateOccupiedTables(int tableNumber, int orderId, int bill) {
		switch(tableNumber) {
		case 1: 
			tableOrderlbl1.setText("ID: ");
			tableBilllbl1.setText("Bill: ");
			tableOrderValue1.setText(Integer.toString(orderId));
			tableBillValue1.setText(Integer.toString(bill));
			break;
		case 2: 
			tableOrderlbl2.setText("ID: ");
			tableBilllbl2.setText("Bill: ");
			tableOrderValue2.setText(Integer.toString(orderId));
			tableBillValue2.setText(Integer.toString(bill));
			break;
		case 3: 
			tableOrderlbl3.setText("ID: ");
			tableBilllbl3.setText("Bill: ");
			tableOrderValue3.setText(Integer.toString(orderId));
			tableBillValue3.setText(Integer.toString(bill));
			break;
		case 4: 
			tableOrderlbl4.setText("ID: ");
			tableBilllbl4.setText("Bill: ");
			tableOrderValue4.setText(Integer.toString(orderId));
			tableBillValue4.setText(Integer.toString(bill));
			break;
		case 5: 
			tableOrderlbl5.setText("ID: ");
			tableBilllbl5.setText("Bill: ");
			tableOrderValue5.setText(Integer.toString(orderId));
			tableBillValue5.setText(Integer.toString(bill));
			break;
		case 6: 
			tableOrderlbl6.setText("ID: ");
			tableBilllbl6.setText("Bill: ");
			tableOrderValue6.setText(Integer.toString(orderId));
			tableBillValue6.setText(Integer.toString(bill));
			break;
		case 7: 
			tableOrderlbl7.setText("ID: ");
			tableBilllbl7.setText("Bill: ");
			tableOrderValue7.setText(Integer.toString(orderId));
			tableBillValue7.setText(Integer.toString(bill));
			break;
		case 8: 
			tableOrderlbl8.setText("ID: ");
			tableBilllbl8.setText("Bill: ");
			tableOrderValue8.setText(Integer.toString(orderId));
			tableBillValue8.setText(Integer.toString(bill));
			break;
		case 9: 
			tableOrderlbl9.setText("ID: ");
			tableBilllbl9.setText("Bill: ");
			tableOrderValue9.setText(Integer.toString(orderId));
			tableBillValue9.setText(Integer.toString(bill));
			break;
		case 10: 
			tableOrderlbl10.setText("ID: ");
			tableBilllbl10.setText("Bill: ");
			tableOrderValue10.setText(Integer.toString(orderId));
			tableBillValue10.setText(Integer.toString(bill));
			break;
		case 11: 
			tableOrderlbl11.setText("ID: ");
			tableBilllbl11.setText("Bill: ");
			tableOrderValue11.setText(Integer.toString(orderId));
			tableBillValue11.setText(Integer.toString(bill));
			break;
		case 12: 
			tableOrderlbl12.setText("ID: ");
			tableBilllbl12.setText("Bill: ");
			tableOrderValue12.setText(Integer.toString(orderId));
			tableBillValue12.setText(Integer.toString(bill));
			break;
		}
	}

	/**
	 * Clear unoccupied tables.
	 *
	 * @param tableNumber the table number
	 */
	private void clearUnoccupiedTables(int tableNumber) {
		switch(tableNumber) {
		case 1: 
			tableOrderlbl1.setText("Free");
			tableBilllbl1.setText("");
			tableOrderValue1.setText("");
			tableBillValue1.setText("");
			break;
		case 2: 
			tableOrderlbl2.setText("Free");
			tableBilllbl2.setText("");
			tableOrderValue2.setText("");
			tableBillValue2.setText("");
			break;
		case 3: 
			tableOrderlbl3.setText("Free");
			tableBilllbl3.setText("");
			tableOrderValue3.setText("");
			tableBillValue3.setText("");
			break;
		case 4: 
			tableOrderlbl4.setText("Free");
			tableBilllbl4.setText("");
			tableOrderValue4.setText("");
			tableBillValue4.setText("");
			break;
		case 5: 
			tableOrderlbl5.setText("Free");
			tableBilllbl5.setText("");
			tableOrderValue5.setText("");
			tableBillValue5.setText("");
			break;
		case 6: 
			tableOrderlbl6.setText("Free");
			tableBilllbl6.setText("");
			tableOrderValue6.setText("");
			tableBillValue6.setText("");
			break;
		case 7: 
			tableOrderlbl7.setText("Free");
			tableBilllbl7.setText("");
			tableOrderValue7.setText("");
			tableBillValue7.setText("");
			break;
		case 8: 
			tableOrderlbl8.setText("Free");
			tableBilllbl8.setText("");
			tableOrderValue8.setText("");
			tableBillValue8.setText("");
			break;
		case 9: 
			tableOrderlbl9.setText("Free");
			tableBilllbl9.setText("");
			tableOrderValue9.setText("");
			tableBillValue9.setText("");
			break;
		case 10: 
			tableOrderlbl10.setText("Free");
			tableBilllbl10.setText("");
			tableOrderValue10.setText("");
			tableBillValue10.setText("");
			break;
		case 11: 
			tableOrderlbl11.setText("Free");
			tableBilllbl11.setText("");
			tableOrderValue11.setText("");
			tableBillValue11.setText("");
			break;
		case 12: 
			tableOrderlbl12.setText("Free");
			tableBilllbl12.setText("");
			tableOrderValue12.setText("");
			tableBillValue12.setText("");
			break;

		}
	}


	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	//_____________________________________________________________________________________________GENERAL
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________

	/**
	 * Sets the waiter.
	 *
	 * @param user the user
	 * @param employeeId the employee id
	 */
	public void SetWaiter(String user, int employeeId) {
		userLbl.setText(user);
		userId.setText(Integer.toString(employeeId));
	}

	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	//______________________________________________________________________________________________TABLES
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________

	// SEARCH

	/**
	 * Select category made tables.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void selectCategoryMadeTables() throws ClassNotFoundException, SQLException {
		searchOrders();
	}

	/**
	 * Search orders.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// @FXML
	private void searchOrders() throws SQLException, ClassNotFoundException {
		// Get all Orders information
		ObservableList<Order> orderData = OrderDAO.searchOrdersByTableNo(1);
		// Populate Orders on TableView
		populateEmployees(orderData);

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
	 * Populate drop down menu table.
	 */
	private void populateDropDownMenuTable() {
		// Source: http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
		ObservableList<String> options = 
				FXCollections.observableArrayList(
						"Order ID",
						"Table Number",
						"Total Bill"
						);
		// Populate search box with options
		searchByBox.setItems(options);
		// Set default value
		searchByBox.setValue("Order ID");
	}

	/**
	 * Gets the selected search filter.
	 *
	 * @return the selected search filter
	 */
	private String getSelectedSearchFilter() {
		return searchByBox.getValue();
	}

	/**
	 * Populate employees.
	 *
	 * @param empData the emp data
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateEmployees(ObservableList<Order> empData) throws ClassNotFoundException {
		// Set items to the employeeTable
		ordersTable.setItems(empData);
	}	

	/**
	 * Search time tables.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private void searchTimeTables() throws ClassNotFoundException, SQLException {
		// Get start and end
		String start = String.valueOf(tablesDatePickerStart.getValue()) + " " + tablesTimeStart.getText();
		String end = String.valueOf(tablesDatePickerEnd.getValue()) + " " + tablesTimeEnd.getText();
		ObservableList<Order> orderList = OrderDAO.searchTime(start, end, 1);
		// Populate table
		populateEmployees(orderList);
	}

	/**
	 * Search time btn call tables.
	 * Call search by time
	 * 
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void searchTimeBtnCallTables() throws ClassNotFoundException, SQLException {
		try {
			// Perform time search
			searchTimeTables();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Negate search by time tables bool.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private void negateSearchByTimeTablesBool() throws ClassNotFoundException, SQLException{
		try {
			if(searchByTimeTablesBool) {
				searchByTimeTablesBool = false;
				searchPromptText.clear();
				searchOrders();
			}
			else if(!searchByTimeTablesBool) {
				searchByTimeTablesBool = true;
				searchPromptText.clear();
				searchOrders();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete order.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void deleteOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Confirmation");
		alert.setContentText("Please confirm deletion");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				ObservableList<Order> deleteOrders = ordersTable.getSelectionModel().getSelectedItems();
				for (Order selectedOrder : deleteOrders) {
					// Activity 
					boolean orderWasClosed = true; 

					// Update TableStatus:
					// table number of selected order
					// if table number == 0:
					// get status of table based on table number
					// if order id == status
					// update table status
					int tableNumber = selectedOrder.getTableNumber();
					if(tableNumber > 0) {
						TableStatus tableStatus = TableStatusDAO.searchTableStatus(tableNumber);
						if(selectedOrder.getOrderId() == tableStatus.getOrderId()) {
							TableStatusDAO.updateOrderId(0, tableNumber);
							// Activity
							deleteUnclosedActivity(selectedOrder.getOrderId(), tableNumber, Integer.valueOf(userId.getText()));
							orderWasClosed = false;
						}
					}
					// Delete Order
					OrderDAO.deleteOrderWithId(selectedOrder.getOrderId());
					// Delete corresponding suborders
					TableDAO.deleteSubOrderWithOrderId(selectedOrder.getOrderId());
					deleteOrdBtn.setDisable(true); // Takeaway
					if(orderWasClosed) {
						// Activity 
						deleteClosedActivity(selectedOrder.getOrderId(), tableNumber, Integer.valueOf(userId.getText()));
					}
				}
				// Update the displayed Orders again with changes 
				searchOrders();
				searchTakeaway();
				// resultArea.setText("Orders deleted!");
				// Depict tables
				determineStatusOfTableDisplay();
			} catch (SQLException e) {
				// resultArea.setText("Problem occurred while deleting order " + e);
				throw e;
			}
		}
	}

	/**
	 * Check table status.
	 *
	 * @param actionEvent the action event
	 * @param tableNumber the table number
	 */
	public void checkTableStatus(ActionEvent actionEvent, int tableNumber) {
		try {
			TableStatus tableStatus = TableStatusDAO.searchTableStatus(tableNumber);
			if(tableStatus.getOrderId() == 0) {
				// get the orderId
				int max = maxOrderId();

				if (max > 0) {
					// Store new orderId
					// Open fxml
					openOrder(actionEvent, tableNumber, max);
				}
			}
			else {
				// resultArea.setText("Continue on order");
				int orderId = tableStatus.getOrderId();
				openOrder(actionEvent, tableNumber, orderId);
			}
			// 
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Max order id.
	 *
	 * @return the int
	 */
	public static int maxOrderId() {
		try {
			// find the max OrderId
			ObservableList<Order> orderList = OrderDAO.searchOrders();
			// local variables to store max
			int max = Integer.MIN_VALUE;
			int OrderId = 0;
			// loop to find max
			for(Order i : orderList) {
				OrderId = i.getOrderId();
				if(OrderId > max) {
					max = OrderId;
				}
			}
			if(max < 0)
				max = 1;
			else 
				// Increment orderId
				max++;
			return max;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Open orderfxml
	 *
	 * @param event the event
	 * @param tableNumber the table number
	 * @param orderId the order id
	 */
	public void openOrder(ActionEvent event, int tableNumber, int orderId) {
		try {
			// set the static variables
			// these are used as filters for the tableview
			TableDAO.setTableNumber(tableNumber);

			TableDAO.setOrderId(orderId);

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/order/Order.fxml").openStream());
			Scene scene = new Scene(root);

			OrderController orderController = (OrderController)loader.getController();

			// Convert int to string
			String tableNumberString = String.valueOf(tableNumber);
			String tableOrderIdString = String.valueOf(orderId);

			// Set table and order Id on Order page
			orderController.setTableNumberText(tableNumberString);
			orderController.setOrderIdText(tableOrderIdString);
			orderController.SetWaiter(userLbl.getText(), Integer.valueOf(userId.getText()));
			// Set total bill 
			orderController.calculateBill();

			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ROW SELECTION
	/**
	 * Checks if is row selected detector takeaway.
	 */
	// Determines whether or not a row is selected based on which further method is called
	private void isRowSelectedDetectorTable() {
		// Source: http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
		ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				activateTableDeleteBtn();
			}
		});
	}

	private void activateTableDeleteBtn() {
		deleteOrdBtn.setDisable(false);
	}


	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	// TAKEAWAY
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________


	// SEARCH
	/**
	 * Search takeaway.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void searchTakeaway() throws SQLException, ClassNotFoundException {
		// Get all Orders information
		ObservableList<Order> orderData = OrderDAO.searchOrdersByTableNo(2);
		// Populate Orders on TableView
		populateTakeaway(orderData);
		// SOURCE: http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		// 1. ObservableList --> FilteredList (initially display all data)
		FilteredList<Order> filteredSearch = new FilteredList<>(orderData, p -> true);

		// 2. Set the filter whenever  filter changes
		searchPromptTextTakeawy.textProperty().addListener((observable, oldValue, newValue) -> {
			// Find which filter was selected
			String selectedSearchFilter = getSelectedSearchFilterTakeway();
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
		sortedSearch.comparatorProperty().bind(takeawayTable.comparatorProperty());
		// 5. Add data
		takeawayTable.setItems(sortedSearch);
		// Several choices
		takeawayTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**
	 * Populate drop down menu takeaway.
	 */
	private void populateDropDownMenuTakeaway() {
		// Source: http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
		ObservableList<String> options = 
				FXCollections.observableArrayList(
						"Order ID",
						"Total Bill"
						);
		// Populate search box with options
		searchByBoxTakeaway.setItems(options);
		// Set default value
		searchByBoxTakeaway.setValue("Order ID");
	}

	/**
	 * Gets the selected search filter takeway.
	 *
	 * @return the selected search filter takeway
	 */
	private String getSelectedSearchFilterTakeway() {
		return searchByBoxTakeaway.getValue();
	}

	/**
	 * Populate takeaway.
	 *
	 * @param ordData the ord data
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateTakeaway(ObservableList<Order> ordData) throws ClassNotFoundException {
		// Set items to the employeeTable
		takeawayTable.setItems(ordData);
	}

	/**
	 * Populate sub orders.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateSubOrders() throws SQLException, ClassNotFoundException {
		// Get all items that are selected
		// Count number of elements in observableList
		// if == 1 --> populate table
		ObservableList<Order> selectedOrderList = takeawayTable.getSelectionModel().getSelectedItems();
		int selectedOrderListSize = selectedOrderList.size();
		if(selectedOrderListSize == 1) {
			for (Order selectedOrder : selectedOrderList) {
				int orderId = selectedOrder.getOrderId();
				ObservableList<Table> table = TableDAO.searchSubOrders(orderId);
				subOrdersTable.setItems(table);
				// Also activate close button
				// Only one takeaway at a time can be closed, in order to check
				if(selectedOrder.getTableNumber() == 0) {
					// Activate close button
					activateClose();
				} else {
					deactivateClose();
				}
			}
		} else {
			subOrdersTable.getItems().clear();
			// Deactivate close button
			deactivateClose();
		}
	}


	/**
	 * Perform the search by time search by time
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private void searchTimeTakeaway() throws ClassNotFoundException, SQLException {
		try {
			// Get start and end
			String start = String.valueOf(takeawayDatePickerStart.getValue()) + " " + takeawayTimeStart.getText();
			String end = String.valueOf(takeawayDatePickerEnd.getValue()) + " " + takeawayTimeEnd.getText();
			ObservableList<Order> orderList = OrderDAO.searchTime(start, end, 2);
			// Populate table
			populateTakeaway(orderList);
		} catch (ClassNotFoundException | SQLException e) {

		}
	}

	/**
	 * Call search by time
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void searchTimeBtnCallTakeaway() throws ClassNotFoundException, SQLException {
		// Perform time search
		searchTimeTakeaway();
	}

	/**
	 * Negate search by time takeaway bool.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private void negateSearchByTimeTakeawayBool() throws ClassNotFoundException, SQLException{
		try {
			if(searchByTimeTakeawayBool) {
				searchByTimeTakeawayBool = false;
				searchPromptTextTakeawy.clear();
				searchTakeaway();
			}
			else if(!searchByTimeTakeawayBool) {
				searchByTimeTakeawayBool = true;
				searchPromptTextTakeawy.clear();
				searchTakeaway();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// ROW SELECTION
	/**
	 * Checks if is row selected detector takeaway.
	 */
	// Determines whether or not a row is selected based on which further method is called
	private void isRowSelectedDetectorTakeaway() {
		// Source: http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
		takeawayTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {

				try {
					populateSubOrders();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			} else {

			}
		});
	}

	/**
	 * Deactivate close.
	 */
	private void deactivateClose() {
		// deactivate
		closeTakeawayBtn.setDisable(true);

	}

	/**
	 * Activate close.
	 */
	private void activateClose() {
		// activate
		closeTakeawayBtn.setDisable(false);
	}

	// DELETE
	/**
	 * Delete takeaway.
	 *
	 * @param event the event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void deleteTakeaway(ActionEvent event) throws SQLException, ClassNotFoundException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Confirmation");
		alert.setContentText("Please confirm deletion");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){

			try {
				ObservableList<Order> deleteOrders = takeawayTable.getSelectionModel().getSelectedItems();
				for (Order selectedOrder : deleteOrders) {

					// Update TableStatus:
					// table number of selected order
					// if table number == 0:
					// get status of table based on table number
					// if order id == status
					// update table status
					if(selectedOrder.getTableNumber() == 0) {
						deleteUnclosedActivity(selectedOrder.getOrderId(), 0, Integer.valueOf(userId.getText()));
					} else if(selectedOrder.getTableNumber() == -1) {
						deleteClosedActivity(selectedOrder.getOrderId(), 0, Integer.valueOf(userId.getText()));
					}
					// Delete Order
					OrderDAO.deleteOrderWithId(selectedOrder.getOrderId());
					// Delete corresponding suborders
					TableDAO.deleteSubOrderWithOrderId(selectedOrder.getOrderId());
					// Clear suborder table
					subOrdersTable.getItems().clear();
				}
				// Update the displayed Orders again with changes 
				searchOrders();
				searchTakeaway();
			} catch (ClassNotFoundException | SQLException e) {
				throw e;
			}
		}
	}

	/**
	 * Complete take away order.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void completeTakeAwayOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		ObservableList<Order> selectedOrderList = takeawayTable.getSelectionModel().getSelectedItems();
		for(Order selectedOrder : selectedOrderList) {
			// Update takeaway status
			OrderDAO.closeTakeaway(selectedOrder.getOrderId());
			// Activity
			closeActivity(selectedOrder.getOrderId(), 0, Integer.valueOf(userId.getText()));
			// Update tables
			searchTakeaway();
			populateSubOrders();
		}
	}

	// EMPLOYEE ACTIVITY
	/**
	 * Open activity.
	 *
	 * @param orderId the order id
	 * @param tableNumber the table number
	 * @param employeeId the employee id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void openActivity(int orderId, int tableNumber, int employeeId) throws SQLException, ClassNotFoundException {
		// Define activity
		String activity = "Opened new order with orderId: " + orderId + " on table: " + tableNumber;
		try {
			ActivityDAO.insertActivity(employeeId, activity);
		}catch (ClassNotFoundException | SQLException e) {
			throw e;
		}
	}

	/**
	 * Delete unclosed activity.
	 *
	 * @param orderId the order id
	 * @param tableNumber the table number
	 * @param employeeId the employee id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void deleteUnclosedActivity(int orderId, int tableNumber, int employeeId) throws SQLException, ClassNotFoundException {
		// Define activity
		String activity = "Deleted UNPAID order with orderId: " + orderId + " on table: " + tableNumber;

		try {
			ActivityDAO.insertActivity(employeeId, activity);
		}catch (ClassNotFoundException | SQLException e) {
			throw e;
		}
	}

	/**
	 * Delete closed activity.
	 *
	 * @param orderId the order id
	 * @param tableNumber the table number
	 * @param employeeId the employee id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void deleteClosedActivity(int orderId, int tableNumber, int employeeId) throws SQLException, ClassNotFoundException {
		// Define activity
		String activity = "Deleted PAID order with orderId: " + orderId + " on table: " + tableNumber;

		try {
			ActivityDAO.insertActivity(employeeId, activity);
		}catch (ClassNotFoundException | SQLException e) {
			throw e;
		}
	}

	/**
	 * Close activity.
	 *
	 * @param orderId the order id
	 * @param tableNumber the table number
	 * @param employeeId the employee id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void closeActivity(int orderId, int tableNumber, int employeeId) throws SQLException, ClassNotFoundException {
		// Define activity
		String activity;
		if(tableNumber == 0) {
			// Takeaway
			activity = "Closed takeaway order with orderId: " + orderId;
		} else {
			// Table
			activity = "Closed order with orderId: " + orderId + " on table: " + tableNumber;
		}

		try {
			ActivityDAO.insertActivity(employeeId, activity);
		}catch (ClassNotFoundException | SQLException e) {
			throw e;
		}
	}
}	
