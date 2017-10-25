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
public class Customer_Transaction_Model {

    private final SimpleIntegerProperty customerID;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty customerGender;
    private final SimpleStringProperty customerCellphoneNum;
    private final SimpleStringProperty customerLandlineNum;
    private final SimpleStringProperty customerAddressStreet;
    private final SimpleStringProperty customerAddressBarangay;
    private final SimpleStringProperty customerAddressCityMunicipality;
    private final SimpleStringProperty customerAddressProvince;
    private final SimpleStringProperty customerAddressCountry;
    private final SimpleIntegerProperty customerBorrowedRound;
    private final SimpleIntegerProperty customerBorrowedSlim;
    private final SimpleIntegerProperty customerReturnedRound;
    private final SimpleIntegerProperty customerReturnedSlim;
    private final SimpleIntegerProperty customerRemainingRound;
    private final SimpleIntegerProperty customerRemainingSlim;
    private final SimpleDoubleProperty customerOthers;
    private final SimpleDoubleProperty customerTotal;
    private final SimpleDoubleProperty customerBalance;
    private final SimpleDoubleProperty customerChange;
    private final SimpleDoubleProperty customerAmount;
    private final SimpleStringProperty customerDate;
    private final SimpleStringProperty customerRemarks;
    private final SimpleStringProperty customerTransactionType;
    private final SimpleStringProperty customerTransactionCashier;
    private final SimpleStringProperty customerTransactionStatus;
    private final SimpleStringProperty edit;
    private final SimpleStringProperty delete;
    private final SimpleStringProperty addDiscount;

    public Customer_Transaction_Model(
            int customerID,
            String customerName,
            String customerGender,
            String customerCellphoneNum,
            String customerLandlineNum,
            String customerAddressStreet,
            String customerAddressBarangay,
            String customerAddressCityMunicipality,
            String customerAddressProvince,
            String customerAddressCountry,
            int customerBorrowedRound,
            int customerBorrowedSlim,
            int customerReturnedRound,
            int customerReturnedSlim,
            int customerRemainingRound,
            int customerRemainingSlim,
            double customerOthers,
            double customerTotal,
            double customerAmount,
            double customerBalance,
            double customerChange,
            String customerDate,
            String customerRemarks,
            String customerTransactionType,
            String customerTransactionCashier,
            String customerTransactionStatus,
            String edit,
            String addDiscount,
            String delete) {

        this.customerID = new SimpleIntegerProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerGender = new SimpleStringProperty(customerGender);
        this.customerCellphoneNum = new SimpleStringProperty(customerCellphoneNum);
        this.customerLandlineNum = new SimpleStringProperty(customerLandlineNum);
        this.customerAddressStreet = new SimpleStringProperty(customerAddressStreet);
        this.customerAddressBarangay = new SimpleStringProperty(customerAddressBarangay);
        this.customerAddressCityMunicipality = new SimpleStringProperty(customerAddressCityMunicipality);
        this.customerAddressProvince = new SimpleStringProperty(customerAddressProvince);
        this.customerAddressCountry = new SimpleStringProperty(customerAddressCountry);
        this.customerBorrowedRound = new SimpleIntegerProperty(customerBorrowedRound);
        this.customerBorrowedSlim = new SimpleIntegerProperty(customerBorrowedSlim);
        this.customerReturnedRound = new SimpleIntegerProperty(customerReturnedRound);
        this.customerReturnedSlim = new SimpleIntegerProperty(customerReturnedSlim);
        this.customerRemainingRound = new SimpleIntegerProperty(customerRemainingRound);
        this.customerRemainingSlim = new SimpleIntegerProperty(customerRemainingSlim);
        this.customerOthers = new SimpleDoubleProperty(customerOthers);
        this.customerTotal = new SimpleDoubleProperty(customerTotal);
        this.customerBalance = new SimpleDoubleProperty(customerBalance);
        this.customerChange = new SimpleDoubleProperty(customerChange);
        this.customerAmount = new SimpleDoubleProperty(customerAmount);
        this.customerDate = new SimpleStringProperty(customerDate);
        this.customerRemarks = new SimpleStringProperty(customerRemarks);
        this.customerTransactionType = new SimpleStringProperty(customerTransactionType);
        this.customerTransactionCashier = new SimpleStringProperty(customerTransactionCashier);
        this.customerTransactionStatus = new SimpleStringProperty(customerTransactionStatus);
        this.edit = new SimpleStringProperty(edit);
        this.delete = new SimpleStringProperty(delete);
        this.addDiscount = new SimpleStringProperty(addDiscount);

    }

