package waiter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *Class holding properties of TableStatus
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */
public class TableStatus {
	// Instance variables
	private IntegerProperty table_number;
	private IntegerProperty order_id;
	
	/**
	 * Instantiates a new table status.
	 */
	public TableStatus() {
		this.table_number = new SimpleIntegerProperty();
		this.order_id = new SimpleIntegerProperty();
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
}
