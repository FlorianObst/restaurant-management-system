package products;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *Class holding properties of Products
 *<p>
 *Sources: http://www.swtestacademy.com/database-operations-javafx/
 *
 *@author Florian Obst
 *@version Final
 */

public class Products {
	// Instance variables (columns of table)
	private IntegerProperty productId;
	private StringProperty productCategory;
	private StringProperty productName;
	private IntegerProperty productPrice;
	/**
	 * Instantiates a new products.
	 */
	public Products() {
		this.productId = new SimpleIntegerProperty();
		this.productName = new SimpleStringProperty();
		this.productCategory = new SimpleStringProperty();
		this.productPrice = new SimpleIntegerProperty();
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
}
