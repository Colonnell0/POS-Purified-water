/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.Main.stage;
import java.io.IOException;
import java.net.UnknownHostException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Customer;
import models.Customer_Transaction_Model;

/**
 *
 * @author Colonnello
 */
public class CustomerController extends Customer {

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<Customer_Transaction_Model> tblCustomer;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerID;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerName;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerCellphone;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerLandline;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressStreet;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressBarangay;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressCityMunicipality;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressProvince;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerBorrowedRound;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerBorrowedSlim;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerBalance;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerDate;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerRemarks;

    //---------------------------------------------------------------------
    @FXML
    private TextField txtCustomerName;

    static Stage modal;

    private double balance;
    private String res[][] = null;

    //---------------------------------------------------------------------
    public static String customerName[];
    final ObservableList<Customer_Transaction_Model> data = FXCollections.observableArrayList();

    public static String[] setCustomerName(String source, String title, int w, int h) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(source));
        AnchorPane showModal = loader.load();

        modal.setTitle(title);

        modal.setOnCloseRequest(e -> {
            customerName = new String[1];
            customerName[0] = "close";
            modal.close();

        });
        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNIFIED);
        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(w);
        modal.setMinHeight(h);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        modal.showAndWait();

        return customerName;
    }

    public void onKeyShowCustomer(KeyEvent key) throws UnknownHostException {

        tblCCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        tblCCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        tblCCustomerCellphone.setCellValueFactory(new PropertyValueFactory<>("CustomerCellphoneNum"));
        tblCCustomerLandline.setCellValueFactory(new PropertyValueFactory<>("CustomerLandlineNum"));
        tblCCustomerAddressStreet.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressStreet"));
        tblCCustomerAddressBarangay.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressBarangay"));
        tblCCustomerAddressCityMunicipality.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressCityMunicipality"));
        tblCCustomerAddressProvince.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressProvince"));
        tblCCustomerBorrowedRound.setCellValueFactory(new PropertyValueFactory<>("CustomerRemainingRound"));
        tblCCustomerBorrowedSlim.setCellValueFactory(new PropertyValueFactory<>("CutomerRemainingSlim"));
        tblCCustomerBalance.setCellValueFactory(new PropertyValueFactory<>("CustomerBalance"));
        tblCCustomerDate.setCellValueFactory(new PropertyValueFactory<>("CustomerDate"));
        tblCCustomerRemarks.setCellValueFactory(new PropertyValueFactory<>("CustomerRemarks"));
        tblCustomer.setItems(data);

        res = viewAllCustomerProfileGroup(setLoginDetails()[1], txtCustomerName.getText());
        tblCustomer.getItems().clear();
        if (res != null) {
            for (String[] re : res) {
                if (Double.parseDouble(re[19]) <= 0) {
                    balance = 0;
                }
                if (Double.parseDouble(re[19]) > 0) {
                    balance = Double.parseDouble(re[19]);
                }
                System.out.println(balance);

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
                        balance, 0,
                        re[20],
                        re[21], "", "", "", "",//type
                        "",//cashier
                        "");

                data.add(entry);
             
            }

        }

    }

    public void setCustomerPOS(KeyEvent key) throws Exception {

        if (key.getCode() == KeyCode.ENTER) {
            customerName = new String[18];

            customerName[0] = "" + tblCustomer.getSelectionModel().getSelectedItem().getCustomerID();
            customerName[1] = tblCustomer.getSelectionModel().getSelectedItem().getCustomerName();
            customerName[2] = tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressStreet();
            customerName[3] = tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressBarangay();
            customerName[4] = tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressCityMunicipality();
            customerName[5] = tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressProvince();
            customerName[6] = "" + tblCustomer.getSelectionModel().getSelectedItem().getCustomerRemainingRound();
            customerName[7] = "" + tblCustomer.getSelectionModel().getSelectedItem().getCutomerRemainingSlim();
            customerName[8] = "" + tblCustomer.getSelectionModel().getSelectedItem().getCustomerBalance();
            customerName[9] = tblCustomer.getSelectionModel().getSelectedItem().getCustomerDate();
            customerName[10] = tblCustomer.getSelectionModel().getSelectedItem().getCustomerRemarks();
            customerName[11] = res[tblCustomer.getSelectionModel().getSelectedIndex()][22];
            customerName[12] = res[tblCustomer.getSelectionModel().getSelectedIndex()][23];
            customerName[13] = res[tblCustomer.getSelectionModel().getSelectedIndex()][24];
            customerName[14] = res[tblCustomer.getSelectionModel().getSelectedIndex()][9];
            customerName[15] = res[tblCustomer.getSelectionModel().getSelectedIndex()][3];
            customerName[16] = res[tblCustomer.getSelectionModel().getSelectedIndex()][4];
            customerName[17] = res[tblCustomer.getSelectionModel().getSelectedIndex()][25];
            modal.close();
        }

    }

    public void setViewClose(KeyEvent key) throws Exception {

        if (key.getCode() == KeyCode.ESCAPE) {
            customerName = new String[1];
            customerName[0] = "close";
            modal.close();
        }

    }

}
