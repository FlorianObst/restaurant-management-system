package order;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import products.Products;
import products.ProductsDAO;
import waiter.EmployeeController;
import waiter.Order;
import waiter.OrderDAO;
import waiter.TableStatus;
import waiter.TableStatusDAO;

import java.sql.SQLException;
import java.util.Optional;

import application.BaseFunctionality;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

/**
 *Handles functionality of fxml for Employee pages
 *<p>
 *Sources: 
 *http://stackoverflow.com/questions/19490868/how-to-unselect-a-selected-table-row-upon-second-click-selection-in-javafx
 *http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
 *http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
 *
 *@author Florian Obst
 *@version Final
 */
public class OrderController extends BaseFunctionality {
	@FXML
	private Button deleteOrdBtn;
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
	private Label userLbl;
	@FXML
	private Label userId;
	@FXML
	private TextField searchPromptText;
	@FXML
	private Button updateBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button saveAndBack;
	@FXML 
	private Label tableNumberText;
	@FXML
	private Label orderIdText;
	@FXML
	private Label totalBillText;
	@FXML
	private TextField specialRequestPrompt;
	@FXML
	private Button closeOrderBtn;
	@FXML
	private ComboBox<String> searchByBox;
	@FXML
	private ComboBox<String> productCategoryBox;
	@FXML
	private ComboBox<String> productNameBox;
	@FXML
	private TextField commentPromptBox;
	@FXML
	private TextField editCommentText;
	@FXML
	private Button deleteOrder;

	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________
	// INITIALIZE
	//____________________________________________________________________________________________________
	//____________________________________________________________________________________________________

	/**
	 * Initialize.
	 */
	// INITIALIZE _______________________________________________________________________________________________________________________________________
	@FXML
	private void initialize() {

		// Initialize suborder table columns 
		subOrderID.setCellValueFactory(cellData -> cellData.getValue().sUbOrderIdProperty().asObject());
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		productCategory.setCellValueFactory(cellData -> cellData.getValue().productCategoryProperty());
		productPrice.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
		subOrderComment.setCellValueFactory(cellData -> cellData.getValue().commentsProperty());

		// Populate the drop down menus
		populateSearchDropDown();
		populateCategoryDropDown();

		// Fill the tableView
		try {
			// Populate TableView based on search filters
			searchOrders();
			// Populate special request field
			showSpecialRequest();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// Enable select and deselect by clicking on a row
		// Source: http://stackoverflow.com/questions/19490868/how-to-unselect-a-selected-table-row-upon-second-click-selection-in-javafx
		subOrdersTable.setRowFactory(new Callback<TableView<Table>, TableRow<Table>>() {  
			@Override  
			public TableRow<Table> call(TableView<Table> tableView2) {  
				final TableRow<Table> row = new TableRow<>();  
				// Enable MouseEvent
				row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {  
					@Override  
					public void handle(MouseEvent event) {  
						final int index = row.getIndex();  
						// Apply to suborderstable
						if (index >= 0 && index < subOrdersTable.getItems().size() && subOrdersTable.getSelectionModel().isSelected(index)  ) {
							subOrdersTable.getSelectionModel().clearSelection();
							event.consume();  
						}  
					}  
				});  
				return row;  
			}  
		});

		// check if row selected
		isRowSelectedDetector();

		// Set string in TableView
		subOrdersTable.setPlaceholder(new Label("Please add  product"));
		
		// Deactivate btns
		deactivateBtns();
	}

	// GENERAL

