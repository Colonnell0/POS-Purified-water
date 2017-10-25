/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Customer;
import models.Customer_Transaction_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class FindCustomerController extends Customer implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Customer_Transaction_Model> tblCustomer;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerID;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerName;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerGender;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerCellphoneNum;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerLandlineNum;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressStreet;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressBarangay;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressCityMunicipality;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressProvince;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressCountry;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerBorrowedRound;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerBorrowedSlim;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerReturnedRound;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerReturnedSlim;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerRemainingRound;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerRemainingSlim;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerOthers;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerTotal;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerBalance;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerAmount;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerDate;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerRemarks;

    //---------------------------------------------------------------------
    @FXML
    private TextField txtCustomer;

    @FXML
    private static Stage stage;

    @FXML
    public String customerName;

    @FXML
    private ComboBox txtComboTotal;

    @FXML
    private ToggleButton tgbTotal;

    private String account_name = "";

    //---------------------------------------------------------------------
    final ObservableList<Customer_Transaction_Model> data = FXCollections.observableArrayList();

    //---------------------------------------------------------------------
    public void findCustomerForPOS() {
        tabulateData();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            account_name = setLoginDetails()[1];
        } catch (UnknownHostException ex) {
            Logger.getLogger(FindCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblCCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        tblCCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        tblCCustomerGender.setCellValueFactory(new PropertyValueFactory<>("CustomerGender"));
        tblCCustomerCellphoneNum.setCellValueFactory(new PropertyValueFactory<>("CustomerCellphoneNum"));
        tblCCustomerLandlineNum.setCellValueFactory(new PropertyValueFactory<>("CustomerLandlineNum"));
        tblCCustomerAddressStreet.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressStreet"));
        tblCCustomerAddressBarangay.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressBarangay"));
        tblCCustomerAddressCityMunicipality.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressCityMunicipality"));
        tblCCustomerAddressProvince.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressProvince"));
        tblCCustomerAddressCountry.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressCountry"));
        tblCCustomerBorrowedRound.setCellValueFactory(new PropertyValueFactory<>("CustomerBorrowedRound"));
        tblCCustomerBorrowedSlim.setCellValueFactory(new PropertyValueFactory<>("CustomerBorrowedSlim"));
        tblCCustomerReturnedRound.setCellValueFactory(new PropertyValueFactory<>("CustomerReturnedRound"));
        tblCCustomerReturnedSlim.setCellValueFactory(new PropertyValueFactory<>("CustomerReturnedSlim"));
        tblCCustomerRemainingRound.setCellValueFactory(new PropertyValueFactory<>("CustomerRemainingRound"));
        tblCCustomerRemainingSlim.setCellValueFactory(new PropertyValueFactory<>("CutomerRemainingSlim"));
        tblCCustomerOthers.setCellValueFactory(new PropertyValueFactory<>("CustomerOthers"));
        tblCCustomerTotal.setCellValueFactory(new PropertyValueFactory<>("CustomerTotal"));
        tblCCustomerAmount.setCellValueFactory(new PropertyValueFactory<>("CustomerAmount"));
        tblCCustomerBalance.setCellValueFactory(new PropertyValueFactory<>("CustomerBalance"));
        tblCCustomerDate.setCellValueFactory(new PropertyValueFactory<>("CustomerDate"));
        tblCCustomerRemarks.setCellValueFactory(new PropertyValueFactory<>("CustomerRemarks"));
        tblCustomer.setItems(data);

        tabulateData();

    }

    public void tabulateData() {
        tblCustomer.getItems().clear();
        String res[][] = viewAllCustomerProfileGroup(account_name, txtCustomer.getText());
        if (res != null) {
            for (String[] re : res) {
                Customer_Transaction_Model entry = new Customer_Transaction_Model(
                        Integer.parseInt(re[0]),
                        re[1],
                        re[2],
                        re[3],
                        re[4],
                        re[5],
                        re[6],
                        re[7],
                        re[8],
                        re[9],
                        Integer.parseInt(re[10]),
                        Integer.parseInt(re[11]),
                        Integer.parseInt(re[12]),
                        Integer.parseInt(re[13]),
                        Integer.parseInt(re[14]),
                        Integer.parseInt(re[15]),
                        Double.parseDouble(re[16]),
                        Double.parseDouble(re[17]),
                        Double.parseDouble(re[18]),
                        Double.parseDouble(re[19]),
                        0,
                        re[20],
                        re[21], "", "", "", "",//type
                        "",//cashier
                        "");
                data.add(entry);

            }

        }
    }
}
