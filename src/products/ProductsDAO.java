package products;

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
public class ProductsDAO {

	//____________________________________________________________________________________________________
	// All products and products with category
	//____________________________________________________________________________________________________
	/**
	 * Search products.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<Products> searchProducts() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM products";
		// Execute
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			ObservableList<Products> productsList = getProductsList(rs);

			// Return products object
			return productsList;
		} catch (SQLException e) {
			// Return exception
			throw e;
		}
	}

	/**
	 * Search products based on category.
	 *
	 * @param category the category
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ObservableList<Products> searchProductsBasedOnCategory (String category) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM products Where PRODUCT_CATEGORY =(('" + category + "'))";
		// Execute
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			ObservableList<Products> productsList = getProductsList(rs);

			// Return employee object
			return productsList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has been failed: " + e);
			// Return exception
			throw e;
		}
	}

	/**
	 * Gets the products list.
	 *
	 * @param rs the rs
	 * @return the products list
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private static ObservableList<Products> getProductsList(ResultSet rs) throws SQLException, ClassNotFoundException {
		ObservableList<Products> productsList = FXCollections.observableArrayList();
		// Create objects
		while (rs.next()) {
			Products products = new Products();
			products.setProductId(rs.getInt("PRODUCT_ID"));
			products.setProductName(rs.getString("PRODUCT_NAME"));
			products.setProductCategory(rs.getString("PRODUCT_CATEGORY"));
			products.setProductPrice(rs.getInt("PRODUCT_PRICE"));
			// Add objects to the ObservableList
			productsList.add(products);
		}
		// return observable List of Table
		return productsList;
	}


	//____________________________________________________________________________________________________
	// Select product that will be added
	//____________________________________________________________________________________________________

	/**
	 * Search sub order.
	 *
	 * @param productName the product name
	 * @return the products
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Products searchSubOrder(String productName) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM products WHERE PRODUCT_NAME=(('" + productName + "'))";
		// Execute 
		try {
			ResultSet rs = DbControl.dbExecuteQuery(selectStmt);
			Products product = getProduct(rs);

			return product;
		} catch (SQLException e) {
			System.out.println("While searching for product " + productName + ", an error occurred: " + e);
			// Return exception
			throw e;
		}
	}

	/**
	 * Gets the product.
	 *
	 * @param rs the rs
	 * @return the product
	 * @throws SQLException the SQL exception
	 */
	private static Products getProduct (ResultSet rs) throws SQLException {
		Products product = null;
		if (rs.next()) {
			product = new Products();
			product.setProductId(rs.getInt("PRODUCT_ID"));
			product.setProductName(rs.getString("PRODUCT_NAME"));
			product.setProductCategory(rs.getString("PRODUCT_CATEGORY"));
			product.setProductPrice(rs.getInt("PRODUCT_PRICE"));
		}
		return product;
	}

	//____________________________________________________________________________________________________
	// Select product that will be added
	//____________________________________________________________________________________________________

	/**
	 * Update product.
	 *
	 * @param productId the product id
	 * @param prouctCategory the prouct category
	 * @param productName the product name
	 * @param productPrice the product price
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void updateProduct (int productId, String prouctCategory, String productName, int productPrice) throws SQLException, ClassNotFoundException {
		String updateStmt = "UPDATE products SET PRODUCT_CATEGORY = '" + prouctCategory + "', PRODUCT_NAME = '" + productName + "', PRODUCT_PRICE =  " + productPrice + " where PRODUCT_ID = '" + productId +"'";
		// Execute
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while UPDATE Operation: " + e);
			throw e;
		}

	}

	//____________________________________________________________________________________________________
	// Delete add change
	//____________________________________________________________________________________________________

	/**
	 * Delete product with id.
	 *
	 * @param productId the product id
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void deleteProductWithId(int productId) throws SQLException, ClassNotFoundException {
		String updateStmt = "DELETE FROM products WHERE PRODUCT_ID =" + productId;
		// Execute
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while DELETE Operation: " + e);
			throw e;
		}
	}

	/**
	 * Insert product.
	 *
	 * @param prouctCategory the prouct category
	 * @param productName the product name
	 * @param productPrice the product price
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void insertProduct(String prouctCategory, String productName, int productPrice)
			throws SQLException, ClassNotFoundException {
		String updateStmt = "INSERT INTO products (PRODUCT_CATEGORY, PRODUCT_NAME, PRODUCT_PRICE)" +"VALUES ('"
				+ prouctCategory + "', '" + productName + "'," + productPrice + " )";
		// Execute UPDATE operation
		try {
			DbControl.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while INSERT Operation: " + e);
			throw e;
		}
	}
}