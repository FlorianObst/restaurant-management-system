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
public class ActivityDAO {

	//____________________________________________________________________________________________________
	// insert
	//____________________________________________________________________________________________________
	/**
	 * Insert activity.
	 *
	 * @param employeeId the employee id
	 * @param activity the activity
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// *************************************
	public static void insertActivity(int employeeId, String activity) throws SQLException, ClassNotFoundException {
		String updateStmt = "INSERT INTO activity (EMPLOYEE_ID, DATE, ACTIVITY)" +"VALUES ("+ employeeId + ",CURRENT_TIMESTAMP,'" + activity + "' )";

		// Execute
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}
	}


	//____________________________________________________________________________________________________
	// Search
	//____________________________________________________________________________________________________
	/**
	 * Search activity.
	 *
	 * @param employeeId the employee id
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<Activity> searchActivity(int employeeId) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM activity WHERE EMPLOYEE_ID=" + employeeId;
		// Execute
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			ObservableList<Activity> activityList = getActivityList(rs);

			// Return object
			return activityList;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Gets the activity list.
	 *
	 * @param rs the rs
	 * @return the activity list
	 * @throws SQLException the SQL exception
	 */
	private static ObservableList<Activity> getActivityList(ResultSet rs) throws SQLException {
		ObservableList<Activity> activityList = FXCollections.observableArrayList();		

		while (rs.next()) {
			Activity activity = new Activity();
			activity.setActivityId(rs.getInt("ACTIVITY_ID"));
			activity.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
			activity.setDateAndTime(rs.getString("DATE"));
			activity.setActivity(rs.getString("ACTIVITY"));
			// Add to observable list
			activityList.add(activity);
		}
		return activityList;
	}
}
