/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import libs.Database;

/**
 *
 * @author Colonnello
 */
public class EditProduct_Model extends Database {
    
    protected String[][] getProduct(String product) {
        String productName[][];
        String column[] = {"product_id", "product_name", "product_price","product_status","product_date"};

        productName = SELECT(column, "products", column, "", "WHERE product_name LIKE  '%" + product, "%'");

        return productName;
    }
    protected void setProduct(String tableName, String column[], String values[]) {

        INSERT(tableName, column, values);
    }
      /**
     *
     * @param length
     * @param alias
     * @param table
     * @param column
     */
    protected int getRandomNum(int length, String alias, String table, String column) {
        return SELECT_UNIQUE_RANDOM(length, alias, table, column);
    }
    
     protected void updateProduct(String column[], String values[], String productID) {

        UPDATE("products", column, values, "WHERE product_id = '" + productID+"'");

    }
     
    protected void setDeleteProduct(String table, String column, String id) {
        DELETE(table, column, id);
    }
    
}