	/**
	 * Sign out.
	 *
	 * @param event the event
	 */
	@FXML
	public void signOut(ActionEvent event) {
		try {
			super.signOut(event);
			updateSpecialRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Back to employee.
	 *
	 * @param event the event
	 * @throws SQLException the SQL exception
	 */
	@FXML
	public void backToEmployee(ActionEvent event) throws SQLException {
		try {
			// save again so incorporate special requests
			updateSpecialRequest();

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/waiter/EmployeeView.fxml").openStream());
			Scene scene = new Scene(root);

			EmployeeController employeeController = (EmployeeController) loader.getController();
			employeeController.SetWaiter(userLbl.getText(), Integer.valueOf(userId.getText()));

			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// SEARCH
	/**
	 * Search orders.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void searchOrders() throws SQLException, ClassNotFoundException {
		// Get all Suborders information
		ObservableList<Table> tableData = TableDAO.searchSubOrders();
		// Populate Suborders on TableView
		populateSubOrders(tableData);

		// SOURCE: http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		// 1. ObservableList --> FilteredList (initially display all data)
		FilteredList<Table> filteredSearch = new FilteredList<>(tableData, p -> true);

		// 2. Set the filter whenever  filter changes
		searchPromptText.textProperty().addListener((observable, oldValue, newValue) -> {
			// Find which filter was selected
			String selectedSearchFilter = getSelectedSearchFilter();
			// For each character stroke: following function will be called --> update search filter:
			filteredSearch.setPredicate(subOrder -> {
				// If searchText empty --> display all employees.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// convert to lower case
				String lowerCaseSearchText = newValue.toLowerCase();
				// Compare the search keywords and employee data based on selected search filter
				if(selectedSearchFilter.equals("Sub-order ID")) {
					if(Integer.toString(subOrder.getSubOrderId()).toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}
				else if (selectedSearchFilter.equals("Product")) {
					if(subOrder.getProductName().toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}else if (selectedSearchFilter.equals("Product category")) {
					if(subOrder.getProductCategory().toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}else if (selectedSearchFilter.equals("Product price")) {
					if(Integer.toString(subOrder.getProductPrice()).toLowerCase().contains(lowerCaseSearchText)) {
						return true;
					}
				}
				// else
				return false;
			});
		});
		// 3. FilteredList --> SortedList 
		SortedList<Table> sortedSearch = new SortedList<>(filteredSearch);
		// 4. Bind SL comp to TV comp
		sortedSearch.comparatorProperty().bind(subOrdersTable.comparatorProperty());
		// 5. Add data
		subOrdersTable.setItems(sortedSearch);
		// Several choices
		subOrdersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**
	 * Populate search drop down.
	 */
	private void populateSearchDropDown() {
		// Source: http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
		ObservableList<String> options = 
				FXCollections.observableArrayList(
						"Sub-order ID",
						"Product",
						"Product category",
						"Product price"
						);
		// Populate search box with options
		searchByBox.setItems(options);
		// Set default value
		searchByBox.setValue("Sub-order ID");
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
	 * Populate sub orders.
	 *
	 * @param subOrderData the sub order data
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void populateSubOrders(ObservableList<Table> subOrderData) throws ClassNotFoundException {
		// Set items to the employeeTable
		subOrdersTable.setItems(subOrderData);
	}

	/**
	 * Calculate bill.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void calculateBill() throws SQLException, ClassNotFoundException {
		// calculate bill when order is opened and after each placed order
		String totalBill = Integer.toString(TableDAO.calculateBill());
		totalBillText.setText(totalBill);
	}

	/**
	 * Sets the table number text.
	 *
	 * @param tableNumber the new table number text
	 */
	public void setTableNumberText (String tableNumber) {
		// Set text on the order.fxml
		tableNumberText.setText(tableNumber);
	}

	/**
	 * Sets the order id text.
	 *
	 * @param orderId the new order id text
	 */
	public void setOrderIdText (String orderId) {
		// Set text on the order.fxml
		orderIdText.setText(orderId);
	}

	/**
	 * Save order in orders table
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void saveOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// step 1: access the order ID on order.fxml
			// step 2: check if orderId exists in order List
			// step 3: if exists: call updateOrder
			// step 4: if not: call placeOrder

			// step 1: access the order ID on order.fxml
			int orderId = Integer.parseInt(orderIdText.getText());
			// step 2: check if orderId exists in order List
			// Access observablelist of orders table
			ObservableList<Order> orderList = OrderDAO.searchOrders();
			// Check if orderList is empty. This will be the case in very first start of program (cant loop through an empty orderList)
			if(orderList.isEmpty()) {
				// Activity
				EmployeeController.openActivity(Integer.valueOf(orderIdText.getText()), Integer.valueOf(tableNumberText.getText()), Integer.valueOf(userId.getText()));
				placeOrder();
			}
			else {
				boolean updateOrder = false;
				// loop through observable list
				for(Order i : orderList) {
					if(i.getOrderId() == orderId) {
						updateOrder = true;
					}
				}
				if(updateOrder) {
					updateOrder();
				}
				else {
					// Activity
					EmployeeController.openActivity(Integer.valueOf(orderIdText.getText()), Integer.valueOf(tableNumberText.getText()), Integer.valueOf(userId.getText()));
					placeOrder();
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Place order.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void placeOrder() throws SQLException, ClassNotFoundException {
		OrderDAO.placeOrder(Integer.parseInt(tableNumberText.getText()), Integer.parseInt(totalBillText.getText()), specialRequestPrompt.getText()); 
	}

	/**
	 * Update existing order in orders table
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void updateOrder() throws SQLException, ClassNotFoundException {
		OrderDAO.changeOrder(Integer.parseInt(orderIdText.getText()), Integer.parseInt(totalBillText.getText()), specialRequestPrompt.getText());
	}

	/**
	 * Update special request in orders table
	 */
	private void updateSpecialRequest() throws SQLException, ClassNotFoundException {
		if (checkBill() != 0) {
			updateOrder();
		}
	}

	/**
	 * Close order.
	 * Sets table status to 0 and switches to employee view
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void closeOrder (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TableStatusDAO.updateOrderId(0, Integer.parseInt(tableNumberText.getText()));
			// Activity
			EmployeeController.closeActivity(Integer.valueOf(orderIdText.getText()), Integer.valueOf(tableNumberText.getText()), Integer.valueOf(userId.getText()));

			try {
				((Node) actionEvent.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/waiter/EmployeeView.fxml").openStream());
				Scene scene = new Scene(root);

				EmployeeController employeeController = (EmployeeController) loader.getController();
				employeeController.SetWaiter(userLbl.getText(), Integer.valueOf(userId.getText()));

				scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Populate category drop down.
	 */
	private void populateCategoryDropDown() {
		// Source: http://www.java2s.com/Tutorials/Java/JavaFX/0590__JavaFX_ComboBox.htm
		ObservableList<Products> productsData;
		try {
			// Get all Products
			productsData = ProductsDAO.searchProducts();
			// Loop through OL<Products> list and put category names in OL<String>
			ObservableList<String> productsDataString = FXCollections.observableArrayList();
			for(Products i : productsData) {
				String category = i.getProductCategory();

				boolean addCategory = true;

				// Loop through productsDataString to check if that category already exists
				for(String uniqueCategory : productsDataString) {
					if (uniqueCategory.equalsIgnoreCase(category)) {
						addCategory = false;
					}
				}
				// If none of the existing categories equals the new category --> add
				if(addCategory) {
					productsDataString.add(category);
				}
			}
			// Populate Employees on TableView
			productCategoryBox.setItems(productsDataString);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		productCategoryBox.setValue("Select category: ");
	}

	/**
	 * Select product category.
	 *
	 * @param e the e
	 */
	@FXML
	private void selectProductCategory(ActionEvent e) {
		// Access selected product category
		String selectedProductCategory = productCategoryBox.getValue();
		// Call to populate second box
		populateProductNameBox(selectedProductCategory);
	}

	/**
	 * Populate product name box.
	 *
	 * @param productCategory the product category
	 */
	private void populateProductNameBox(String productCategory) {
		ObservableList<Products> productsData;
		try {
			productsData = ProductsDAO.searchProductsBasedOnCategory(productCategory);

			// Loop through OL<Products> list and put product names in OL<String>
			ObservableList<String> productsDataString = FXCollections.observableArrayList();

			for(Products i : productsData) {
				productsDataString.add(i.getProductName());
			}
			// Populate Employees on TableView
			productNameBox.setItems(productsDataString);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Place sub order.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void placeSubOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		// Only if a selection has been made
		if(!productNameBox.getSelectionModel().isEmpty()) {
			try {
				// Get selected product, store corresponding instance of a Products instance
				Products product = ProductsDAO.searchSubOrder(productNameBox.getValue());
				// Call Table DAO to place suborder
				TableDAO.placeSubOrder(product.getProductName(), product.getProductCategory(), product.getProductPrice(), commentPromptBox.getText());
				// Update bill
				calculateBill();
				// Update table
				searchOrders();
				// Clear comment box
				emptyCommentPromptBox();
				// save order
				saveOrder(actionEvent);
				// Update status and order
				updateStatusAndOrders();
			} catch (SQLException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}

	/**
	 * Empty Edit-TextFields comment prompt box.
	 */
	private void emptyCommentPromptBox() {
		commentPromptBox.clear();
	}

	/**
	 * Update sub order.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void updateSubOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// update changes using selected suborder id and edited comment
			TableDAO.changeSubOrder(subOrdersTable.getSelectionModel().getSelectedItem().getSubOrderId(), editCommentText.getText()); 
			// Update table
			searchOrders();
			// Clear the comment edit box
			emptyEditCommentBox();
			// save order
			saveOrder(actionEvent);
			// Update status and order
			updateStatusAndOrders();
		} catch (SQLException e) {
		}
	}

	/**
	 * Empty edit comment box.
	 */
	private void emptyEditCommentBox() {
		editCommentText.clear();
	}

	// ROW SELECTION
	/**
	 * Determines whether or not a row is selected based on which further method is called
	 */
	private void isRowSelectedDetector() {
		// Source: http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
		subOrdersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				try {
					// If a row is selected --> display the content in Edit-TextFields
					displaySelection();
					// activate
					activateBtns();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			} else {
				// If no row selected --> empty the Edit-TextFields
				// emptyBoxes();
				// deactivate
				deactivateBtns();
			}
		});
	}
	
	private void activateBtns() {
		updateBtn.setDisable(false);
		deleteOrdBtn.setDisable(false);
	}
	
	private void deactivateBtns() {
		updateBtn.setDisable(true);
		deleteOrdBtn.setDisable(true);
	}

	/**
	 * Display the content in Edit-TextFields
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void displaySelection() throws SQLException, ClassNotFoundException {
		try {
			// Access the elements from selected row
			Table selectedTable = subOrdersTable.getSelectionModel().getSelectedItem();
			editCommentText.setText(selectedTable.getComments());
		} catch (Exception e) {
		}
	}

	// DELETE SUBORDER
	/**
	 * Delete sub order.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void deleteSubOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Confirmation");
		alert.setContentText("Please confirm deletion");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			// ... user chose OK
			try {
				ObservableList<Table> deleteSubOrders = subOrdersTable.getSelectionModel().getSelectedItems();
				for (Table selecteSubdOrder : deleteSubOrders) {
					// delete
					TableDAO.deleteSubOrderWithSubId(selecteSubdOrder.getSubOrderId());
					// Update bill
					calculateBill();
				}
				// Update table
				searchOrders();
				// save order
				saveOrder(actionEvent);
				// Update status and order
				updateStatusAndOrders();
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	/**
	 * Delete order.
	 *
	 * @param actionEvent the action event
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// DELETE ENTIRE ORDER
	@FXML
	private void deleteOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Confirmation");
		alert.setContentText("Please confirm deletion");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				// Reset table status
				TableStatusDAO.updateOrderId(0, Integer.parseInt(tableNumberText.getText()));
				// Delete Order
				OrderDAO.deleteOrderWithId(Integer.parseInt(orderIdText.getText()));
				// Delete corresponding suborders
				TableDAO.deleteSubOrderWithOrderId(Integer.parseInt(orderIdText.getText()));
				// Activity
				EmployeeController.deleteUnclosedActivity(Integer.valueOf(orderIdText.getText()), Integer.valueOf(tableNumberText.getText()), Integer.valueOf(userId.getText()));
				// exit window
				backToEmployee(actionEvent);	
			} catch (ClassNotFoundException | SQLException e) {
				throw e;
			}
		}
	}

	// BILL
	/**
	 * Check bill.
	 *
	 * @return the int
	 */
	private int checkBill() {
		return Integer.parseInt(totalBillText.getText());
	}

	/**
	 * Update status and orders.
	 * This function must be called each time so that order is correctly saved even when user exits program or computer is turned off
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void updateStatusAndOrders() throws SQLException, ClassNotFoundException {
		if(checkBill() == 0) {
			try {
				// If the table was previously opened and had suborders and then the waiter opens the table again and deletes all suborders --> the table status is set back to 0
				TableStatusDAO.updateOrderId(0, Integer.parseInt(tableNumberText.getText()));
				// If no suborders are contained --> delete the order
				OrderDAO.deleteOrderWithId(Integer.parseInt(orderIdText.getText()));
			} catch (ClassNotFoundException | SQLException e) {
				throw e;
			}
		} else {
			try {
				TableStatusDAO.updateOrderId(Integer.parseInt(orderIdText.getText()), Integer.parseInt(tableNumberText.getText()));
			} catch (ClassNotFoundException | SQLException e) {
				throw e;
			}
		}
	}

	// SPECIAL REQ
	/**
	 * Show special request.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void showSpecialRequest() throws SQLException, ClassNotFoundException {

		TableStatus tableStatus = TableStatusDAO.searchTableStatus(TableDAO.getTableNumber());
		// Only if this is not a new order - get special request
		System.out.println("1");
		if(tableStatus.getOrderId() > 0) {
			System.out.println("2");
			// Access the corresponding order object
			Order order = OrderDAO.searchTable(TableDAO.getOrderId());
			System.out.println("3");
			// Access the object's special request
			String specialRequest = order.getSpecialRequests();
			// Set text to special request
			specialRequestPrompt.setText(specialRequest);	
		}
	}

	/**
	 * Sets the waiter label
	 *
	 * @param user the user
	 * @param employeeId the employee id
	 */
	public void SetWaiter(String user, int employeeId) {
		userLbl.setText(user);
		userId.setText(Integer.toString(employeeId));
	}
}
