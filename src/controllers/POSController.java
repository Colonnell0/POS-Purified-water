/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.POS_model;
import libs.FormatNumber;
import models.Customer_Transaction_Model;
import org.controlsfx.control.textfield.TextFields;
import models.POSTTable;

public class POSController extends POS_model implements Initializable {

    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblToPay;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerID;

    @FXML
    private Label lblCustomerAddressStreet;

    @FXML
    private Label lblCustomerAddressBarangay;

    @FXML
    private Label lblCustomerAddressCityMunicipality;

    @FXML
    private Label lblCustomerAddressProvince;

    @FXML
    private Label lblCustomerBorrowedRound;

    @FXML
    private Label lblCustomerBorrowedSlim;

    @FXML
    private Label lblCustomerBalance;

    //------------------------------------------------------
    @FXML
    private TextField txtCustomerName;
    //-------------------------------------------------------
    @FXML
    private TableView<Customer_Transaction_Model> tblCustomer;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerID;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerName;

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
    private Button btnCustomer;

    @FXML
    private Button btnDiscount;

    @FXML
    private Button btnPayTransaction;

    @FXML
    private Button btnSaleNote;

    @FXML
    private Button btnExpenses;

    @FXML
    private Button btnDelivery;

    @FXML
    private AnchorPane anchorPanePOS;

    //-------------------------------------------------------
    @FXML
    private TextField txtProductNameCode;

    @FXML
    private TableView<POSTTable> POSTransaction;

    @FXML
    private TableColumn<POSTTable, Integer> productCode;

    @FXML
    private TableColumn<POSTTable, String> productName;

    @FXML
    private TableColumn<POSTTable, Double> productQuantity;

    @FXML
    private TableColumn<POSTTable, Double> productPrice;

    @FXML
    TableColumn<POSTTable, Double> productTotal;

    private String arrayItemProduct[];
    private int borrowedRound = 0;
    private String note = "N/A";
    private int borrowedSlim = 0;
    private double subTotalPrice = 0;
    private double balance = 0;
    private double total = 0;
    private double toPay = 0;
    List<String> productID = new ArrayList<>();
    List<String> quantity = new ArrayList<>();
    private String product_code;
    private double discount;
    private int trap = 0;
    private String customerID = "";
    private int transactionBorrowedRound = 0;
    private int transactionBorrowedSlim = 0;
    private int transactionOthers = 0;
    private String transactionID;
    private String registeredYN = "";
    private int counterLogin = 0;
    private double quantity_f = 0;
    private String accountDetails[];
    static Stage modal;
    private String date = "";
    private List<String> transactionBasket = new ArrayList<String>();

    String customerTransaction[];

    //----------------------------------------------------------
    final ObservableList<POSTTable> postTableData = FXCollections.observableArrayList();
    final ObservableList<Customer_Transaction_Model> postTableCustomer = FXCollections.observableArrayList();

    FormatNumber formatNumber = new FormatNumber();
    Date dNow = new Date();
    SimpleDateFormat ft
            = new SimpleDateFormat("yyyy/MM/dd");

    public void setTableProductEnterKey(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER && !txtProductNameCode.getText().isEmpty() && Arrays.asList(arrayItemProduct).contains(txtProductNameCode.getText())) {
            String[] splited = txtProductNameCode.getText().split("\\s+");
            product_code = splited[0];

            TextInputDialog Textdialog = new TextInputDialog("1");
            Textdialog.setTitle(product_code);
            Textdialog.setHeaderText(txtProductNameCode.getText());
            Textdialog.setContentText("Please enter your Quantity:");

            DialogPane dialogPane = Textdialog.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 14px;\n"
                    + "   -fx-font-weight: bold;");

            txtProductNameCode.setText("");
            String res[] = new String[3];
            res = getProductForTable(product_code);