    //-----------------------------------------------------------------------------------
    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender.set(customerGender);
    }

    public void setCustomerCellphoneNum(String customerCellphoneNum) {
        this.customerCellphoneNum.set(customerCellphoneNum);
    }

    public void setCustomerLandlineNum(String customerLandlineNum) {
        this.customerLandlineNum.set(customerLandlineNum);
    }

    public void setCustomerAddressStreet(String customerAddressStreet) {
        this.customerAddressStreet.set(customerAddressStreet);
    }

    public void setCustomerAddress(String customerAddressBarangay) {
        this.customerAddressBarangay.set(customerAddressBarangay);
    }

    public void setCustomerAddressCityMunicipality(String customerAddressCityMunicipality) {
        this.customerAddressCityMunicipality.set(customerAddressCityMunicipality);
    }

    public void setCustomerAddressProvince(String customerAddressProvince) {
        this.customerAddressProvince.set(customerAddressProvince);
    }

    public void setCustomerAddressCountry(String customerAddressCountry) {
        this.customerAddressCountry.set(customerAddressCountry);
    }

    public void setCustomerBorrowedRound(int customerBorrowedRound) {
        this.customerBorrowedRound.set(customerBorrowedRound);
    }

    public void setCustomerBorrowedSlim(int customerBorrowedSlim) {
        this.customerBorrowedSlim.set(customerBorrowedSlim);
    }

    public void setCustomerReturnedRound(int customerReturnedRound) {
        this.customerReturnedRound.set(customerReturnedRound);
    }

    public void setCustomerReturnedSlim(int customerReturnedSlim) {
        this.customerReturnedSlim.set(customerReturnedSlim);
    }

    public void setCustomerRemainingRound(int customerRemainingRound) {
        this.customerRemainingRound.set(customerRemainingRound);
    }

    public void setCustomerRemainingSlim(int customerRemainingSlim) {
        this.customerRemainingSlim.set(customerRemainingSlim);
    }

    public void setCustomerOthers(double customerOthers) {
        this.customerOthers.set(customerOthers);
    }

    public void setCustomerTotal(double customerTotal) {
        this.customerTotal.set(customerTotal);
    }

    public void setCustomerBalance(double customerBalance) {
        this.customerBalance.set(customerBalance);
    }

    public void setCustomerChange(double customerChange) {
        this.customerChange.set(customerChange);
    }

    public void setCustomerAmount(double customerAmount) {
        this.customerBalance.set(customerAmount);
    }

    public void setCustomerDate(String customerDate) {
        this.customerDate.set(customerDate);
    }

    public void setCustomerRemarks(String customerRemarks) {
        this.customerRemarks.set(customerRemarks);
    }

    public void setCustomerTransactionType(String customerTransactionType) {
        this.customerTransactionType.set(customerTransactionType);
    }

    public void setCustomerTransactionCashier(String customerTransactionCashier) {
        this.customerTransactionCashier.set(customerTransactionCashier);
    }

    public void setCustomerTransactionStatus(String customerTransactionStatus) {
        this.customerTransactionStatus.set(customerTransactionStatus);
    }

    public void setEdit(String edit) {
        this.edit.set(edit);
    }

    public void setDelete(String delete) {
        this.delete.set(delete);
    }

    public void setAddDiscount(String addDiscount) {
        this.addDiscount.set(addDiscount);
    }

    //--------------------------------------------------------------------
    public int getCustomerID() {
        return this.customerID.get();
    }

    public String getCustomerName() {
        return this.customerName.get();
    }

    public String getCustomerGender() {
        return this.customerGender.get();
    }

    public String getCustomerCellphoneNum() {
        return this.customerCellphoneNum.get();
    }

    public String getCustomerLandlineNum() {
        return this.customerLandlineNum.get();
    }

    public String getCustomerAddressStreet() {
        return this.customerAddressStreet.get();
    }

    public String getCustomerAddressBarangay() {
        return this.customerAddressBarangay.get();
    }

    public String getCustomerAddressCityMunicipality() {
        return this.customerAddressCityMunicipality.get();
    }

    public String getCustomerAddressProvince() {
        return this.customerAddressProvince.get();
    }

    public String getCustomerAddressCountry() {
        return this.customerAddressCountry.get();
    }

    public int getCustomerBorrowedRound() {
        return this.customerBorrowedRound.get();
    }

    public int getCustomerBorrowedSlim() {
        return this.customerBorrowedSlim.get();
    }

    public int getCustomerReturnedRound() {
        return this.customerReturnedRound.get();
    }

    public int getCustomerReturnedSlim() {
        return this.customerReturnedSlim.get();
    }

    public int getCustomerRemainingRound() {
        return this.customerRemainingRound.get();
    }

    public int getCutomerRemainingSlim() {
        return this.customerRemainingSlim.get();
    }

    public double getCustomerOthers() {
        return this.customerOthers.get();
    }

    public double getCustomerTotal() {
        return this.customerTotal.get();
    }

    public double getCustomerBalance() {
        return this.customerBalance.get();
    }

    public double getCustomerChange() {
        return this.customerChange.get();
    }

    public double getCustomerAmount() {
        return this.customerAmount.get();
    }

    public String getCustomerDate() {
        return this.customerDate.get();
    }

    public String getCustomerRemarks() {
        return this.customerRemarks.get();
    }

    public String getCustomerTransactionType() {
        return this.customerTransactionType.get();
    }

    public String getCustomerTransactionCashier() {
        return this.customerTransactionCashier.get();
    }

    public String getCustomerTransactionStatus() {
        return this.customerTransactionStatus.get();
    }

    public String getEdit() {
        return this.edit.get();
    }

    public String getDelete() {
        return this.delete.get();
    }

    public String getAddDiscount() {
        return this.addDiscount.get();
    }

}
