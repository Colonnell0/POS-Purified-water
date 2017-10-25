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
public class Expenses_Table_Model {

    private final SimpleIntegerProperty expensesID;
    private final SimpleStringProperty expenses;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty cashierName;
    private final SimpleStringProperty received;
    private final SimpleStringProperty date;
    private final SimpleStringProperty note;

    public Expenses_Table_Model(
            int expensesID,
            String expenses,
            double amount,
            String cashierName,
            String received,
            String date,
            String note) {
        this.expensesID = new SimpleIntegerProperty(expensesID);
        this.expenses = new SimpleStringProperty(expenses);
        this.amount = new SimpleDoubleProperty(amount);
        this.cashierName = new SimpleStringProperty(cashierName);
        this.received = new SimpleStringProperty(received);
        this.date = new SimpleStringProperty(date);
        this.note = new SimpleStringProperty(note);

    }
    public void setExpensesID(int expensesID){
        this.expensesID.set(expensesID);
    }
    public void setExpenses(String expenses){
        this.expenses.set(expenses);
    }
    public void setAmount(double amount){
        this.amount.set(amount);
    }
    public void setCashierName(String cashierName){
        this.cashierName.set(cashierName);
    }
    public void setReceived(String received){
        this.received.set(received);
    }
    public void setDate(String date){
        this.date.set(date);
    }
    public void setNote(String note){
        this.note.set(note);
    }
    
    //--------------------------------------------------------------------------
    public int getExpensesID(){
        return this.expensesID.get();
    }
    public String getExpenses(){
        return this.expenses.get();
    }
    public double getAmount(){
        return this.amount.get();
    }
    public String getCashierName(){
        return this.cashierName.get();
    }
    public String getReceived(){
        return this.received.get();
    }
    public String getDate(){
        return this.date.get();
    }
    public String getNote(){
        return this.note.get();
    }

}
