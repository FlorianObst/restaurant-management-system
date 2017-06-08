package login;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbControl.DbControl;

/**
 * Checks login details
 * <p>
 * Source: https://www.youtube.com/watch?v=IGTL5mvYU54
 *
 * @author Florian Obst
 * @version Final
 */
public class Login {
	/**
	 * Emp login.
	 *queries the database for the relevant login data and writes the business
	 *
	 * @param username the user
	 * @param password the password
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// logic to it
	public boolean empLogin(String username, String password) throws SQLException, ClassNotFoundException {
		ResultSet rs = null;
		// Create stmnt
		String stmnt = "select * from employees where Username = '" + username + "'and Password = '" + password + "'";
		try {
			rs = DbControl.dbExecuteQuery(stmnt);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		} finally {
			rs.close();
		}
	}

	/**
	 * Manager login.
	 *
	 * @param user the user
	 * @param pass the pass
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public boolean managerLogin(String username, String password) throws SQLException, ClassNotFoundException {
		ResultSet rs = null;
		// Create stmnt
		String stmnt = "select * from managers where Username = '" + username + "'and Password = '" + password + "'";
		try {
			rs = DbControl.dbExecuteQuery(stmnt);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		} finally {
			rs.close();
		}
	}
}
