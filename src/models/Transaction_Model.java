package models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Colonnello on 2/9/2017.
 */
public class Transaction_Model {

    private final SimpleIntegerProperty transaction_id;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty address;
    private final SimpleStringProperty cellphone;
    private final SimpleStringProperty landline;
    private final SimpleStringProperty productName;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty total;
    private final SimpleDoubleProperty amount;
    private final SimpleDoubleProperty balance;
    private final SimpleDoubleProperty change;
    private final SimpleIntegerProperty returnRound;
    private final SimpleIntegerProperty returnSlim;
    private final SimpleStringProperty date;
    private final SimpleStringProperty remarks;

    public Transaction_Model(
            int transaction_id,
            String customerName,
            String address,
            String cellphone,
            String landline,
            String productName,
            int quantity,
            Double total,
            Double amount,
            Double balance,
            Double change,
            int returnRound,
            int returnSlim,
            String date,
            String remarks
    ) {
        this.transaction_id = new SimpleIntegerProperty(transaction_id);
        this.customerName = new SimpleStringProperty(customerName);
        this.address = new SimpleStringProperty(address);
        this.cellphone = new SimpleStringProperty(cellphone);
        this.landline = new SimpleStringProperty(landline);
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.total = new SimpleDoubleProperty(total);
        this.amount = new SimpleDoubleProperty(amount);
        this.balance = new SimpleDoubleProperty(balance);
        this.change = new SimpleDoubleProperty(change);
        this.returnRound = new SimpleIntegerProperty(returnRound);
        this.returnSlim = new SimpleIntegerProperty(returnSlim);
        this.date = new SimpleStringProperty(date);
        this.remarks = new SimpleStringProperty(remarks);

    }

    //-------------------------------------------------------------------------------------
    public void setTransaction_id(int transaction_id) {
        this.transaction_id.set(transaction_id);
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setCellphone(String cellphone) {
        this.cellphone.set(cellphone);
    }

    public void setLandline(String landline) {
        this.landline.set(landline);
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public void setChange(double change) {
        this.change.set(change);
    }

   
    public void setReturnRound(int returnRound) {
        this.returnRound.set(returnRound);
    }

    public void setReturnSlim(int returnSlim) {
        this.returnSlim.set(returnSlim);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }
    //-----------------------------------------------------------------------------------------------

    public int getTransaction_id() {
        return transaction_id.get();
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getCellphone() {
        return cellphone.get();
    }

    public String getLandline() {
        return landline.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getTotal() {
        return total.get();
    }

    public double getAmount() {
        return amount.get();
    }

    public double getBalance() {
        return balance.get();
    }

    public double getChange() {
        return change.get();
    }


    public int getReturnRound() {
        return returnRound.get();
    }

    public int getReturnSlim() {
        return returnSlim.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getRemarks() {
        return remarks.get();
    }

}
