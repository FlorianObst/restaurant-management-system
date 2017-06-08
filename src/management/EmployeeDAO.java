package management;

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
public class EmployeeDAO {
	//____________________________________________________________________________________________________
	// Employees ID
	//____________________________________________________________________________________________________

	/**
	 * Search employee with Id
	 *
	 * @param employeeId
	 * @return the employee
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Employee searchEmployeeWithId(int employeeId) throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		String loginStmt = "select * from employees where EMPLOYEE_ID = " + employeeId;
		// Execute SELECT statement
		// Get rs from dbExecuteQuery 
		ResultSet rs = DbControl.dbExecuteQuery(loginStmt);
		// Use rs for getEmployeeFromResultSet
		Employee employee = getEmployeeFromResultSet(rs);
		// Return
		return employee;
	}

	//____________________________________________________________________________________________________
	// all Employees with UN and PW
	//____________________________________________________________________________________________________

	/**
	 * Search employee.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the employee
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Employee searchEmployee(String username, String password) throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		String loginStmt = "select * from employees where Username = '" + username + "'and Password = '" + password + "'";
		// Execute SELECT statement
		// Get rs from dbExecuteQuery 
		ResultSet rs = DbControl.dbExecuteQuery(loginStmt);
		// Use rs for getEmployeeFromResultSet
		Employee employee = getEmployeeFromResultSet(rs);
		// Return
		return employee;
	}

	/**
	 * Gets the employee from rs
	 *
	 * Use ResultSet from DB as parameter and set Employee Object's attributes
	 *
	 * @param rs the ResultSet
	 * @return the employee from result set
	 * @throws SQLException the SQL exception
	 */
	private static Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException {
		Employee emp = null;
		// While next exists --> add
		if (rs.next()) {
			emp = new Employee();
			emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
			emp.setFirstName(rs.getString("FIRST_NAME"));
			emp.setLastName(rs.getString("LAST_NAME"));
			emp.setmail(rs.getString("EMAIL"));
			emp.setloginUsername(rs.getString("USERNAME"));
			emp.setloginPassword(rs.getString("PASSWORD"));
		}
		return emp;
	}

	//____________________________________________________________________________________________________
	// All EMployees
	//____________________________________________________________________________________________________

	/**
	 * Search employees.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<Employee> searchEmployees() throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		String selectStmt = "SELECT * FROM employees";
		// Execute SELECT statement
		// Get ResultSet from dbExecuteQuery method
		ResultSet rsEmps = DbControl.dbExecuteQuery(selectStmt);
		// oUse rs for getEmployeeList
		ObservableList<Employee> employeeList = getEmpList(rsEmps);
		// Return list
		return employeeList;
	}

	/**
	 * Gets the employee list.
	 *
	 * @param rs the rs
	 * @return the employee list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// Select * from employees operation
	private static ObservableList<Employee> getEmpList(ResultSet rs) throws SQLException, ClassNotFoundException {
		// OL to hold Employee
		ObservableList<Employee> employeeList = FXCollections.observableArrayList();
		while (rs.next()) {
			Employee emp = new Employee();
			emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
			emp.setFirstName(rs.getString("FIRST_NAME"));
			emp.setLastName(rs.getString("LAST_NAME"));
			emp.setmail(rs.getString("EMAIL"));
			emp.setloginUsername(rs.getString("USERNAME"));
			emp.setloginPassword(rs.getString("PASSWORD"));
			// Insert to list
			employeeList.add(emp);
		}
		// Return List
		return employeeList;
	}

	//____________________________________________________________________________________________________
	// Delete, Update, Insert
	//____________________________________________________________________________________________________

	/**
	 * Update employee.
	 *
	 * @param emloyeepId the emloyeep id
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param userName the user name
	 * @param passWord the pass word
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void updateEmployee (int emloyeepId, String firstName, String lastName, String userName, String passWord) throws SQLException, ClassNotFoundException {
		// Declare a UPDATE statement
		String stmnt = "UPDATE employees SET FIRST_NAME = '" + firstName + "', LAST_NAME = '" + lastName + "', USERNAME =  '" + userName + "', PASSWORD = '" + passWord + "' where EMPLOYEE_ID = '" + emloyeepId +"'";
		// Execute UPDATE operation
		DbControl.dbExecuteUpdate(stmnt);

	}

	/**
	 * Delete emp with id.
	 *
	 * @param empId the emp id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void deleteEmpWithId(int empId) throws SQLException, ClassNotFoundException {
		String stmnt = "DELETE FROM employees WHERE EMPLOYEE_ID =" + empId;
		DbControl.dbExecuteUpdate(stmnt);
	}

	/**
	 * Add emp.
	 *
	 * @param name the name
	 * @param lastname the lastname
	 * @param username the username
	 * @param password the password
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void addEmp(String name, String lastname, String username, String password) throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String stmnt = "INSERT INTO employees (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD)" +"VALUES ('" + name + "', '" + lastname + "','" + username + "','" + password + "')";
		DbControl.dbExecuteUpdate(stmnt);
	}
}