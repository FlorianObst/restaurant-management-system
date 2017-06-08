package waiter;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dbControl.DbControl;

/**
 *Interacts with Database
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */
public class OrderDAO {

	//____________________________________________________________________________________________________
	// SELECT
	//____________________________________________________________________________________________________

	/**
	 * Search table.
	 *
	 * @param orderId the order id
	 * @return the order
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Order searchTable(int orderId) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM orders WHERE ORDER_ID=" + orderId;

		// Execute
		try {
			ResultSet rsEmp = DbControl.dbExecuteQuery(selectStmt);
			Order order = getOrderFromResultSet(rsEmp);

			// Return order object
			return order;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Gets the order from result set.
	 *
	 * @param rs the rs
	 * @return the order from result set
	 * @throws SQLException the SQL exception
	 */
	private static Order getOrderFromResultSet(ResultSet rs) throws SQLException {
		Order ord = null;
		if (rs.next()) {
			ord = new Order();
			ord.setOrderId(rs.getInt("ORDER_ID"));
			ord.setProductName(rs.getString("PRODUCT_NAME"));
			ord.setTableNumber(rs.getInt("TABLE_NUMBER"));
			ord.setOrderTime(rs.getString("ORDER_TIME"));
			ord.setListOfOrders(rs.getString("LIST_OF_ORDERS"));
			ord.setTotalBill(rs.getInt("TOTAL_BILL"));
			ord.setSpecialRequests(rs.getString("SPECIAL_REQUESTS"));
			ord.setComments(rs.getString("COMMENTS"));
			if(ord.getTableNumber() == 0) {
				ord.setTableNumberStatus("Pending Delivery");
			} else if(ord.getTableNumber() == -1) {
				ord.setTableNumberStatus("Closed Delivery");
			} else {
				ord.setTableNumberStatus(Integer.toString(ord.getTableNumber()));
			}
		}
		return ord;
	}

	/**
	 * Search orders.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<Order> searchOrders() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM orders";
		// Execute
		try {
			ResultSet rsOrders = DbControl.dbExecuteQuery(selectStmt);
			ObservableList<Order> orderList = getOrderList(rsOrders);

			// Return order object
			return orderList;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Search orders by table no.
	 *
	 * @param choice the choice
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<Order> searchOrdersByTableNo(int choice) throws SQLException, ClassNotFoundException {
		// if choice == 1 --> only tables. if choice == 2 --> only takeaway
		String selectStmt = null;
		if (choice == 1) {
			selectStmt = "SELECT * FROM orders Where TABLE_NUMBER > 0";
		} else if (choice == 2) {
			selectStmt = "SELECT * FROM orders Where TABLE_NUMBER <= 0";
		}

		// Execute
		try {
			ResultSet rsOrders = DbControl.dbExecuteQuery(selectStmt);
			ObservableList<Order> orderList = getOrderList(rsOrders);
			// Return order object
			return orderList;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Gets the order list.
	 *
	 * @param rs the rs
	 * @return the order list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private static ObservableList<Order> getOrderList(ResultSet rs) throws SQLException, ClassNotFoundException {
		ObservableList<Order> orderList = FXCollections.observableArrayList();

		while (rs.next()) {
			Order ord = new Order();
			ord.setOrderId(rs.getInt("ORDER_ID"));
			ord.setProductName(rs.getString("PRODUCT_NAME"));
			ord.setTableNumber(rs.getInt("TABLE_NUMBER"));
			ord.setOrderTime(rs.getString("ORDER_TIME"));
			ord.setListOfOrders(rs.getString("LIST_OF_ORDERS"));
			ord.setTotalBill(rs.getInt("TOTAL_BILL"));
			ord.setSpecialRequests(rs.getString("SPECIAL_REQUESTS"));
			ord.setComments(rs.getString("COMMENTS"));
			// Need to show in table if its an open delivery (tableNumber == 0), a closed delivery (tn == -1) or a table (tn > 0)
			// Store that status in a new StringProperty
			if(ord.getTableNumber() == 0) {
				ord.setTableNumberStatus("Pending Delivery");
			} else if(ord.getTableNumber() == -1) {
				ord.setTableNumberStatus("Closed Delivery");
			} else {
				ord.setTableNumberStatus(Integer.toString(ord.getTableNumber()));
			}
			// Add order to the ObservableList
			orderList.add(ord);
		}
		// return empList (ObservableList of orders)
		return orderList;
	}
	
	/**
	 * Search time.
	 *
	 * @param start the start
	 * @param end the end
	 * @param whichOrders the which orders
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<Order> searchTime(String start,String end, int whichOrders) throws SQLException, ClassNotFoundException {
		// whichOrders: 1 --> Orders; 2 --> Takeaway; 3 --> All
		String selectStmt; 
		if(whichOrders == 1) {
			// Declare a SELECT statement Order
			selectStmt = "SELECT * FROM orders Where ORDER_TIME BETWEEN '"+start+"'and'"+end+"' AND ORDER_ID > 0";
		} else if(whichOrders == 2){
			// Declare a SELECT statement Takeaway
			selectStmt = "SELECT * FROM orders Where ORDER_TIME BETWEEN '"+start+"'and'"+end+"' AND ORDER_ID < 0";
		} else {
			// Declare a SELECT statement Takeaway
			selectStmt = "SELECT * FROM orders Where ORDER_TIME BETWEEN '"+start+"'and'"+end+"'";
		}

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsOrders = DbControl.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getorderList method and get order
			// object
			ObservableList<Order> orderList = getOrderList(rsOrders);

			// Return order object
			return orderList;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	//____________________________________________________________________________________________________
	// Delete insert update
	//____________________________________________________________________________________________________

	/**
	 * Delete order with id.
	 *
	 * @param orderId the order id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void deleteOrderWithId(int orderId) throws SQLException, ClassNotFoundException {
		String updateStmt = "DELETE FROM orders WHERE ORDER_ID =" + orderId;
		// Execute
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Place order.
	 *
	 * @param tableNumber the table number
	 * @param totalBill the total bill
	 * @param specialRequests the special requests
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void placeOrder(int tableNumber, int totalBill, String specialRequests)
			throws SQLException, ClassNotFoundException {
		// Note: OrderId created auto
		String updateStmt = "INSERT INTO orders (TABLE_NUMBER, ORDER_TIME, TOTAL_BILL, SPECIAL_REQUESTS)" +"VALUES("
				+ tableNumber + ",CURRENT_TIMESTAMP," + totalBill + ",'" + specialRequests+ "')";
		// time('now')
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Change order.
	 *
	 * @param orderId the order id
	 * @param totalBill the total bill
	 * @param specialRequests the special requests
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void changeOrder(int orderId, int totalBill, String specialRequests ) throws SQLException, ClassNotFoundException {
		String updateStmt = "UPDATE orders SET ORDER_TIME = CURRENT_TIMESTAMP, TOTAL_BILL ='"+ totalBill+"', SPECIAL_REQUESTS ='"+specialRequests+"' where ORDER_ID = " + orderId +"";
		// Execute
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Close takeaway.
	 *
	 * @param orderId the order id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// -1 indicated order has been closed
	public static void closeTakeaway(int orderId) throws SQLException, ClassNotFoundException {
		// Declare a UPDATE statement
		String updateStmt = "UPDATE orders SET TABLE_NUMBER = -1 where ORDER_ID = " + orderId +"";
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}
	}
}