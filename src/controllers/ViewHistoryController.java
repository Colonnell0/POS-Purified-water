/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Customer_Transaction_Model;
import models.Transaction_History_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class ViewHistoryController extends Transaction_History_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    List<String> list = new ArrayList<>();

    @FXML
    private ComboBox cmbNumberColumns;

    @FXML
    private DatePicker dtpDate;

    private int cmbData = 5;

    private String date = "";

    private String limit = "";

    @FXML
    private TableView<Customer_Transaction_Model> tblCustomer;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerID;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerName;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerTotal;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerAmount;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerBalance;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerType;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerCashier;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerStatus;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerDate;

    final ObservableList<Customer_Transaction_Model> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list.add(cmbData + " Columns");
        for (int i = 1; i <= 10; i++) {
            cmbData *= 2;
            list.add(cmbData + " Columns");
        }
        ObservableList obList = FXCollections.observableList(list);
        cmbNumberColumns.setItems(obList);
        cmbNumberColumns.setValue(5 + " Columns");
        dtpDate.setValue(LocalDate.now());

        tblCCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        tblCCustomerType.setCellValueFactory(new PropertyValueFactory<>("CustomerTransactionType"));
        tblCCustomerCashier.setCellValueFactory(new PropertyValueFactory<>("CustomerTransactionCashier"));
        tblCCustomerStatus.setCellValueFactory(new PropertyValueFactory<>("CustomerTransactionStatus"));
        tblCCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        tblCCustomerTotal.setCellValueFactory(new PropertyValueFactory<>("CustomerTotal"));
        tblCCustomerAmount.setCellValueFactory(new PropertyValueFactory<>("CustomerAmount"));
        tblCCustomerBalance.setCellValueFactory(new PropertyValueFactory<>("CustomerBalance"));
        tblCCustomerDate.setCellValueFactory(new PropertyValueFactory<>("CustomerDate"));
        tblCustomer.setItems(data);

        date = dtpDate.getValue().toString();
        limit = "LIMIT " + cmbNumberColumns.getValue().toString().replace(" Columns", "");
        tabulateData();

    }

    private void tabulateData() {
        tblCustomer.getItems().clear();
        String res[][] = viewAllCustomerProfile(limit, date);
        if (res != null) {
            for (String[] re : res) {
                Customer_Transaction_Model entry = new Customer_Transaction_Model(
                        Integer.parseInt(re[0]),//id
                        re[1],//name
                        "",//gender
                        "",//cellphone
                        "",//landline
                        "",//street
                        "",//barangay
                        "",//city
                        "",//province
                        "",//country
                        0,//b round
                        0,//b slim
                        0,//rn round
                        0,//rn slim
                        0,//rm round
                        0,//rm slim
                        0,//others
                        Double.parseDouble(re[2]),//total
                        Double.parseDouble(re[3]),//amount
                        Double.parseDouble(re[4]),//balance
                        0,//change
                        re[8],//date
                        "",//remarks
                        re[5],//type
                        re[6],//cashier
                        re[7], "",//type
                        "",//cashier
                        "");//status

                data.add(entry);

            }
        }
    }

    public void setCmbValue() {
        limit = "LIMIT " + cmbNumberColumns.getValue().toString().replace(" Columns", "");
        tabulateData();

    }

    public void setDate() {

        date = dtpDate.getValue().toString();
        tabulateData();
    }

}
