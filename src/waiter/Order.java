package waiter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *Class holding properties of Order
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */
public class Order {
	// Declare orders Table Columns
	private IntegerProperty order_id;
	private IntegerProperty table_number;
	private StringProperty order_time;
	private StringProperty list_of_orders;
	private IntegerProperty total_bill;
	private StringProperty special_requests;
	private StringProperty comments;
	private StringProperty product_name;
	// Status
	private StringProperty tableNumberStatus;
	

	/**
	 * Instantiates a new order.
	 */
	public Order() {
		this.order_id = new SimpleIntegerProperty();
		this.table_number = new SimpleIntegerProperty();
		this.order_time = new SimpleStringProperty();
		this.list_of_orders = new SimpleStringProperty();
		this.total_bill = new SimpleIntegerProperty();
		this.special_requests = new SimpleStringProperty();
		this.comments = new SimpleStringProperty();
		this.product_name = new SimpleStringProperty();
		// Status
		this.tableNumberStatus = new SimpleStringProperty();
	}
	
	/**
	 * Gets the table number status.
	 *
	 * @return the table number status
	 */
	// Status
	public String getTableNumberStatus() {
		return tableNumberStatus.get();
	}
	
	/**
	 * Sets the table number status.
	 *
	 * @param status the new table number status
	 */
	public void setTableNumberStatus(String status) {
		this.tableNumberStatus.set(status);
	}
	
	/**
	 * Table number status property.
	 *
	 * @return the string property
	 */
	public StringProperty tableNumberStatusProperty() {
		return tableNumberStatus;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public int getOrderId() {
		return order_id.get();
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setOrderId(int orderId) {
		this.order_id.set(orderId);
	}

	/**
	 * Order id property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty OrderIdProperty() {
		return order_id;
	}

	/**
	 * Gets the table number.
	 *
	 * @return the table number
	 */
	public int getTableNumber() {
		return table_number.get();
	}

	/**
	 * Sets the table number.
	 *
	 * @param tableNumber the new table number
	 */
	public void setTableNumber(int tableNumber) {
		this.table_number.set(tableNumber);
	}

	/**
	 * Table number property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty tableNumberProperty() {
		return table_number;
	}

	/**
	 * Gets the order time.
	 *
	 * @return the order time
	 */
	public String getOrderTime() {
		return order_time.get();
	}

	/**
	 * Sets the order time.
	 *
	 * @param orderTime the new order time
	 */
	public void setOrderTime(String orderTime) {
		this.order_time.set(orderTime);
	}

	/**
	 * Order time property.
	 *
	 * @return the string property
	 */
	public StringProperty orderTimeProperty() {
		return order_time;
	}

	/**
	 * Gets the list of orders.
	 *
	 * @return the list of orders
	 */
	public String getListOfOrders() {
		return list_of_orders.get();
	}

	/**
	 * Sets the list of orders.
	 *
	 * @param listOfOrders the new list of orders
	 */
	public void setListOfOrders(String listOfOrders) {
		this.list_of_orders.set(listOfOrders);
	}

	/**
	 * List of orders.
	 *
	 * @return the string property
	 */
	public StringProperty listOfOrders() {
		return list_of_orders;
	}

	/**
	 * Gets the total bill.
	 *
	 * @return the total bill
	 */
	public int getTotalBill() {
		return total_bill.get();
	}

	/**
	 * Sets the total bill.
	 *
	 * @param totalBill the new total bill
	 */
	public void setTotalBill(int totalBill) {
		this.total_bill.set(totalBill);
	}

	/**
	 * Total bill property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty totalBillProperty() {
		return total_bill;
	}

	/**
	 * Gets the special requests.
	 *
	 * @return the special requests
	 */
	public String getSpecialRequests() {
		return special_requests.get();
	}

	/**
	 * Sets the special requests.
	 *
	 * @param specialRequests the new special requests
	 */
	public void setSpecialRequests(String specialRequests) {
		this.special_requests.set(specialRequests);
	}

	/**
	 * Special requests property.
	 *
	 * @return the string property
	 */
	public StringProperty specialRequestsProperty() {
		return special_requests;
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public Object getComments() {
		return comments.get();
	}

	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(String comments) {
		this.comments.set(comments);
	}

	/**
	 * Comments property.
	 *
	 * @return the string property
	 */
	public StringProperty commentsProperty() {
		return comments;
	}

	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public Object getProductName() {
		return product_name.get();
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the new product name
	 */
	public void setProductName(String productName) {
		this.product_name.set(productName);
	}

	/**
	 * Product name property.
	 *
	 * @return the string property
	 */
	public StringProperty productNameProperty() {
		return product_name;
	}
}
