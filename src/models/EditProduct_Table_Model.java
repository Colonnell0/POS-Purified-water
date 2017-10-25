/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Colonnello
 */
public class EditProduct_Table_Model {

    private final SimpleIntegerProperty productID;
    private final SimpleStringProperty productName;
    private final SimpleDoubleProperty productPrice;
    private final SimpleStringProperty productStatus;
    private final SimpleStringProperty productDate;
    private final SimpleStringProperty edit;
    private final SimpleStringProperty delete;

    public EditProduct_Table_Model(
            int productID,
            String productName,
            double productPrice,
            String productStatus,
            String productDate,
            String edit,
            String delete
    ) {
        this.productID = new SimpleIntegerProperty(productID);
        this.productName = new SimpleStringProperty(productName);
        this.productPrice = new SimpleDoubleProperty(productPrice);
        this.productStatus = new SimpleStringProperty(productStatus);
        this.productDate = new SimpleStringProperty(productDate);
        this.edit = new SimpleStringProperty(edit);
        this.delete = new SimpleStringProperty(delete);

    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setProductPrice(double productPrice) {
        this.productPrice.set(productPrice);
    }

    public void setProductStatus(String productStatus) {
        this.productStatus.set(productStatus);
    }

    public void setProductDate(String productDate) {
        this.productDate.set(productDate);
    }

    public void setEdit(String edit) {
        this.edit.set(edit);
    }

    public void setDelete(String delete) {
        this.delete.set(delete);
    }

    //--------------------------------------------------------------------------
    public int getProductID() {
        return this.productID.get();
    }

    public String getProductName() {
        return this.productName.get();
    }

    public Double getProductPrice() {
        return this.productPrice.get();
    }

    public String getProductStatus() {
        return this.productStatus.get();
    }

    public String getProductDate() {
        return this.productDate.get();
    }

    public String getEdit() {
        return this.edit.get();
    }

    public String getDelete() {
        return this.delete.get();
    }

}
