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
public class Discount_table_Model {

    private final SimpleIntegerProperty discountID;
    private final SimpleStringProperty discountCustomerName;
    private final SimpleStringProperty discountGender;
    private final SimpleStringProperty discountCellphone;
    private final SimpleStringProperty discountLandline;
    private final SimpleStringProperty discountStreet;
    private final SimpleStringProperty discountBarangay;
    private final SimpleStringProperty discountCity;
    private final SimpleStringProperty discountProvince;
    private final SimpleStringProperty discountCountry;
    private final SimpleStringProperty discountProduct;
    private final SimpleIntegerProperty discountNumberOfProduct;
    private final SimpleDoubleProperty discountDiscount;
    private final SimpleStringProperty discountDate;
    private final SimpleStringProperty discountEdit;
    private final SimpleStringProperty discountDelete;

    public Discount_table_Model(
            int discountID,
            String discountCustomerName,
            String discountGender,
            String discountCellphone,
            String discountLandline,
            String discountStreet,
            String discountBarangay,
            String discountCity,
            String discountProvince,
            String discountCountry,
            String discountProduct,
            int discountNumberOfProduct,
            double discountDiscount,
            String discountDate,
            String discountEdit,
            String discountDelete) {
        this.discountID = new SimpleIntegerProperty(discountID);
        this.discountCustomerName = new SimpleStringProperty(discountCustomerName);
        this.discountGender = new SimpleStringProperty(discountGender);
        this.discountCellphone = new SimpleStringProperty(discountCellphone);
        this.discountLandline = new SimpleStringProperty(discountLandline);
        this.discountStreet = new SimpleStringProperty(discountStreet);
        this.discountBarangay = new SimpleStringProperty(discountBarangay);
        this.discountCity = new SimpleStringProperty(discountCity);
        this.discountProvince = new SimpleStringProperty(discountProvince);
        this.discountCountry = new SimpleStringProperty(discountCountry);
        this.discountProduct = new SimpleStringProperty(discountProduct);
        this.discountNumberOfProduct = new SimpleIntegerProperty(discountNumberOfProduct);
        this.discountDiscount = new SimpleDoubleProperty(discountDiscount);
        this.discountDate = new SimpleStringProperty(discountDate);
        this.discountEdit = new SimpleStringProperty(discountEdit);
        this.discountDelete = new SimpleStringProperty(discountDelete);

    }

    public void setDiscountID(int discountID) {
        this.discountID.set(discountID);
    }

    public void setDiscountCustomerName(String discountCustomerName) {
        this.discountCustomerName.set(discountCustomerName);
    }

    public void setDiscountGender(String discountGender) {
        this.discountGender.set(discountGender);
    }

    public void setDiscountCellphone(String discountCellphone) {
        this.discountCellphone.set(discountCellphone);
    }

    public void setDiscountLandline(String discountLandline) {
        this.discountLandline.set(discountLandline);
    }

    public void setDiscountStreet(String discountStreet) {
        this.discountStreet.set(discountStreet);
    }

    public void setDiscountCity(String discountCity) {
        this.discountCity.set(discountCity);
    }

    public void setDiscountProvince(String discountProvince) {
        this.discountProvince.set(discountProvince);
    }

    public void setDiscountCountry(String discountCountry) {
        this.discountCountry.set(discountCountry);
    }

    public void setDiscountProduct(String discountProduct) {
        this.discountProduct.set(discountProduct);
    }

    public void setDiscountNumberOfProduct(int discountNumberOfProduct) {
        this.discountNumberOfProduct.set(discountNumberOfProduct);
    }

    public void setDiscountDiscount(double discountDiscount) {
        this.discountDiscount.set(discountDiscount);
    }

    public void setDiscountDate(String discountDate) {
        this.discountDate.set(discountDate);

    }

    public void setDiscountEdit(String discountEdit) {
        this.discountEdit.set(discountEdit);
    }

    public void setDiscountDelete(String discountDelete) {
        this.discountDelete.set(discountDelete);
    }

//------------------------------------------------------------------------------
    public int getDiscountID() {
        return this.discountID.get();
    }

    public String getDiscountCustomerName() {
        return this.discountCustomerName.get();
    }

    public String getDiscountGender() {
        return this.discountGender.get();
    }

    public String getDiscountCellphone() {
        return this.discountCellphone.get();
    }

    public String getDiscountLandline() {
        return this.discountLandline.get();
    }

    public String getDiscountSteet() {
        return this.discountStreet.get();
    }

    public String getDiscountBarangay() {
        return this.discountBarangay.get();
    }

    public String getDiscountCity() {
        return this.discountCity.get();
    }

    public String getDiscountProvince() {
        return this.discountProvince.get();
    }

    public String getDiscountCountry() {
        return this.discountCountry.get();
    }

    public String getDiscountProduct() {
        return this.discountProduct.get();
    }

    public int getDiscountNumberOfProduct() {
        return this.discountNumberOfProduct.get();
    }

    public double getDiscountDiscount() {
        return this.discountDiscount.get();
    }

    public String getDiscountDate() {
        return this.discountDate.get();
    }

    public String getDiscountEdit() {
        return this.discountEdit.get();
    }

    public String getDiscountDelete() {
        return this.discountDelete.get();
    }
}