            Optional<String> result = Textdialog.showAndWait();
            if (result.isPresent()) {

                POSTTable entry = new POSTTable(Integer.parseInt(res[0]), res[1], Double.parseDouble(result.get()), Double.parseDouble(res[2]));
                postTableData.add(entry);

                POSTransaction.getSelectionModel().selectLast();
                subTotalPrice += POSTransaction.getSelectionModel().getSelectedItem().getProductTotal();
                transactionBasket.add(POSTransaction.getSelectionModel().getSelectedItem().getProductName() + "      " + POSTransaction.getSelectionModel().getSelectedItem().getProductPrice() + " x " + POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() + " = " + POSTransaction.getSelectionModel().getSelectedItem().getProductTotal());

                String discount_result[][] = getDiscount(lblCustomerID.getText());
                if (discount_result.length > 0) {
                    for (int i = 0; i < discount_result.length; i++) {
                        if (product_code.equalsIgnoreCase(discount_result[i][0])) {
                            if (productID.contains(product_code)) {
                                double qty = Double.parseDouble(this.quantity.get(i));
                                qty = qty + POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity();
                                quantity.remove(i);
                                quantity.add(i, "" + qty);

                            } else {
                                productID.add("" + POSTransaction.getSelectionModel().getSelectedItem().getProductCode());
                                quantity.add("" + POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity());
                            }

                        }

                    }

                    for (int i = 0; i < productID.size(); i++) {
                        for (int q = 0; q < discount_result.length; q++) {
                            if (productID.get(i).equalsIgnoreCase(discount_result[q][0])) {

                                for (int c = 1; c <= Double.parseDouble(quantity.get(i)); c++) {
                                    quantity_f = c % Double.parseDouble(discount_result[q][1]);
                                    if (quantity_f == 0) {
                                        discount += Double.parseDouble(discount_result[q][2]);

                                    }

                                }
                            }
                        }
                    }
                }

                total = subTotalPrice + balance - discount;
                toPay = total;
                discount = 0;

                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 924447151) {
                    transactionBorrowedRound += POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

                }
                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 96901344) {
                    transactionBorrowedSlim += POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

                }
                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 96901344 && POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 924447151) {
                    transactionOthers += POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

                }

                lblSubtotal.setText(formatNumber.formatNumbers("" + subTotalPrice));
                lblTotal.setText(formatNumber.formatNumbers("" + total));
                lblToPay.setText(formatNumber.formatNumbers("" + toPay));

            }
        }

    }

    public void setDataTable() {
        if (trap == 0) {
            arrayItemProduct = new String[getProductCodeName().length];
            arrayItemProduct = getProductCodeName();
            TextFields.bindAutoCompletion(txtProductNameCode, arrayItemProduct);

            productCode.setCellValueFactory(new PropertyValueFactory<>("ProductCode"));
            productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
            productQuantity.setCellValueFactory(new PropertyValueFactory<>("ProductQuantity"));
            productPrice.setCellValueFactory(new PropertyValueFactory<>("ProductPrice"));
            productTotal.setCellValueFactory(new PropertyValueFactory<>("ProductTotal"));
            POSTransaction.setItems(postTableData);
            trap++;
        }
    }

    public void setTableProductClickBtn() {

        if (txtProductNameCode.getText() != null) {
            String[] splited = txtProductNameCode.getText().split("\\s+");

            TextInputDialog Textdialog = new TextInputDialog("1");
            Textdialog.setTitle(splited[0]);
            Textdialog.setHeaderText(txtProductNameCode.getText());
            Textdialog.setContentText("Please enter your Quantity:");

            DialogPane dialogPane = Textdialog.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 14px;\n"
                    + "   -fx-font-weight: bold;");

            txtProductNameCode.setText("");
            String res[] = new String[3];
            res = getProductForTable(splited[0]);

            Optional<String> result = Textdialog.showAndWait();
            if (result.isPresent()) {
                POSTTable entry = new POSTTable(Integer.parseInt(res[0]), res[1], Double.parseDouble(result.get()), Double.parseDouble(res[2]));
                postTableData.add(entry);

                POSTransaction.getSelectionModel().selectLast();
                subTotalPrice += POSTransaction.getSelectionModel().getSelectedItem().getProductTotal();
                transactionBasket.add(POSTransaction.getSelectionModel().getSelectedItem().getProductName() + "      " + POSTransaction.getSelectionModel().getSelectedItem().getProductPrice() + " x " + POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() + " = " + POSTransaction.getSelectionModel().getSelectedItem().getProductTotal());

                String discount_result[][] = getDiscount(lblCustomerID.getText());
                if (discount_result.length > 0) {
                    for (int i = 0; i < discount_result.length; i++) {
                        if (product_code.equalsIgnoreCase(discount_result[i][0])) {
                            if (productID.contains(product_code)) {
                                double qty = Double.parseDouble(this.quantity.get(i));
                                qty = qty + POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity();
                                quantity.remove(i);
                                quantity.add(i, "" + qty);

                            } else {
                                productID.add("" + POSTransaction.getSelectionModel().getSelectedItem().getProductCode());
                                quantity.add("" + POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity());
                            }

                        }

                    }

                    for (int i = 0; i < productID.size(); i++) {
                        for (int q = 0; q < discount_result.length; q++) {
                            if (productID.get(i).equalsIgnoreCase(discount_result[q][0])) {

                                for (int c = 1; c <= Double.parseDouble(quantity.get(i)); c++) {
                                    quantity_f = c % Double.parseDouble(discount_result[q][1]);
                                    if (quantity_f == 0) {
                                        discount += Double.parseDouble(discount_result[q][2]);
                                    }
                                }
                            }
                        }
                    }
                }

                total = subTotalPrice + balance - discount;
                toPay = total;
                discount = 0;

                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 924447151) {
                    transactionBorrowedRound += POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

                }
                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 96901344) {
                    transactionBorrowedSlim += POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

                }
                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 96901344 && POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 924447151) {
                    transactionOthers += POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

                }

                lblSubtotal.setText(formatNumber.formatNumbers("" + subTotalPrice));
                lblTotal.setText(formatNumber.formatNumbers("" + total));
                lblToPay.setText(formatNumber.formatNumbers("" + toPay));

            }
        }

    }

    public void emptyBasket(ActionEvent event) {
        if (POSTransaction.getItems().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Transaction");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to empty the basket?");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                POSTransaction.getItems().clear();
                transactionBorrowedRound = 0;
                transactionBorrowedSlim = 0;
                transactionOthers = 0;
                subTotalPrice = 0;
                lblSubtotal.setText("");
                lblToPay.setText("");
                lblTotal.setText("");
                balance = 0;
                lblCustomerID.setText("");
                lblCustomerName.setText("");
                lblCustomerAddressStreet.setText("");
                lblCustomerAddressBarangay.setText("");
                lblCustomerAddressCityMunicipality.setText("");
                lblCustomerAddressProvince.setText("");
                lblCustomerBorrowedRound.setText("");
                borrowedRound = 0;
                lblCustomerBorrowedSlim.setText("");
                borrowedSlim = 0;
                lblCustomerBalance.setText("");
                lblBalance.setText("");
                transactionBasket.clear();
                discount = 0;
                quantity.clear();
                productID.clear();
                note = "N/A";
                customerTransaction = null;

            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }
    }

    public void removeProductButton() {
        int selectedIndex = POSTransaction.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (selectedIndex >= 0) {
                subTotalPrice -= POSTransaction.getSelectionModel().getSelectedItem().getProductTotal();

                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 924447151) {
                    transactionBorrowedRound -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity();
                }
                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 96901344) {
                    transactionBorrowedSlim -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity();

                }
                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 96901344 && POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 924447151) {
                    transactionOthers -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

                }

                product_code = "" + POSTransaction.getSelectionModel().getSelectedItem().getProductCode();
                String discount_result[][] = getDiscount(lblCustomerID.getText());
                if (discount_result.length > 0 & POSTransaction.getItems().size() > 0) {
                    for (int i = 0; i < discount_result.length; i++) {
                        if (product_code.equalsIgnoreCase(discount_result[i][0])) {
                            if (productID.contains(product_code)) {

                                productID.remove(i);
                                quantity.remove(i);

                            }

                        }

                    }

                    for (int i = 0; i < productID.size(); i++) {
                        for (int q = 0; q < discount_result.length; q++) {
                            if (productID.get(i).equalsIgnoreCase(discount_result[q][0])) {

                                for (int c = 1; c <= Double.parseDouble(quantity.get(i)); c++) {
                                    quantity_f = c % Double.parseDouble(discount_result[q][1]);
                                    if (quantity_f == 0) {
                                        discount += Double.parseDouble(discount_result[q][2]);

                                    }

                                }
                            }
                        }
                    }
                }

                lblSubtotal.setText(formatNumber.formatNumbers("" + subTotalPrice));
                total = (subTotalPrice + balance) - discount;
                toPay = total;
                discount = 0;
                lblTotal.setText(formatNumber.formatNumbers("" + total));
                lblToPay.setText(formatNumber.formatNumbers("" + toPay));
                POSTransaction.getItems().remove(selectedIndex);
                transactionBasket.remove(selectedIndex);
            } else {
                subTotalPrice -= POSTransaction.getSelectionModel().getSelectedItem().getProductTotal();

                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 924447151) {
                    transactionBorrowedRound -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity();
                }
                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 96901344) {
                    transactionBorrowedSlim -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity();

                }
                if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 96901344 && POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 924447151) {
                    transactionOthers -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

                }

                product_code = "" + POSTransaction.getSelectionModel().getSelectedItem().getProductCode();
                String discount_result[][] = getDiscount(lblCustomerID.getText());
                if (discount_result.length > 0 & POSTransaction.getItems().size() > 0) {
                    for (int i = 0; i < discount_result.length; i++) {
                        if (product_code.equalsIgnoreCase(discount_result[i][0])) {
                            if (productID.contains(product_code)) {

                                productID.remove(i);
                                quantity.remove(i);

                            }

                        }

                    }

                    for (int i = 0; i < productID.size(); i++) {
                        for (int q = 0; q < discount_result.length; q++) {
                            if (productID.get(i).equalsIgnoreCase(discount_result[q][0])) {

                                for (int c = 1; c <= Double.parseDouble(quantity.get(i)); c++) {
                                    quantity_f = c % Double.parseDouble(discount_result[q][1]);
                                    if (quantity_f == 0) {
                                        discount += Double.parseDouble(discount_result[q][2]);

                                    }

                                }
                            }
                        }
                    }
                }

                lblSubtotal.setText(formatNumber.formatNumbers("" + subTotalPrice));
                total = (subTotalPrice + balance) - discount;
                toPay = total;
                discount = 0;
                lblTotal.setText(formatNumber.formatNumbers("" + total));
                lblToPay.setText(formatNumber.formatNumbers("" + toPay));
                POSTransaction.getItems().remove(selectedIndex);
                transactionBasket.remove(selectedIndex);
            }
        }

    }

    public void removeProductKey(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE && POSTransaction.getItems().size() > 0) {

            int selectedIndex = POSTransaction.getSelectionModel().getSelectedIndex();
            subTotalPrice -= POSTransaction.getSelectionModel().getSelectedItem().getProductTotal();

            if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 924447151) {
                transactionBorrowedRound -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity();
            }
            if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() == 96901344) {
                transactionBorrowedSlim -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity();

            }
            if (POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 96901344 && POSTransaction.getSelectionModel().getSelectedItem().getProductCode() != 924447151) {
                transactionOthers -= POSTransaction.getSelectionModel().getSelectedItem().getProductQuantity() * 1;

            }

            product_code = "" + POSTransaction.getSelectionModel().getSelectedItem().getProductCode();
            String discount_result[][] = getDiscount(lblCustomerID.getText());
            if (discount_result.length > 0 & POSTransaction.getItems().size() > 0) {
                for (int i = 0; i < discount_result.length; i++) {
                    if (product_code.equalsIgnoreCase(discount_result[i][0])) {
                        if (productID.contains(product_code)) {

                            productID.remove(i);
                            quantity.remove(i);

                        }

                    }

                }

                for (int i = 0; i < productID.size(); i++) {
                    for (int q = 0; q < discount_result.length; q++) {
                        if (productID.get(i).equalsIgnoreCase(discount_result[q][0])) {

                            for (int c = 1; c <= Double.parseDouble(quantity.get(i)); c++) {
                                quantity_f = c % Double.parseDouble(discount_result[q][1]);
                                if (quantity_f == 0) {
                                    discount += Double.parseDouble(discount_result[q][2]);

                                }

                            }
                        }
                    }
                }
            }

            lblSubtotal.setText(formatNumber.formatNumbers("" + subTotalPrice));
            total = (subTotalPrice + balance) - discount;
            toPay = total;
            discount = 0;
            lblTotal.setText(formatNumber.formatNumbers("" + total));
            lblToPay.setText(formatNumber.formatNumbers("" + toPay));
            POSTransaction.getItems().remove(selectedIndex);
            transactionBasket.remove(selectedIndex);

        }
    }

