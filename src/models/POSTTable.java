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
 public class POSTTable {

        private final SimpleIntegerProperty productCode;
        private final SimpleStringProperty productName;
        private final SimpleDoubleProperty productQuantity;
        private final SimpleDoubleProperty productPrice;
        private SimpleDoubleProperty productTotal;

        /**
         *
         * @param productCode
         * @param productName
         * @param productQuantity
         * @param productPrice
         */
        public POSTTable(int productCode, String productName, double productQuantity, double productPrice) {
            this.productCode = new SimpleIntegerProperty(productCode);
            this.productName = new SimpleStringProperty(productName);
            this.productQuantity = new SimpleDoubleProperty(productQuantity);
            this.productPrice = new SimpleDoubleProperty(productPrice);
        }

        //-----------------------------------------------------
        //-----------------------------------------------------
        public void setProductCode(int productCode) {
            this.productCode.set(productCode);
        }

        public void setProductName(String productName) {
            this.productName.set(productName);
        }

        public void setProductQuantity(double productQuantity) {
            this.productQuantity.set(productQuantity);
        }

        public void setProductPrice(double productPrice) {
            this.productTotal.set(productPrice);

        }

        //-----------------------------------------------------
        //-----------------------------------------------------
        public int getProductCode() {
            return this.productCode.get();
        }

        public String getProductName() {
            return this.productName.get();
        }

        public double getProductQuantity() {
            return this.productQuantity.get();
        }

        public double getProductPrice() {
            return this.productPrice.get();
        }

        public double getProductTotal() {
            double total = this.productQuantity.get() * this.getProductPrice();
            return total;
        }

    }

