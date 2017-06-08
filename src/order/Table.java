package order;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *Class holding properties of Table
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */
public class Table {
	// Instance variables
	private IntegerProperty subOrderId;
	private IntegerProperty productId;
	private StringProperty productName;
	private StringProperty productCategory;
	private IntegerProperty productPrice;
	private IntegerProperty orderId;
	private IntegerProperty tableNumber;
	private StringProperty comments;

	/**
	 * Instantiates a new table.
	 */
	public Table() {
		this.subOrderId = new SimpleIntegerProperty();
		this.productId = new SimpleIntegerProperty();
		this.productName = new SimpleStringProperty();
		this.productCategory = new SimpleStringProperty();
		this.productPrice = new SimpleIntegerProperty();
		this.orderId = new SimpleIntegerProperty();
		this.tableNumber = new SimpleIntegerProperty();
		this.comments = new SimpleStringProperty();
	}

	/**
	 * Gets the sub order id.
	 *
	 * @return the sub order id
	 */
	public int getSubOrderId() {
		return subOrderId.get();
	}

	/**
	 * Sets the sub order id.
	 *
	 * @param subOrderId the new sub order id
	 */
	public void setSubOrderId(int subOrderId) {
		this.subOrderId.set(subOrderId);
	}

	/**
	 * Sub order id property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty sUbOrderIdProperty() {
		return subOrderId;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public int getProductId() {
		return productId.get();
	}

	/**
	 * Sets the product id.
	 *
	 * @param productId the new product id
	 */
	public void setProductId(int productId) {
		this.productId.set(productId);
	}

	/**
	 * Product id property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty productIdProperty() {
		return productId;
	}	

	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return productName.get();
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the new product name
	 */
	public void setProductName(String productName) {
		this.productName.set(productName);
	}

	/**
	 * Product name property.
	 *
	 * @return the string property
	 */
	public StringProperty productNameProperty() {
		return productName;
	}	

	/**
	 * Gets the product category.
	 *
	 * @return the product category
	 */
	public String getProductCategory() {
		return productCategory.get();
	}

	/**
	 * Sets the product category.
	 *
	 * @param productCategory the new product category
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory.set(productCategory);
	}

	/**
	 * Product category property.
	 *
	 * @return the string property
	 */
	public StringProperty productCategoryProperty() {
		return productCategory;
	}	

	/**
	 * Gets the product price.
	 *
	 * @return the product price
	 */
	public int getProductPrice() {
		return productPrice.get();
	}

	/**
	 * Sets the product price.
	 *
	 * @param productPrice the new product price
	 */
	public void setProductPrice(int productPrice) {
		this.productPrice.set(productPrice);
	}

	/**
	 * Product price property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty productPriceProperty() {
		return productPrice;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	// orderId
	public int getOrderId() {
		return orderId.get();
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setOrderId(int orderId) {
		this.orderId.set(orderId);
	}

	/**
	 * Order id property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty orderIdProperty() {
		return orderId;
	}

	/**
	 * Gets the table number.
	 *
	 * @return the table number
	 */
	public int getTableNumber() {
		return tableNumber.get();
	}

	/**
	 * Sets the table number.
	 *
	 * @param tableNumber the new table number
	 */
	public void setTableNumber(int tableNumber) {
		this.tableNumber.set(tableNumber);
	}

	/**
	 * Table number property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty tableNumberProperty() {
		return tableNumber;
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public String getComments() {
		return comments.get();
	}

	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(String comments) {
		this.comments.set(comments);
	}

	/**
	 * Comments property.
	 *
	 * @return the string property
	 */
	public StringProperty commentsProperty() {
		return comments;
	}
}
