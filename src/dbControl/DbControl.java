package dbControl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.rowset.CachedRowSetImpl;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import order.TableDAO;
import waiter.EmployeeController;


/**
 *Handles the database connection
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */
public class DbControl {
	//____________________________________________________________________________________________________
	// SQL EXECUTION
	//____________________________________________________________________________________________________
	
	// Static variables
	// Driver
	private static final String DRIVER = "org.sqlite.JDBC";
	// Establish connection
	public static Connection conn = null;
	// String to hold the link to connection
	private static final String connStr = "jdbc:sqlite:Employee.sqlite";

	/**
	 * Establishes connection to database
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void dbConnect() throws SQLException, ClassNotFoundException {
		Class.forName(DRIVER);
		// Established connection to database
		conn = DriverManager.getConnection(connStr);
		conn.setAutoCommit(false);
	}

	/**
	 * Checks if is db connected.
	 *
	 * @return true, if connected
	 */
	public static boolean dbConnectionSuccess() {
		boolean connected = false;

		try {
			connected = !conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connected;
	}

	/**
	 * Executes the query
	 *
	 * @param stmnt the query stmt
	 * @return the result set
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ResultSet dbExecuteQuery(String query) throws SQLException, ClassNotFoundException {
		// Local var
		CachedRowSetImpl crsi = null;
		Statement stmt = null;
		ResultSet rs = null;
		// Establish connection
		dbConnect();
		// System.out.println("Select statement: " + query + "\n");

		// Save the statement
		stmt = conn.createStatement();

		// Create RS and CRS
		rs = stmt.executeQuery(query);
		crsi = new CachedRowSetImpl();
		crsi.populate(rs);
		if (rs != null) {
			// Close rs
			rs.close();
		}
		if (stmt != null) {
			// Close Statement
			stmt.close();
		}
		// disconnect
		dbCloseConnection();
		// Return CRS
		return crsi;
	}

	/**
	 * Db execute update.
	 *
	 * @param sqlStmt the sql stmt
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	//DB Execute Update (For Update/Insert/Delete) Operation
	public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
		// Local var
		Statement stmt = null;
		// Connection
		dbConnect();
		// stmnt
		stmt = conn.createStatement();
		// Execute
		stmt.executeUpdate(sqlStmt);
		conn.commit();
		if (stmt != null) {
			//Close statement
			stmt.close();
			// disconnect
			dbCloseConnection();
		}
	}
	
	/**
	 * Close connection
	 *
	 * @throws SQLException the SQL exception
	 */
	public static void dbCloseConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	//____________________________________________________________________________________________________
	// IMPORT
	//____________________________________________________________________________________________________
	
	/**
	 * Db import.
	 * Source: https://www.youtube.com/watch?v=ugka_yKT-k4
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	// TODO Remove email + remove try catch?
	public static void dbImport() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		conn = DriverManager.getConnection(connStr);

		// Open Filechoose which ensures CSV
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
		// Keep this try/ catch --> if you close the import without selecting --> no error thrown
		try {
			String path = fileChooser.showOpenDialog(null).getAbsolutePath();
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			PreparedStatement pS;
			// While there is something to import
			while ((line = br.readLine()) != null) {
				// Split at comma
				String[] element = line.split(",");
				// Build statement
				String stmnt = "Insert into employees (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD) values ('"
						+ element[1] + "','" + element[2] + "','" + element[3] + "','" + element[4] + "')"; /*EMAIL,   "','" + element[5] + */

				pS = conn.prepareStatement(stmnt);
				pS.executeUpdate(); 
			}
			br.close();
			dbCloseConnection();
		} catch (Exception e) {
			System.out.println("No file selected");
		}
	}

	/**
	 * Db import order.
	 * @throws ClassNotFoundException 
	 */
	public static void dbImportOrder() throws ClassNotFoundException {
		Class.forName(DRIVER);	
		// Open filechoose and ensure CSH
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
		try {
			String path = fileChooser.showOpenDialog(null).getAbsolutePath();
			BufferedReader br = new BufferedReader(new FileReader(path));
			String row;
			PreparedStatement pS;
			// Get maximum orderId
			int orderId = EmployeeController.maxOrderId();
			while ((row = br.readLine()) != null) {
				String[] element = row.split(",");
				if(element[0].charAt(0) != 'T') {
					conn = DriverManager.getConnection(connStr);
					// Insert suborder into table
					// Format: Price, name, category, comment [Note: comment is optional, all others are required]

					// Include empty string if no comment is included
					String comment = (element.length == 3 ? "" : element[3]);
					// stmnt
					String stmnt = "Insert into table1 (PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_PRICE, ORDER_ID, TABLE_NUMBER, COMMENTS) values ('"
							+ element[1] + "','" + element[2] + "','" + element[0] + "'," + orderId + ", 0 ,'" + comment + "')";
					pS = conn.prepareStatement(stmnt);
					pS.executeUpdate();
				} else {
					// Insert order into order
					// Format: "Total: ", special request [Note: special request is optional, "Total: " keyword is required]
					// Get bill
					int totalBill = TableDAO.calculateImportBill(orderId);

					// Include empty string if no special request is included
					String specialRequest = (element.length == 1 ? "" : element[1]);
					// stmnt
					String stmnt = "INSERT INTO orders (TABLE_NUMBER, ORDER_TIME, TOTAL_BILL, SPECIAL_REQUESTS)" 
							+"VALUES(0, CURRENT_TIMESTAMP," + totalBill + ",'" + specialRequest + "')";
					conn = DriverManager.getConnection(connStr);
					pS = conn.prepareStatement(stmnt);
					pS.executeUpdate();
					orderId++;
				}	
			}
			// Close
			br.close();
			dbCloseConnection();
		} catch (Exception e) {
			System.out.println("No file selected");
		}
	}
}


