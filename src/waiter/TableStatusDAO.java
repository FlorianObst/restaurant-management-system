package waiter;

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
public class TableStatusDAO {

	//____________________________________________________________________________________________________
	// Search
	//____________________________________________________________________________________________________
	/**
	 * Search table status.
	 *
	 * @param tableNumber the table number
	 * @return the table status
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static TableStatus searchTableStatus (int tableNumber) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM table_status WHERE TABLE_NUMBER=" + tableNumber;
		// Execute
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			TableStatus tableStatus = getTableStatusFromResultSet(rs);
			return tableStatus;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Gets the table status from result set.
	 *
	 * @param rs the rs
	 * @return the table status from result set
	 * @throws SQLException the SQL exception
	 */
	private static TableStatus getTableStatusFromResultSet(ResultSet rs) throws SQLException {
		TableStatus tableStatus = null;
		if (rs.next()) {
			tableStatus = new TableStatus();
			tableStatus.setTableNumber(rs.getInt("TABLE_NUMBER"));
			tableStatus.setOrderId(rs.getInt("ORDER_ID"));
		}
		return tableStatus;
	}

	/**
	 * Search table status based on status.
	 *
	 * @param orderId the order id
	 * @return the table status
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static TableStatus searchTableStatusBasedOnStatus (int orderId) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM table_status WHERE ORDER_ID=" + orderId;
		// Execute
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			TableStatus tableStatus = getTableStatusFromResultSet(rs);
			// Return table status
			return tableStatus;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}	

	/**
	 * Search table statuses.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<TableStatus> searchTableStatuses() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM table_status";
		// Execute
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			ObservableList<TableStatus> tableStatusList = getTableStatusList(rs);
			return tableStatusList;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Gets the table status list.
	 *
	 * @param rs the rs
	 * @return the table status list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private static ObservableList<TableStatus> getTableStatusList(ResultSet rs) throws SQLException, ClassNotFoundException {
		// Declare a observable List which comprises of Table objects
		ObservableList<TableStatus> tableStatusList = FXCollections.observableArrayList();
		// Create Table objects
		while (rs.next()) {
			TableStatus tableStatus = new TableStatus();
			tableStatus.setTableNumber(rs.getInt("TABLE_NUMBER"));
			tableStatus.setOrderId(rs.getInt("ORDER_ID"));
			// Add objects to the ObservableList
			tableStatusList.add(tableStatus);
		}
		// return observable List of Table
		return tableStatusList;
	}

	//____________________________________________________________________________________________________
	// Update 
	//____________________________________________________________________________________________________

	/**
	 * Update order id.
	 *
	 * @param orderId the order id
	 * @param tableNumber the table number
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void updateOrderId (int orderId, int tableNumber) throws SQLException, ClassNotFoundException {
		String updateStmt = "UPDATE table_status SET ORDER_ID = '" + orderId + "' where TABLE_NUMBER = '" + tableNumber +"'";
		// Execute
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

}
