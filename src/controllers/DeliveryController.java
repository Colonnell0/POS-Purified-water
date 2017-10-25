/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.Main.stage;
import static controllers.POSController.modal;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libs.FormatNumber;
import models.Customer;
import models.Transaction_Model;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class DeliveryController extends Customer {

    /**
     * Initializes the controller class.
     */
    private static String delivery = "";

    //----------------------------------------------------------------------------------------------
    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtMI;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtStreet;

    @FXML
    private TextField txtBarangay;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtProvince;

    @FXML
    private TextField txtCountry;

    @FXML
    private TextField txtCellphone;

    @FXML
    private TextField txtLandline;

    @FXML
    private TextField txtProductNameCode;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @FXML
    private TableView<Transaction_Model> tblDelivery;

    @FXML
    private TableColumn<Transaction_Model, Integer> transaction_id;

    @FXML
    private TableColumn<Transaction_Model, String> customerName;

    @FXML
    private TableColumn<Transaction_Model, String> address;

    @FXML
    private TableColumn<Transaction_Model, String> cellphone;

    @FXML
    private TableColumn<Transaction_Model, String> landline;

    @FXML
    private TableColumn<Transaction_Model, String> productName;

    @FXML
    private TableColumn<Transaction_Model, Integer> quantity;

    @FXML
    private TableColumn<Transaction_Model, Double> total;

    @FXML
    private TableColumn<Transaction_Model, Double> amount;

    @FXML
    private TableColumn<Transaction_Model, Double> balance;

    @FXML
    private TableColumn<Transaction_Model, Double> change;

    @FXML
    private TableColumn<Transaction_Model, Integer> returnRound;

    @FXML
    private TableColumn<Transaction_Model, Integer> returnSlim;

    @FXML
    private TableColumn<Transaction_Model, String> date;

    @FXML
    private TableColumn<Transaction_Model, String> remarks;

    //----------------------------------------------------------------------------------------------
    final ObservableList<Transaction_Model> temp_data = FXCollections.observableArrayList();
    final ObservableList<Transaction_Model> data = FXCollections.observableArrayList();

    //----------------------------------------------------------------------------------------------
    @FXML
    private TableView<Transaction_Model> tblTempDelivery;

    @FXML
    private TableColumn<Transaction_Model, Integer> temp_transaction_id;

    @FXML
    private TableColumn<Transaction_Model, String> temp_productName;

    @FXML
    private TableColumn<Transaction_Model, Integer> temp_quantity;

    @FXML
    private TableColumn<Transaction_Model, Double> temp_total;

    @FXML
    private TableColumn<Transaction_Model, Double> temp_amount;

    @FXML
    private TableColumn<Transaction_Model, Double> temp_balance;

    @FXML
    private TableColumn<Transaction_Model, Double> temp_change;

    @FXML
    private TableColumn<Transaction_Model, Integer> temp_returnRound;

    @FXML
    private TableColumn<Transaction_Model, Integer> temp_returnSlim;

    @FXML
    private TableColumn<Transaction_Model, String> temp_remarks;

    //----------------------------------------------------------------------------------------------
    private String firstname[];
    private String lastname[];
    private String cellphoneNumber[];
    private String landlineNumber[];
    private String street[];
    private String barangay[];
    private String city[];
    private String province[];
    private String country[];
    private String temp_product[][];
    private String product[];
    private String DeliveryName;

    private int counter = 0;
    private double customerBalance = 0;
    private int customerID;

    private int transactionKeyID = 0;

    List<String> productID = new ArrayList<>();
    List<String> quantity_d = new ArrayList<>();
    private String product_code = "";
    private double quantity_f = 0;
    private double discount = 0;

    private int bRound = 0;
    private int bSlim = 0;
    private int others = 0;
    private double total_transaction = 0;

    FormatNumber formatNumber = new FormatNumber();
    Date dNow = new Date();
    SimpleDateFormat ft
            = new SimpleDateFormat("yyyy/MM/dd");

    private List<String> transaction_list = new ArrayList<>();

    //----------------------------------------------------------------------------------------------
    public static String delivery(String source, String title, int w, int h) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(source));
        AnchorPane showModal = loader.load();

        modal.setTitle(title);

        modal.setOnCloseRequest(e -> {
            delivery = "close";
            modal.close();

        });

        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UTILITY);
        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(w);
        modal.setMinHeight(h);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        modal.showAndWait();

        return delivery;
    }

    public void escHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            delivery = "close";
            modal.close();
        }
    }

    public void getCustomer() {
        if (counter == 0) {
            counter = 5;
            firstname = getFirstname();
            lastname = getLastname();
            street = getStreet();
            cellphoneNumber = getCellphone();
            landlineNumber = getLandline();
            barangay = getBarangay();
            city = getCity();
            province = getProvince();
            country = getCountry();
            customerID = 1 + Integer.parseInt(getCustomerLastID()[0]);
            transactionKeyID = 1 + Integer.parseInt(getTransactionLastID("1")[0]);
            DeliveryName = modal.getTitle().replace("Delivery: ", "");

            temp_product = setProduct();
            product = new String[temp_product.length];

            for (int r = 0; r < temp_product.length; r++) {
                product[r] = temp_product[r][0] + " " + temp_product[r][1];
            }

            TextFields.bindAutoCompletion(txtFirstname, firstname);
            TextFields.bindAutoCompletion(txtLastname, lastname);
            TextFields.bindAutoCompletion(txtStreet, street);
            TextFields.bindAutoCompletion(txtCellphone, cellphoneNumber);
            TextFields.bindAutoCompletion(txtLandline, landlineNumber);
            TextFields.bindAutoCompletion(txtBarangay, barangay);
            TextFields.bindAutoCompletion(txtCity, city);
            TextFields.bindAutoCompletion(txtProvince, province);
            TextFields.bindAutoCompletion(txtCountry, country);
            TextFields.bindAutoCompletion(txtProductNameCode, product);

            temp_transaction_id.setCellValueFactory(new PropertyValueFactory<>("Transaction_id"));
            temp_productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
            temp_quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            temp_total.setCellValueFactory(new PropertyValueFactory<>("Total"));

            transaction_id.setCellValueFactory(new PropertyValueFactory<>("Transaction_id"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
            address.setCellValueFactory(new PropertyValueFactory<>("Address"));
            cellphone.setCellValueFactory(new PropertyValueFactory<>("Cellphone"));
            landline.setCellValueFactory(new PropertyValueFactory<>("Landline"));
            productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            total.setCellValueFactory(new PropertyValueFactory<>("Total"));
            amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
            balance.setCellValueFactory(new PropertyValueFactory<>("Balance"));
            change.setCellValueFactory(new PropertyValueFactory<>("Change"));
            returnRound.setCellValueFactory(new PropertyValueFactory<>("ReturnRound"));
            returnSlim.setCellValueFactory(new PropertyValueFactory<>("ReturnSlim"));
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            remarks.setCellValueFactory(new PropertyValueFactory<>("Remarks"));

            String res[][] = viewDeliveryTransactionView(ft.format(dNow), DeliveryName);
            if (!res.equals(null)) {
                for (String[] re : res) {
                    Transaction_Model entry = new Transaction_Model(
                            Integer.parseInt(re[1]),
                            re[0],
                            re[2],
                            re[3],
                            re[4],
                            re[5],
                            Integer.parseInt(re[6]),
                            Double.parseDouble(re[7]),
                            Double.parseDouble(re[8]),
                            Double.parseDouble(re[9]),
                            Double.parseDouble(re[10]),
                            Integer.parseInt(re[11]),
                            Integer.parseInt(re[12]),
                            re[13],
                            re[14]);
                    data.add(entry);
                }
            }

            tblDelivery.setItems(data);
            tblTempDelivery.setItems(temp_data);
        }
    }

    public void findCustomer() throws IOException {
        String res[];
        res = CustomerController.setCustomerName("/views/ViewCustomer.fxml", "View Customer Transaction", 1000, 650);
        if (!res[0].equalsIgnoreCase("close")) {
            customerID = Integer.parseInt(res[0]);
            txtFirstname.setText(res[11]);
            txtFirstname.setEditable(false);
            txtMI.setText(res[12]);
            txtMI.setEditable(false);
            txtLastname.setText(res[13]);
            txtLastname.setEditable(false);
            txtStreet.setText(res[2]);
            txtStreet.setEditable(false);
            txtBarangay.setText(res[3]);
            txtBarangay.setEditable(false);
            txtCity.setText(res[4]);
            txtCity.setEditable(false);
            txtProvince.setText(res[5]);
            txtProvince.setEditable(false);
            txtCountry.setText(res[14]);
            txtCountry.setEditable(false);
            txtCellphone.setText(res[15]);
            txtCellphone.setEditable(false);
            txtLandline.setText(res[16]);
            txtLandline.setEditable(false);
            customerBalance = Double.parseDouble(res[8]);

        }
    }

    public void confirmTransaction() throws IOException {
        String mi = "";
        if (!txtMI.getText().isEmpty()) {
            mi = txtMI.getText().substring(0, 1).toUpperCase() + txtMI.getText().substring(1);
        }

        String discount_result[][] = getDiscount("" + customerID);
        if (discount_result.length > 0) {
            for (int i = 0; i < discount_result.length; i++) {
                for (int p = 0; p < tblTempDelivery.getItems().size(); p++) {
                    product_code = "" + tblTempDelivery.getItems().get(p).getTransaction_id();
                    if (product_code.equalsIgnoreCase(discount_result[i][0])) {
                        if (productID.contains(product_code)) {
                            double qty = Double.parseDouble(this.quantity_d.get(i));
                            qty = qty + tblTempDelivery.getItems().get(p).getQuantity();
                            quantity_d.remove(i);
                            quantity_d.add(i, "" + qty);

                        } else {
                            productID.add("" + tblTempDelivery.getItems().get(p).getTransaction_id());
                            quantity_d.add("" + tblTempDelivery.getItems().get(p).getQuantity());
                        }

                    }
                }

            }

            for (int i = 0; i < productID.size(); i++) {
                for (int q = 0; q < discount_result.length; q++) {
                    if (productID.get(i).equalsIgnoreCase(discount_result[q][0])) {

                        for (int c = 1; c <= Double.parseDouble(quantity_d.get(i)); c++) {
                            quantity_f = c % Double.parseDouble(discount_result[q][1]);
                            if (quantity_f == 0) {
                                discount += Double.parseDouble(discount_result[q][2]);

                            }

                        }
                    }
                }
            }
        }

        if (tblTempDelivery.getItems().size() > 0) {

            if (!customerAvail("" + customerID)) {

                String Regcolumn[] = {"customer_firstname", "customer_middle_initial", "customer_lastname", "customer_gender", "customer_cellphone_no", "customer_landline", "customer_street", "customer_barangay", "customer_city_municipality", "customer_province", "customer_country", "customer_date", "customer_status", "account_name"};
                String Regvalues[] = {txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), mi, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1), "", txtCellphone.getText(), txtLandline.getText(), txtStreet.getText().substring(0, 1).toUpperCase() + txtStreet.getText().substring(1), txtBarangay.getText().substring(0, 1).toUpperCase() + txtBarangay.getText().substring(1), txtCity.getText().substring(0, 1).toUpperCase() + txtCity.getText().substring(1), txtProvince.getText().substring(0, 1).toUpperCase() + txtProvince.getText().substring(1), txtCountry.getText().substring(0, 1).toUpperCase() + txtCountry.getText().substring(1), ft.format(dNow), " ", DeliveryName};
                setInsertCustomer("customers", Regcolumn, Regvalues);

                String column[] = {"transaction_id", "customer_id", "transaction_borrowed_round", "transaction_borrowed_slim", "transaction_total", "transaction_date", "account_name", "status", "transaction_remarks", "transaction_basket", "transaction_returned_round", "transaction_returned_slim", "transaction_others", "transaction_balance", "transaction_change", "transaction_type"};
                String values[] = {"" + transactionKeyID, "" + customerID, "" + bRound, "" + bSlim, "" + (total_transaction - discount), ft.format(dNow), DeliveryName, "unconfirm", "", transaction_list.toString(), "0", "0", "" + others, "0", "0", "Delivery"};
                setTransaction("transaction", column, values);

            } else {

                String Regcolumn[] = {"customer_firstname",
                    "customer_middle_initial",
                    "customer_lastname",
                    "customer_cellphone_no",
                    "customer_landline",
                    "customer_street",
                    "customer_barangay",
                    "customer_city_municipality",
                    "customer_province",
                    "customer_country",
                    "customer_date",
                    "customer_status"};
                String Regvalues[] = {txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1),
                    mi,
                    txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1),
                    txtCellphone.getText(),
                    txtLandline.getText(),
                    txtStreet.getText().substring(0, 1).toUpperCase() + txtStreet.getText().substring(1),
                    txtBarangay.getText().substring(0, 1).toUpperCase() + txtBarangay.getText().substring(1),
                    txtCity.getText().substring(0, 1).toUpperCase() + txtCity.getText().substring(1),
                    txtProvince.getText().substring(0, 1).toUpperCase() + txtProvince.getText().substring(1),
                    txtCountry.getText().substring(0, 1).toUpperCase() + txtCountry.getText().substring(1),
                    ft.format(dNow),
                    ""};
                updateCustomer(Regcolumn, Regvalues, "" + customerID);

                String column[] = {"transaction_id", "customer_id", "transaction_borrowed_round", "transaction_borrowed_slim", "transaction_total", "transaction_date", "account_name", "status", "transaction_remarks", "transaction_basket", "transaction_returned_round", "transaction_returned_slim", "transaction_others", "transaction_balance", "transaction_change", "transaction_type"};
                String values[] = {"" + transactionKeyID, "" + customerID, "" + bRound, "" + bSlim, "" + (total_transaction - discount), ft.format(dNow), DeliveryName, "unconfirm", "", transaction_list.toString(), "0", "0", "" + others, "0", "0", "Delivery"};
                setTransaction("transaction", column, values);

            }

            String res = CustomerDeliveryController.customerDelivery("/views/CustomerDelivery.fxml", "" + transactionKeyID, 530, 450);
            if (res.equalsIgnoreCase("submitted")) {

                String result[][] = viewDeliveryTransaction(ft.format(dNow), "" + transactionKeyID, DeliveryName);
                for (String[] re : result) {
                    Transaction_Model entry = new Transaction_Model(
                            Integer.parseInt(re[1]),
                            re[0],
                            re[2],
                            re[3],
                            re[4],
                            re[5],
                            Integer.parseInt(re[6]),
                            Double.parseDouble(re[7]),
                            Double.parseDouble(re[8]),
                            Double.parseDouble(re[9]),
                            Double.parseDouble(re[10]),
                            Integer.parseInt(re[11]),
                            Integer.parseInt(re[12]),
                            re[13],
                            re[14]);
                    data.add(entry);
                }

                tblDelivery.setItems(data);
                tblTempDelivery.getItems().clear();
                txtFirstname.setText("");
                txtMI.setText("");
                txtLastname.setText("");
                txtStreet.setText("");
                txtBarangay.setText("");
                txtCity.setText("");
                txtProvince.setText("");
                txtCountry.setText("");
                txtCellphone.setText("");
                txtLandline.setText("");
                txtProductNameCode.setText("");
                transactionKeyID++;
                customerID = 0;
                discount = 0;
                bRound = 0;
                bSlim = 0;
                total_transaction = 0;
                transaction_list.clear();
                productID.clear();
                quantity_d.clear();
                customerID = 1 + Integer.parseInt(getCustomerLastID()[0]);
                transactionKeyID = 1 + Integer.parseInt(getTransactionLastID("1")[0]);

                txtFirstname.setEditable(true);
                txtMI.setEditable(true);
                txtLastname.setEditable(true);
                txtStreet.setEditable(true);
                txtBarangay.setEditable(true);
                txtCity.setEditable(true);
                txtProvince.setEditable(true);
                txtCountry.setEditable(true);
                txtCellphone.setEditable(true);
                txtLandline.setEditable(true);

            }
            if (res.equalsIgnoreCase("close")) {
                setDeleteTransaction("transaction", "transaction_id", "" + transactionKeyID);
                productID.clear();
                quantity_d.clear();
                discount = 0;
            }

        }
    }

    public void setTableCutomerDelivery(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER
                && !txtFirstname.getText().isEmpty()
                && !txtLastname.getText().isEmpty()
                && !txtBarangay.getText().isEmpty()
                && !txtCity.getText().isEmpty()
                && !txtProvince.getText().isEmpty()
                && !txtCountry.getText().isEmpty()
                && !txtProductNameCode.getText().isEmpty()) {
            String[] splited = txtProductNameCode.getText().split("\\s+");

            TextInputDialog Textdialog = new TextInputDialog("1");
            Textdialog.setTitle(splited[0]);
            Textdialog.setHeaderText(txtProductNameCode.getText());
            Textdialog.setContentText("Please enter your Quantity:");

            DialogPane dialogPane = Textdialog.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 14px;\n"
                    + "   -fx-font-weight: bold;");

            txtProductNameCode.setText("");
            String res[][] = new String[1][3];
            res = setProduct(splited[0]);

            Optional<String> result = Textdialog.showAndWait();
            if (result.isPresent()) {

                Transaction_Model entry = new Transaction_Model(
                        Integer.parseInt(splited[0]),//id
                        "",//name
                        "",//address
                        "",//cellphone
                        "",//landline
                        res[0][1],//productname
                        Integer.parseInt(result.get()),//quantity
                        (Double.parseDouble(res[0][2]) * Double.parseDouble(result.get())),//total
                        0.0,//amount
                        0.0,//balance
                        0.0,//change
                        0,//r Round
                        0,//r Slim
                        "",//date
                        ""//remarks
                );
                temp_data.add(entry);

                if (splited[0].equalsIgnoreCase("96901344")) {
                    bSlim += Integer.parseInt(result.get());
                }
                if (splited[0].equalsIgnoreCase("924447151")) {
                    bRound += Integer.parseInt(result.get());
                }
                if (!splited[0].equalsIgnoreCase("96901344") && !splited[0].equalsIgnoreCase("924447151")) {
                    others += Integer.parseInt(result.get());
                }

                tblTempDelivery.getSelectionModel().selectLast();
                transaction_list.add(tblTempDelivery.getSelectionModel().getSelectedItem().getProductName() + "      " + res[0][2] + " x " + result.get() + " = " + tblTempDelivery.getSelectionModel().getSelectedItem().getTotal());
                total_transaction += tblTempDelivery.getSelectionModel().getSelectedItem().getTotal();
                total_transaction = total_transaction - discount;
                discount = 0;

            }

        }
    }

    public void removeItem(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            int selectedIndex = tblTempDelivery.getSelectionModel().getSelectedIndex();
            total_transaction -= tblTempDelivery.getSelectionModel().getSelectedItem().getTotal();
            tblTempDelivery.getItems().remove(selectedIndex);
            transaction_list.remove(selectedIndex);

            if (tblTempDelivery.getSelectionModel().getSelectedItem().getTransaction_id() == 924447151) {
                bRound -= tblTempDelivery.getSelectionModel().getSelectedItem().getQuantity();
            }
            if (tblTempDelivery.getSelectionModel().getSelectedItem().getTransaction_id() == 96901344) {
                bSlim -= tblTempDelivery.getSelectionModel().getSelectedItem().getQuantity();

            }
            if (tblTempDelivery.getSelectionModel().getSelectedItem().getTransaction_id() != 96901344 && tblTempDelivery.getSelectionModel().getSelectedItem().getTransaction_id() != 924447151) {
                others -= tblTempDelivery.getSelectionModel().getSelectedItem().getQuantity();

            }

        }
    }

}