//--------------------------------------------------------------------------------------------
    public void viewHistoryTransaction() throws IOException {
        if (Integer.parseInt(accountDetails[4]) == 3) {
            Main.customer("/views/ViewHistory.fxml", "Giovanz Purified Water | Transaction History", 800, 500);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }
    }

    public void addProduct() throws IOException {
        if (Integer.parseInt(accountDetails[4]) == 3) {
            Main.modal("/views/AddProduct.fxml", "Giovanz Purified Water | Add Product", 420, 400);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    public void Sales() throws IOException {
        if (Integer.parseInt(accountDetails[4]) >= 2) {
            Main.customer("/views/SalesPOS.fxml", "Giovanz Purified Water | Sales", 1000, 635);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    public void editProduct() throws IOException {
        if (Integer.parseInt(accountDetails[4]) == 3) {
            Main.customer("/views/EditProduct.fxml", "Giovanz Purified Water | Transaction History", 700, 500);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    /**
     * FXML Controller class
     *
     * @author Colonnello
     * @param url
     * @param rb
     * @param event
     * @throws java.io.IOException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//       

        btnSaleNote.setOnAction(e -> {
            if (Integer.parseInt(accountDetails[4]) == 2 | Integer.parseInt(accountDetails[4]) == 3) {
                try {
                    if (toPay > 0) {
                        String remarks = RemarksController.setCustomerName("/views/Remark.fxml", "Sale Note", 450, 300);
                        if (!note.equalsIgnoreCase("none")) {
                            note = remarks;

                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(POSController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }
        });

        btnCustomer.setOnAction(e -> {
            if (Integer.parseInt(accountDetails[4]) == 2 | Integer.parseInt(accountDetails[4]) == 3) {

                try {
                    customerTransaction = CustomerController.setCustomerName("/views/ViewCustomer.fxml", "View Customer Transaction", 1000, 650);
                    if (!customerTransaction[0].equalsIgnoreCase("close")) {

                        lblCustomerID.setText(customerTransaction[0]);

                        lblCustomerName.setText(customerTransaction[1]);
                        lblCustomerAddressStreet.setText(customerTransaction[2] + ", ");
                        lblCustomerAddressBarangay.setText(customerTransaction[3] + ", ");
                        lblCustomerAddressCityMunicipality.setText(customerTransaction[4] + ", ");
                        lblCustomerAddressProvince.setText(customerTransaction[5]);
                        lblCustomerBorrowedRound.setText(customerTransaction[6]);
                        borrowedRound = Integer.parseInt(customerTransaction[6]);
                        lblCustomerBorrowedSlim.setText(customerTransaction[7]);
                        borrowedSlim = Integer.parseInt(customerTransaction[7]);
                        lblCustomerBalance.setText("Php " + customerTransaction[8]);

                        customerID = lblCustomerID.getText();
                        lblBalance.setText(customerTransaction[8]);
                        balance = Double.parseDouble(customerTransaction[8]);
                        productID.clear();
                        quantity.clear();

                        String discount_result[][] = getDiscount(lblCustomerID.getText());
                        if (discount_result.length > 0) {
                            for (int i = 0; i < discount_result.length; i++) {
                                for (int p = 0; p < POSTransaction.getItems().size(); p++) {
                                    product_code = "" + POSTransaction.getItems().get(p).getProductCode();
                                    if (product_code.equalsIgnoreCase(discount_result[i][0])) {
                                        if (productID.contains(product_code)) {
                                            double qty = Double.parseDouble(this.quantity.get(i));
                                            qty = qty + POSTransaction.getItems().get(p).getProductQuantity();
                                            quantity.remove(i);
                                            quantity.add(i, "" + qty);

                                        } else {
                                            productID.add("" + POSTransaction.getItems().get(p).getProductCode());
                                            quantity.add("" + POSTransaction.getItems().get(p).getProductQuantity());
                                        }

                                    }
                                }

                            }

                            for (int i = 0; i < productID.size(); i++) {
                                for (int q = 0; q < discount_result.length; q++) {
                                    if (productID.get(i).equalsIgnoreCase(discount_result[q][0])) {

                                        for (int c = 1; c <= Double.parseDouble(quantity.get(i)); c++) {
                                            quantity_f = c % Double.parseDouble(discount_result[q][1]);
                                            if (quantity_f == 0) {
                                                discount += Double.parseDouble(discount_result[q][2]);

                                            }

                                        }
                                    }
                                }
                            }
                        }

                        total = subTotalPrice + balance - discount;
                        toPay = total;
                        discount = 0;

                        date = customerTransaction[17];
                        if (!date.equalsIgnoreCase("0000-00-00")) {
                            lblDiscount.setText("( Discount )");

                        }
                        if (date.equalsIgnoreCase("0000-00-00")) {
                            lblDiscount.setText("");

                        }

                        lblSubtotal.setText(formatNumber.formatNumbers("" + subTotalPrice));
                        lblTotal.setText(formatNumber.formatNumbers("" + total));
                        lblToPay.setText(formatNumber.formatNumbers("" + toPay));

                    }
                    //---------------------------------------------------------------
                } catch (IOException ex) {
                    Logger.getLogger(POSController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }

        }
        );

        btnPayTransaction.setOnAction(e
                -> {
            if (Integer.parseInt(accountDetails[4]) == 2 | Integer.parseInt(accountDetails[4]) == 3) {

                try {
                    if (toPay > 0) {
                        String column[] = {"customer_id", "transaction_borrowed_round", "transaction_borrowed_slim", "transaction_total", "transaction_date", "account_name", "status", "transaction_remarks", "transaction_basket", "transaction_returned_round", "transaction_returned_slim", "transaction_others", "transaction_balance", "transaction_change", "transaction_type"};
                        if (lblCustomerID.getText().isEmpty()) {
                            String values[] = {"0", "" + transactionBorrowedRound, "" + transactionBorrowedSlim, "" + (toPay - balance), ft.format(dNow), accountDetails[1], "unconfirm", note, transactionBasket.toString(), "0", "0", "" + transactionOthers, "0", "0", "POS"};
                            setTransaction("transaction", column, values);
                            transactionID = getTransactionLastID(accountDetails[1]);
                            registeredYN = "NOT_REGISTERED";
                        }
                        if (!lblCustomerID.getText().isEmpty()) {
                            String values[] = {lblCustomerID.getText(), "" + transactionBorrowedRound, "" + transactionBorrowedSlim, "" + (toPay - balance), ft.format(dNow), accountDetails[1], "unconfirm", note, transactionBasket.toString(), "0", "0", "" + transactionOthers, "0", "0", "POS"};
                            setTransaction("transaction", column, values);
                            transactionID = getTransactionLastID(accountDetails[1]);
                            registeredYN = "REGISTERED";

                        }
                        String result = PAYController.payTransaction("/views/Pay.fxml", registeredYN + " " + transactionID + ": " + accountDetails[1], 600, 500);
                        String re[] = result.split("\\s+");

                        if (result.contains("close")) {
                            setDeleteTransaction("transaction", "transaction_id", re[1].replaceAll(":", ""));

                        }
                        if (result.equalsIgnoreCase("submitted")) {
                            POSTransaction.getItems().clear();
                            transactionBorrowedRound = 0;
                            transactionBorrowedSlim = 0;
                            subTotalPrice = 0;
                            lblSubtotal.setText("");
                            lblToPay.setText("");
                            lblTotal.setText("");
                            balance = 0;
                            lblCustomerID.setText("");
                            lblCustomerName.setText("");
                            lblCustomerAddressStreet.setText("");
                            lblCustomerAddressBarangay.setText("");
                            lblCustomerAddressCityMunicipality.setText("");
                            lblCustomerAddressProvince.setText("");
                            lblCustomerBorrowedRound.setText("");
                            borrowedRound = 0;
                            lblCustomerBorrowedSlim.setText("");
                            borrowedSlim = 0;
                            lblCustomerBalance.setText("");
                            lblBalance.setText("");
                            transactionBasket.clear();
                            note = "N/A";
                            customerTransaction = null;

                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(POSController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }

        }
        );

        btnExpenses.setOnAction(e
                -> {
            if (Integer.parseInt(accountDetails[4]) == 2 | Integer.parseInt(accountDetails[4]) == 3) {
                try {
                    String expenses = ExpensesController.expense("/views/Expenses.fxml", "Expenses: " + accountDetails[1], 800, 660);

                } catch (IOException ex) {
                    Logger.getLogger(POSController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }

        }
        );

        btnDelivery.setOnAction(e
                -> {
            if (Integer.parseInt(accountDetails[4]) > 1) {

                try {
                    String delivery = DeliveryController.delivery("/views/Delivery.fxml", "Delivery: " + accountDetails[1], 1200, 800);
                    if (delivery.equalsIgnoreCase("close")) {

                    }
                } catch (IOException ex) {
                    Logger.getLogger(POSController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }
        }
        );

    }

    public void setLogin() throws UnknownHostException {
        if (counterLogin == 0) {
            counterLogin = 5;
            accountDetails = setLoginDetails();

        }
    }

    public void notYET(ActionEvent event) throws UnknownHostException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText("This feature is not available, please contact your administrator!");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(" -fx-font-size: 12px;\n"
                + "   -fx-font-weight: bold;");
        alert.showAndWait();
    }

//-------------------------------------------------------------------
}
