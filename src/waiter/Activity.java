package waiter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *Class holding properties of Activity
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */
public class Activity {
	// Instance variables
	private IntegerProperty activityId;
	private IntegerProperty employeeId;
	private StringProperty dateAndTime;
	private StringProperty activity;

	/**
	 * Instantiates a new activity.
	 */
	public Activity() {
		this.activityId = new SimpleIntegerProperty();
		this.employeeId = new SimpleIntegerProperty();
		this.dateAndTime = new SimpleStringProperty();
		this.activity = new SimpleStringProperty();
	}

	/**
	 * Gets the activity id.
	 *
	 * @return the activity id
	 */
	public int getActivityId() {
		return activityId.get();
	}

	/**
	 * Sets the activity id.
	 *
	 * @param activityId the new activity id
	 */
	public void setActivityId(int activityId) {
		this.activityId.set(activityId);
	}

	/**
	 * Activity id property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty activityIdProperty() {
		return activityId;
	}

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public int getEmployeeId() {
		return employeeId.get();
	}

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId.set(employeeId);
	}

	/**
	 * Employee id property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty employeeIdProperty() {
		return employeeId;
	}

	/**
	 * Gets the date and time.
	 *
	 * @return the date and time
	 */
	public String getDateAndTime() {
		return dateAndTime.get();
	}

	/**
	 * Sets the date and time.
	 *
	 * @param dateAndTime the new date and time
	 */
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime.set(dateAndTime);
	}

	/**
	 * Date and time property.
	 *
	 * @return the string property
	 */
	public StringProperty dateAndTimeProperty() {
		return dateAndTime;
	}

	/**
	 * Gets the activity.
	 *
	 * @return the activity
	 */
	public String getActivity() {
		return activity.get();
	}

	/**
	 * Sets the activity.
	 *
	 * @param activity the new activity
	 */
	public void setActivity(String activity) {
		this.activity.set(activity);
	}

	/**
	 * Activity property.
	 *
	 * @return the string property
	 */
	public StringProperty activityProperty() {
		return activity;
	}
}
