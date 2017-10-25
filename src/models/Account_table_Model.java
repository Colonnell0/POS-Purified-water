/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Colonnello
 */
public class Account_table_Model {

    private final SimpleIntegerProperty accountID;
    private final SimpleStringProperty accountName;
    private final SimpleStringProperty address;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty position;
    private final SimpleStringProperty dateCreated;
    private final SimpleStringProperty status;
    private final SimpleStringProperty edit;
    private final SimpleStringProperty delete;

    public Account_table_Model(
            String accountName,
            String position,
            String dateCreated,
            String status,
            int accountID,
            String address,
            String gender,
            String contact,
            String edit,
            String delete) {
        this.accountName = new SimpleStringProperty(accountName);
        this.position = new SimpleStringProperty(position);
        this.dateCreated = new SimpleStringProperty(dateCreated);
        this.status = new SimpleStringProperty(status);
        this.accountID = new SimpleIntegerProperty(accountID);
        this.address = new SimpleStringProperty(address);
        this.gender = new SimpleStringProperty(gender);
        this.contact = new SimpleStringProperty(contact);
        this.edit = new SimpleStringProperty(edit);
        this.delete = new SimpleStringProperty(delete);

    }

    public void setAccountName(String accountName) {
        this.accountName.set(accountName);
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setAccountID(int accountID) {
        this.accountID.set(accountID);
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public void setEdit(String edit) {
        this.edit.set(edit);
    }

    public void setDelete(String delete) {
        this.delete.set(delete);
    }

    //--------------------------------------------------------------------------
    public String getAccountName() {
        return this.accountName.get();
    }

    public String getPosition() {
        return this.position.get();
    }

    public String getDateCreated() {
        return this.dateCreated.get();
    }

    public String getStatus() {
        return this.status.get();
    }

    public int getAccountID() {
        return this.accountID.get();
    }

    public String getGender() {
        return this.gender.get();
    }

    public String getAddress() {
        return this.address.get();
    }

    public String getContact() {
        return this.contact.get();
    }

    public String getEdit() {
        return this.edit.get();
    }

    public String getDelete() {
        return this.delete.get();
    }

}
