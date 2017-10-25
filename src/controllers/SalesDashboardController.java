/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.Main.stage;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libs.FormValidation;
import libs.FormatNumber;
import models.Customer_Transaction_Model;
import models.Dashboard_Model;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class SalesDashboardController extends Dashboard_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lblDataEntry;

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
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerOthers;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerTotal;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerBalance;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerChange;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerAmount;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerDate;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerRemarks;

    //---------------------------------------------------------------------
    @FXML
    private ToggleButton tgbTotal;

    @FXML
    private TextField txtCustomer;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TextField txtProvince;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtBarangay;

    @FXML
    private ComboBox cmbTransaction;

    @FXML
    private ComboBox cmbNumberOfColumns;

    @FXML
    private ComboBox cmbAccounts;

    private String address[][];
    private String txtAddressCity[][];
    private String txtAddressProvince[][];
    private String txtAddressCountry[][];

    Stage modal = new Stage();

    //---------------------------------------------------------------------
    final ObservableList<Customer_Transaction_Model> data = FXCollections.observableArrayList();
    FormValidation formVal = new FormValidation();
    FormatNumber formatNumber = new FormatNumber();

    private double finalChange;

    private String transactionType = " ";
    private String customerName = " ";
    private String to = "";
    private String from = "";
    private String province = " ";
    private String city = " ";
    private String barangay = " ";
    private String account="";

    private int print_start;
    private int print_end;

    //---------------------------------------------------------------------
    @FXML
    public void findCustomerCollectibles() {

        customerName = " AND CONCAT(customers.customer_firstname,' ',customers.customer_middle_initial,'. ',customers.customer_lastname) LIKE '%" + txtCustomer.getText() + "%'";
        tabulateData();
    }

    //---------------------------------------------------------------------
    @FXML
    public void setTimeFrom() throws ParseException {
        from = " AND datediff(ifnull(transaction.transaction_date,'0000-00-00'), '" + dateFrom.getValue().toString() + "') >=0 ";
        tabulateData();

    }

    @FXML
    public void setBarangay() throws ParseException {
        barangay = "AND customers.customer_barangay LIKE '%" + txtBarangay.getText() + "%'";
        tabulateData();

    }

    @FXML
    public void setCity() throws ParseException {
        city = " AND customers.customer_city_municipality LIKE '%" + txtCity.getText() + "%' ";
        tabulateData();

    }

    @FXML
    public void setProvince() throws ParseException {
        province = "AND customers.customer_province LIKE '%" + txtProvince.getText() + "%' ";
        tabulateData();

    }

    @FXML
    public void setTimeTo() throws ParseException {
        to = "  AND datediff('" + dateTo.getValue().toString() + "',ifnull(transaction.transaction_date,'0000-00-00')) >=0 ";
        tabulateData();
    }

    public void setAccounts() {
        account = cmbAccounts.getValue().toString();
        tabulateData();
    }

    @FXML
    public void tabulateData() {

        tblCustomer.getItems().clear();
        String res[][] = viewSalesReport(customerName,account, from, to, barangay, city, province, transactionType);
        if (res != null) {
            int i = 1;
            for (String[] re : res) {
                Customer_Transaction_Model entry = new Customer_Transaction_Model(
                        i,//id
                        re[1],//name
                        re[2],//gender
                        re[3],//cellphone
                        re[4],//landline
                        re[5],//street
                        re[6],//barangay
                        re[7],//city
                        re[8],//province
                        re[9],//country
                        Integer.parseInt(re[10]),//b round
                        Integer.parseInt(re[11]),//b slim
                        Integer.parseInt(re[12]),//rn round
                        Integer.parseInt(re[13]),//rn slim
                        Integer.parseInt(re[14]),//rm round
                        Integer.parseInt(re[15]),//rm slim
                        Double.parseDouble(re[16]),//others
                        Double.parseDouble(re[17]),//total
                        Double.parseDouble(re[18]),//amount
                        Double.parseDouble(re[19]),//balance
                        Double.parseDouble(re[20]),//change
                        re[21],//date
                        re[22],//remarks
                        "",//type
                        "",//cashier
                        "", "",//type
                        "",//cashier
                        "");//status
                finalChange += Double.parseDouble(re[23]);
                data.add(entry);
                i++;

            }
            lblDataEntry.setText("Php " + formatNumber.formatNumbers("" + finalChange));
            finalChange = 0;
        }

        List<String> numberOfColumns = new ArrayList<>();
        cmbNumberOfColumns.getItems().remove("");
        numberOfColumns.add("1 - " + tblCustomer.getItems().size());
        cmbNumberOfColumns.setValue(numberOfColumns.get(0));
        int start = 1;
        for (int i = 1; i <= tblCustomer.getItems().size(); i++) {
            if ((i % 21) == 0) {

                start = Integer.parseInt(numberOfColumns.get(numberOfColumns.size() - 1).split("\\s+")[0]);
                numberOfColumns.remove(numberOfColumns.size() - 1);
                numberOfColumns.add(start + " - " + i);
                numberOfColumns.add((i + 1) + " - " + tblCustomer.getItems().size());

            }

        }
        ObservableList col = FXCollections.observableList(numberOfColumns);
        cmbNumberOfColumns.setItems(col);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());
        to = "  AND datediff('" + dateTo.getValue().toString() + "',ifnull(transaction.transaction_date,'0000-00-00')) >=0 ";
        from = " AND datediff(ifnull(transaction.transaction_date,'0000-00-00'), '" + dateFrom.getValue().toString() + "') >=0 ";

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
        tblCCustomerOthers.setCellValueFactory(new PropertyValueFactory<>("CustomerOthers"));
        tblCCustomerTotal.setCellValueFactory(new PropertyValueFactory<>("CustomerTotal"));
        tblCCustomerBalance.setCellValueFactory(new PropertyValueFactory<>("CustomerBalance"));
        tblCCustomerChange.setCellValueFactory(new PropertyValueFactory<>("CustomerChange"));
        tblCCustomerAmount.setCellValueFactory(new PropertyValueFactory<>("CustomerAmount"));
        tblCCustomerDate.setCellValueFactory(new PropertyValueFactory<>("CustomerDate"));
        tblCCustomerRemarks.setCellValueFactory(new PropertyValueFactory<>("CustomerRemarks"));
        tblCustomer.setItems(data);
        tabulateData();

        List<String> list = new ArrayList<>();
        String[] result = getAccountList();
        for (int i = 0; i < result.length; i++) {
            list.add(result[i]);
        }
        ObservableList obList = FXCollections.observableList(list);

        cmbAccounts.setItems(obList);
        cmbAccounts.setValue(result[0]);
        setAccounts();

        print_start = Integer.parseInt(cmbNumberOfColumns.getValue().toString().split("\\s+")[0]);
        print_end = Integer.parseInt(cmbNumberOfColumns.getValue().toString().split("\\s+")[2]);

        //--------------------------------------------------------------------
        address = getAddress("");
        String barangay[] = new String[address.length];
        for (int r = 0; r < address.length; r++) {
            barangay[r] = address[r][1];
        }

        TextFields.bindAutoCompletion(txtBarangay, barangay);
        //---------------------------------------------------------------------
        txtAddressCity = getAddress("cities.city");
        String city[] = new String[txtAddressCity.length];
        for (int r = 0; r < txtAddressCity.length; r++) {
            city[r] = txtAddressCity[r][3];
        }

        TextFields.bindAutoCompletion(txtCity, city);
        //---------------------------------------------------------------------

        txtAddressProvince = getAddress("provinces.province");
        String province[] = new String[txtAddressProvince.length];
        for (int r = 0; r < txtAddressProvince.length; r++) {
            province[r] = txtAddressProvince[r][5];
        }

        TextFields.bindAutoCompletion(txtProvince, province);

        List<String> lists = new ArrayList<>();
        lists.add("Daily");
        lists.add("Monthly");
        lists.add("Yearly");
        ObservableList obLists = FXCollections.observableList(list);

        cmbTransaction.setItems(obLists);
        cmbTransaction.setValue("Daily");
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());

    }

    @FXML
    public void cmbTransactionHandler() {
        if (cmbTransaction.getValue().toString().equalsIgnoreCase("Daily")) {
            transactionType = " ";
        }
        if (cmbTransaction.getValue().toString().equalsIgnoreCase("Monthly")) {
            transactionType = " , MONTH(date) ";
        }
        if (cmbTransaction.getValue().toString().equalsIgnoreCase("Yearly")) {
            transactionType = " , YEAR(date) ";
        }
        tabulateData();
    }

    @FXML
    public void cmbNumberColumnsHandler() {
        print_start = Integer.parseInt(cmbNumberOfColumns.getValue().toString().split("\\s+")[0]);
        print_end = Integer.parseInt(cmbNumberOfColumns.getValue().toString().split("\\s+")[2]);
    }

    @FXML
    public void print() throws IOException {

        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/PrintSalesPreview.fxml"));
        AnchorPane showModal = loader.load();

        modal.setTitle("Print preview");
        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UTILITY);

        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(639);
        modal.setMinHeight(800);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        PrintSalesPreviewController controller = loader.<PrintSalesPreviewController>getController();
        controller.lblFromDate.setText(date(dateFrom.getValue()));
        controller.lbltoDate.setText(date(dateTo.getValue()));

        controller.lblAddress.setText("Address: " + txtBarangay.getText() + " " + txtCity.getText() + " " + txtProvince.getText());

        controller.lblTransactionType.setText(
                "Transaction type: " + cmbTransaction.getValue().toString());

        controller.tblCCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        controller.tblCCustomerBorrowedRound.setCellValueFactory(new PropertyValueFactory<>("CustomerBorrowedRound"));
        controller.tblCCustomerBorrowedSlim.setCellValueFactory(new PropertyValueFactory<>("CustomerBorrowedSlim"));
        controller.tblCCustomerAmount.setCellValueFactory(new PropertyValueFactory<>("CustomerAmount"));
        controller.tblCCustomerTotal.setCellValueFactory(new PropertyValueFactory<>("CustomerTotal"));
        controller.tblCCustomerRemarks.setCellValueFactory(new PropertyValueFactory<>("CustomerRemarks"));
        controller.lblTransactionTotal.setText("Total: " + lblDataEntry.getText());

        for (int i = print_start - 1; i < print_end; i++) {

            Customer_Transaction_Model entry = new Customer_Transaction_Model(
                    0,//id
                    tblCustomer.getItems().get(i).getCustomerName(),//name
                    "",//gender
                    "",//cellphone
                    "",//landline
                    tblCustomer.getItems().get(i).getCustomerAddressStreet(),//street
                    tblCustomer.getItems().get(i).getCustomerAddressBarangay(),//barangay
                    tblCustomer.getItems().get(i).getCustomerAddressCityMunicipality(),//city
                    "",//province
                    "",//country
                    tblCustomer.getItems().get(i).getCustomerBorrowedRound(),//b round
                    tblCustomer.getItems().get(i).getCustomerBorrowedSlim(),//b slim
                    0,//rn round
                    0,//rn slim
                    0,//rm round
                    0,//rm slim
                    0,//others
                    tblCustomer.getItems().get(i).getCustomerTotal(),//total
                    tblCustomer.getItems().get(i).getCustomerAmount(),//amount
                    0,//balance
                    0,//change
                    "",//date
                    tblCustomer.getItems().get(i).getCustomerRemarks()//remarks
                    ,
                     "", "", "", "",//type
                    "",//cashier
                    "");

            controller.data.add(entry);

        }
        controller.tblCustomer.setItems(controller.data);

        modal.showAndWait();

    }

    String date(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        return date.format(formatter);
    }

}
