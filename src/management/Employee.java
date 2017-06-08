package management;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *Class holding properties of Employee
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */
public class Employee {
    
    // Instance variables
    private IntegerProperty empId;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty mail;
    private IntegerProperty nr;
    private StringProperty loginUsername;
    private StringProperty loginPassword;

    /**
     * Instantiates a new employee.
     */
    public Employee() {
        this.empId = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.mail = new SimpleStringProperty();
        this.nr = new SimpleIntegerProperty();
        this.loginUsername = new SimpleStringProperty();
        this.loginPassword = new SimpleStringProperty();
    
    }

    /**
     * Gets the employee id.
     *
     * @return the employee id
     */
    public int getEmployeeId() {
        return empId.get();
    }

    /**
     * Sets the employee id.
     *
     * @param employeeId the new employee id
     */
    public void setEmployeeId(int employeeId){
        this.empId.set(employeeId);
    }

    /**
     * Employee id property.
     *
     * @return the integer property
     */
    public IntegerProperty employeeIdProperty(){
        return empId;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName () {
        return firstName.get();
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName){
        this.firstName.set(firstName);
    }

    /**
     * First name property.
     *
     * @return the string property
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName () {
        return lastName.get();
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName){
        this.lastName.set(lastName);
    }

    /**
     * Last name property.
     *
     * @return the string property
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Gets the mail.
     *
     * @return the mail
     */
    public String getmail () {
        return mail.get();
    }

    /**
     * Sets the mail.
     *
     * @param mail the new mail
     */
    public void setmail (String mail){
        this.mail.set(mail);
    }

    /**
     * mail property.
     *
     * @return the string property
     */
    public StringProperty mailProperty() {
        return mail;
    }

  /**
   * Gets the phone number.
   *
   * @return the phone number
   */
  public Integer getPhoneNumber () {
      return nr.get();
  }

  /**
   * Sets the phone number.
   *
   * @param phoneNumber the new phone number
   */
  public void setPhoneNumber (Integer phoneNumber){
      this.nr.set(phoneNumber);
  }

  /**
   * Phone number property.
   *
   * @return the integer property
   */
  public IntegerProperty phoneNumberProperty() {
      return nr;
  }
    
    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getloginUsername () {
        return loginUsername.get();
    }

    /**
     * Sets the user name.
     *
     * @param loginUsername the new user name
     */
    public void setloginUsername (String loginUsername){
        this.loginUsername.set(loginUsername);
    }

    /**
     * User name property.
     *
     * @return the string property
     */
    public StringProperty loginUsernameProperty() {
        return loginUsername;
    }

    /**
     * Gets the pass word.
     *
     * @return the pass word
     */
    public Object getloginPassword(){
        return loginPassword.get();
    }

    /**
     * Sets the pass word.
     *
     * @param loginPassword the new pass word
     */
    public void setloginPassword(String loginPassword){
        this.loginPassword.set(loginPassword);
    }

    /**
     * Pass word property.
     *
     * @return the string property
     */
    public StringProperty loginPasswordProperty() {
        return loginPassword;
    }
}
