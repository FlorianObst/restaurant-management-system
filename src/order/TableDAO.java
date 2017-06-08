package order;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbControl.DbControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *Interacts with Database
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */
public class TableDAO {
	// instance variable, used to set table number and order id in the tables table
	static int tableNumber;
	static int orderId;

	// Setter
	/**
	 * Sets the table number.
	 *
	 * @param newTableNumber the new table number
	 */
	public static void setTableNumber(int newTableNumber) {
		tableNumber = newTableNumber;
	}

	/**
	 * Sets the order id.
	 *
	 * @param newOrderId the new order id
	 */
	public static void setOrderId(int newOrderId) {
		orderId = newOrderId;
	}

	// Getter
	/**
	 * Gets the table number.
	 *
	 * @return the table number
	 */
	public static int getTableNumber() {
		return tableNumber;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public static int getOrderId() {
		return orderId;
	}

	//____________________________________________________________________________________________________
	// All suborders and orders with orderId
	//____________________________________________________________________________________________________
	/**
	 * Search sub orders.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<Table> searchSubOrders() throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		// Note: uses static variables tableNumber and orderId
		String selectStmt = "SELECT * FROM table1 Where TABLE_NUMBER =" + tableNumber + " AND ORDER_ID =" + orderId;
		// Execute
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsSubOrders = DbControl.dbExecuteQuery(selectStmt);
			ObservableList<Table> tableList = getTableList(rsSubOrders);
			return tableList;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Search sub orders.
	 *
	 * @param orderId the order id
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// Same approach as above however uses a parameter for orderId (MgmtOrderController)
	public static ObservableList<Table> searchSubOrders(int orderId) throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		// Note: uses static variables tableNumber and orderId
		String selectStmt = "SELECT * FROM table1 Where ORDER_ID =" + orderId;
		// Execute
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsSubOrders = DbControl.dbExecuteQuery(selectStmt);
			ObservableList<Table> tableList = getTableList(rsSubOrders);
			return tableList;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Gets the table list.
	 *
	 * @param rs the rs
	 * @return the table list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private static ObservableList<Table> getTableList(ResultSet rs) throws SQLException, ClassNotFoundException {
		// Declare a TL which comprises of Table objects
		ObservableList<Table> tableList = FXCollections.observableArrayList();
		// Create Table objects
		while (rs.next()) {
			Table table = new Table();
			table.setSubOrderId(rs.getInt("SUB_ORDER_ID"));
			table.setProductName(rs.getString("PRODUCT_NAME"));
			table.setProductCategory(rs.getString("PRODUCT_CATEGORY"));
			table.setProductPrice(rs.getInt("PRODUCT_PRICE"));
			table.setComments(rs.getString("COMMENTS"));
			// Add objects to the ObservableList
			tableList.add(table);
		}
		// return OL of Table
		return tableList;
	}

	//____________________________________________________________________________________________________
	// A suborders
	//____________________________________________________________________________________________________
	/**
	 * Search sub order.
	 *
	 * @param subOrderId the sub order id
	 * @return the table
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Table searchSubOrder(String subOrderId) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM table1 WHERE SUB_ORDER_ID=" + subOrderId;
		// Execute
		try {
			ResultSet rsEmp = DbControl.dbExecuteQuery(selectStmt);
			Table table = getOrderFromResultSet(rsEmp);
			// Return employee object
			return table;
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
	private static Table getOrderFromResultSet(ResultSet rs) throws SQLException {
		// Declare instance
		Table table = null;
		// Set variables
		if (rs.next()) {
			table = new Table();
			table.setSubOrderId(rs.getInt("SUB_ORDER_ID"));
			table.setProductId(rs.getInt("PRODUCT_ID"));
			table.setProductName(rs.getString("PRODUCT_NAME"));
			table.setProductCategory(rs.getString("PRODUCT_CATEGORY"));
			table.setProductPrice(rs.getInt("PRODUCT_PRICE"));
		}
		return table;
	}

	//____________________________________________________________________________________________________
	// delete, change, place
	//____________________________________________________________________________________________________
	/**
	 * Delete sub order with sub id.
	 *
	 * @param subOrderId the sub order id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void deleteSubOrderWithSubId(int subOrderId) throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String deleteStmt = "DELETE FROM table1 WHERE SUB_ORDER_ID =" + subOrderId;

		// Execute UPDATE operation
		try {
			DbControl.dbExecuteUpdate(deleteStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Delete sub order with order id.
	 *
	 * @param orderId the order id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// When entire order is deleted from employee controller, then corresponding suborders are deleted too
	public static void deleteSubOrderWithOrderId(int orderId) throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String deleteStmt = "DELETE FROM table1 WHERE ORDER_ID =" + orderId;

		// Execute UPDATE operation
		try {
			DbControl.dbExecuteUpdate(deleteStmt);
		} catch (SQLException e) {
			throw e;
		}
	} 

	/**
	 * Change sub order.
	 *
	 * @param subOrderId the sub order id
	 * @param comment the comment
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void changeSubOrder(int subOrderId, String comment) throws SQLException, ClassNotFoundException {
		String updateStmt = "UPDATE table1 SET COMMENTS = '" + comment + "'where SUB_ORDER_ID = '" + subOrderId + "'";
		// Execute
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Place sub order.
	 *
	 * @param productName the product name
	 * @param productCategory the product category
	 * @param productPrice the product price
	 * @param comments the comments
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void placeSubOrder(String productName, String productCategory, int productPrice, String comments) throws SQLException, ClassNotFoundException {
		String updateStmt = "INSERT INTO table1 (PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_PRICE, ORDER_ID, TABLE_NUMBER, COMMENTS)"
				+ "VALUES('" + productName + "','" + productCategory + "','" + productPrice + "','"	+ orderId + "','" + tableNumber + "','" + comments + "')";
		// Execute
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

	//____________________________________________________________________________________________________
	// Calculate bill
	//____________________________________________________________________________________________________

	/**
	 * Calculate bill.
	 *
	 * @return the int
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// Calculate bill for restaurant tables
	public static int calculateBill() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT SUM(PRODUCT_PRICE) AS totalBill FROM table1 WHERE TABLE_NUMBER=" + tableNumber + " AND ORDER_ID= " + orderId;
		// Execute
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			int totalBill = 0;
			if (rs.next()) {
				totalBill = rs.getInt("totalBill");
			}
			return totalBill;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Calculate import bill.
	 *
	 * @param orderId the order id
	 * @return the int
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// Calculate bill for imported orders
	public static int calculateImportBill(int orderId) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT SUM(PRODUCT_PRICE) AS totalBill FROM table1 WHERE ORDER_ID= " + orderId;
		// Execute
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			int totalBill = 0;
			if (rs.next()) {
				totalBill = rs.getInt("totalBill");
			}
			return totalBill;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}
}
