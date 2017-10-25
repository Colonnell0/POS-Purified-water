/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import libs.FormValidation;
import models.Modal_model;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class ModalController extends Modal_model implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public TextField txtFirstname;

    @FXML
    public TextField txtMI;

    @FXML
    public TextField txtLastname;

    @FXML
    public TextField txtAddressStreet;

    @FXML
    public TextField txtCellphoneNumber;

    @FXML
    public TextField txtLandline;

    @FXML
    public TextField txtCityMunicipality;

    @FXML
    public TextField txtBarangay;

    @FXML
    public TextField txtProvince;

    @FXML
    public TextField txtCountry;

//------------------------------------------------------
    @FXML
    public Label lblFirstname;

    @FXML
    public Label lblMI;

    @FXML
    public Label lblLastname;

    @FXML
    public Label lblAddressStreet;

    @FXML
    public Label lblCellphoneNumber;

    @FXML
    public Label lblLandline;

    @FXML
    public Label lblCityMunicipality;

    @FXML
    public Label lblBarangay;

    @FXML
    public Label lblProvince;

    @FXML
    public Label lblCountry;

    FormValidation formVal = new FormValidation();

//------------------------------------------------------
    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane customerLayout;
//------------------------------------------------------
    @FXML
    private ToggleGroup groupGender;

    @FXML
    RadioButton radioMale;

    @FXML
    RadioButton radioFemale;

    Date dNow = new Date();
    SimpleDateFormat ft
            = new SimpleDateFormat("yyyy/MM/dd");

    @FXML
    public Button btnSubmit;

    private String address[][];
    private String txtAddressCity[][];
    private String txtAddressProvince[][];
    private String txtAddressCountry[][];

    public int customer_id = 0;
    public double customer_balance = 0;
    public int customer_remaining_round = 0;
    public int customer_remaining_slim = 0;

    public String MI = "";

//------------------------------------------------------
    public boolean formCustomerFirstname() {

        boolean tf = false;
        if (formVal.maxLength(txtFirstname.getText().length(), 10)) {
            txtFirstname.setStyle(getBackgroundColor("#023358"));
            lblFirstname.setStyle(getTextFillColor("black"));
            lblFirstname.setText("Firstname");

        }
        if (formVal.minLength(txtFirstname.getText().length(), 2)) {
            txtFirstname.setStyle(getBackgroundColor("#023358"));
            lblFirstname.setStyle(getTextFillColor("black"));

        }
        if (tf = !formVal.maxLength(txtFirstname.getText().length(), 10) || !formVal.minLength(txtFirstname.getText().length(), 2)) {
            txtFirstname.setStyle(getBackgroundColor("#FF3D19"));
            lblFirstname.setStyle(getTextFillColor("#FF3D19"));
        }

        return tf;

    }

    public boolean formCustomerMI() {
        boolean tf = false;
        if (formVal.maxLength(txtMI.getText().length(), 1)) {
            txtMI.setStyle(getBackgroundColor("#023358"));
            lblMI.setStyle(getTextFillColor("black"));

        }

        if (tf = !formVal.maxLength(txtMI.getText().length(), 1)) {
            txtMI.setStyle(getBackgroundColor("#FF3D19"));
            lblMI.setStyle(getTextFillColor("#FF3D19"));
        }
        return tf;
    }

    public boolean formCustomerLastname() {
        boolean tf = false;
        if (formVal.maxLength(txtLastname.getText().length(), 10)) {
            txtLastname.setStyle(getBackgroundColor("#023358"));
            lblLastname.setStyle(getTextFillColor("black"));
            lblLastname.setText("Lastname");

        }
        if (formVal.minLength(txtLastname.getText().length(), 2)) {
            txtLastname.setStyle(getBackgroundColor("#023358"));
            lblLastname.setStyle(getTextFillColor("black"));

        }
        if (tf = !formVal.maxLength(txtLastname.getText().length(), 10) || !formVal.minLength(txtLastname.getText().length(), 2)) {
            txtLastname.setStyle(getBackgroundColor("#FF3D19"));
            lblLastname.setStyle(getTextFillColor("#FF3D19"));
        }
        return tf;
    }

    public boolean forCustomerAddressStreet() {
        boolean tf = false;

        if (formVal.minLength(txtAddressStreet.getText().length(), 3)) {
            txtAddressStreet.setStyle(getBackgroundColorBorderBottom("#023358"));
            lblAddressStreet.setStyle(getTextFillColor("black"));
            lblAddressStreet.setText("Street, road, blk. no.");

        }
        if (tf = !formVal.minLength(txtAddressStreet.getText().length(), 3)) {
            txtAddressStreet.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
            lblAddressStreet.setStyle(getTextFillColor("#FF3D19"));
        }
        return tf;
    }

    public void setTxtAddress() {
        lblCityMunicipality.setText("City / Municipality");
        txtCityMunicipality.setStyle(getBackgroundColorBorderBottom("#023358"));
        lblCityMunicipality.setStyle(getTextFillColor("black"));

        lblProvince.setText("Province");
        txtProvince.setStyle(getBackgroundColorBorderBottom("#023358"));
        lblProvince.setStyle(getTextFillColor("black"));

        lblCountry.setText("Country");
        txtCountry.setStyle(getBackgroundColorBorderBottom("#023358"));
        lblCountry.setStyle(getTextFillColor("black"));

        txtBarangay.setStyle(getBackgroundColorBorderBottom("#023358"));
        lblBarangay.setStyle(getTextFillColor("black"));
        lblBarangay.setText("Barangay");
    }

    public void forCustomerAddressBarangay(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            for (int i = 0; i < address.length; i++) {
                if (txtBarangay.getText().equals(address[i][1])) {
                    if (txtCityMunicipality.getText().isEmpty()) {
                        txtCityMunicipality.setText(address[i][3]);

                    }
                    if (txtProvince.getText().isEmpty()) {
                        txtProvince.setText(address[i][5]);

                    }
                    if (txtCountry.getText().isEmpty()) {
                        txtCountry.setText(address[i][7]);

                    }
                    break;
                }

            }

        }

    }

    public void forCustomerAddressCityMunicipality(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            for (int i = 0; i < txtAddressCity.length; i++) {
                if (txtCityMunicipality.getText().equals(txtAddressCity[i][3])) {
                    if (txtBarangay.getText().isEmpty()) {
                        txtBarangay.setText(txtAddressCity[i][1]);

                    }
                    if (txtProvince.getText().isEmpty()) {
                        txtProvince.setText(txtAddressCity[i][5]);

                    }
                    if (txtCountry.getText().isEmpty()) {
                        txtCountry.setText(txtAddressCity[i][7]);

                    }
                    break;
                }

            }

        }

    }

    public void forCustomerAddressProvince(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            for (int i = 0; i < txtAddressProvince.length; i++) {
                if (txtProvince.getText().equals(txtAddressProvince[i][5])) {
                    if (txtBarangay.getText().isEmpty()) {
                        txtBarangay.setText(txtAddressProvince[i][1]);

                    }
                    if (txtCityMunicipality.getText().isEmpty()) {
                        txtCityMunicipality.setText(txtAddressProvince[i][3]);

                    }
                    if (txtCountry.getText().isEmpty()) {
                        txtCountry.setText(txtAddressProvince[i][7]);

                    }
                    break;
                }

            }

        }
    }

    public void forCustomerAddressCountry(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            for (int i = 0; i < txtAddressCountry.length; i++) {
                if (txtCountry.getText().equals(txtAddressCountry[i][7])) {
                    if (txtBarangay.getText().isEmpty()) {
                        txtBarangay.setText(txtAddressCountry[i][1]);

                    }
                    if (txtCityMunicipality.getText().isEmpty()) {
                        txtCityMunicipality.setText(txtAddressCountry[i][3]);

                    }
                    if (txtProvince.getText().isEmpty()) {
                        txtProvince.setText(txtAddressCountry[i][5]);

                    }
                    break;
                }

            }

        }
    }

    public boolean forCustomerCellphone() {
        boolean tf = false;

        if (formVal.minLength(txtCellphoneNumber.getText().length(), 3)) {
            txtCellphoneNumber.setStyle(getBackgroundColorBorderBottom("#023358"));
            lblCellphoneNumber.setStyle(getTextFillColor("black"));

        }
        if (formVal.maxLength(txtCellphoneNumber.getText().length(), 13)) {
            txtCellphoneNumber.setStyle(getBackgroundColorBorderBottom("#023358"));
            lblCellphoneNumber.setStyle(getTextFillColor("black"));

        }
        if (tf = !formVal.minLength(txtCellphoneNumber.getText().length(), 3) || !formVal.maxLength(txtCellphoneNumber.getText().length(), 13)) {
            txtCellphoneNumber.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
            lblCellphoneNumber.setStyle(getTextFillColor("#FF3D19"));
        }
        if (txtCellphoneNumber.getText().length() == 4) {
            txtCellphoneNumber.appendText(" ");
        }
        if (txtCellphoneNumber.getText().length() == 9) {
            txtCellphoneNumber.appendText(" ");
        }
        return tf;
    }

    public boolean forCustomerLandline() {
        boolean tf = false;

        if (formVal.minLength(txtLandline.getText().length(), 3)) {

            txtLandline.setStyle(getBackgroundColorBorderBottom("#023358"));
            lblLandline.setStyle(getTextFillColor("black"));

        }
        if (formVal.maxLength(txtLandline.getText().length(), 8)) {
            txtLandline.setStyle(getBackgroundColorBorderBottom("#023358"));
            lblLandline.setStyle(getTextFillColor("black"));

        }
        if (tf = !formVal.minLength(txtLandline.getText().length(), 3) || !formVal.maxLength(txtLandline.getText().length(), 8)) {
            txtLandline.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
            lblLandline.setStyle(getTextFillColor("#FF3D19"));
        }
        if (txtLandline.getText().length() == 3) {
            txtLandline.appendText("-");
        }
        return tf;
    }

    public void forRadio() {
        radioMale.setStyle(getTextFillColor("black"));
        radioFemale.setStyle(getTextFillColor("black"));
    }

    public void forCustomerSubmit(KeyEvent event) throws UnknownHostException {
        if (event.getCode() == KeyCode.ENTER && btnSubmit.getText().equalsIgnoreCase("Submit")) {
            if (groupGender.getSelectedToggle() == null) {
                radioMale.setStyle(getTextFillColor("red"));
                radioFemale.setStyle(getTextFillColor("red"));
            }

            if (formVal.empty(txtFirstname.getText())) {
                txtFirstname.setStyle(getBackgroundColor("#FF3D19"));
                lblFirstname.setStyle(getTextFillColor("#FF3D19"));
                lblFirstname.setText("* Firstname");
            }
            if (formVal.empty(txtLastname.getText())) {
                txtLastname.setStyle(getBackgroundColor("#FF3D19"));
                lblLastname.setStyle(getTextFillColor("#FF3D19"));
                lblLastname.setText("* Lastname");
            }
            if (formVal.empty(txtAddressStreet.getText())) {
                txtAddressStreet.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
                lblAddressStreet.setStyle(getTextFillColor("#FF3D19"));
                lblAddressStreet.setText("* Street, road, blk. no.");
            }
            if (formVal.empty(txtBarangay.getText())) {
                txtBarangay.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
                lblBarangay.setStyle(getTextFillColor("#FF3D19"));
                lblBarangay.setText("* Barangay");
            }
            if (formVal.empty(txtCityMunicipality.getText())) {
                txtCityMunicipality.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
                lblCityMunicipality.setStyle(getTextFillColor("#FF3D19"));
                lblCityMunicipality.setText("* City / Municipality");
            }
            if (formVal.empty(txtProvince.getText())) {
                txtProvince.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
                lblProvince.setStyle(getTextFillColor("#FF3D19"));
                lblProvince.setText("* Province");
            }
            if (formVal.empty(txtCountry.getText())) {
                txtCountry.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
                lblCountry.setStyle(getTextFillColor("#FF3D19"));
                lblCountry.setText("* Country");
            }
            if (!formVal.empty(txtFirstname.getText())
                    && !formVal.empty(txtLastname.getText())
                    && !formVal.empty(txtAddressStreet.getText())
                    && !formVal.empty(txtBarangay.getText())
                    && !formVal.empty(txtCityMunicipality.getText())
                    && !formVal.empty(txtProvince.getText())
                    && !formVal.empty(txtCountry.getText())
                    && txtMI.getText().length() <= 1
                    && !forCustomerAddressStreet()
                    // && forCustomerCellphone()
                    && groupGender.getSelectedToggle() != null //  && forCustomerLandline()
                    ) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation message");
                alert.setContentText("Successfully registered!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 15px;\n"
                        + "   -fx-font-weight: bold;");

                if (!txtMI.getText().isEmpty()) {
                    MI = txtMI.getText().substring(0, 1).toUpperCase() + txtMI.getText().substring(1);
                }
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    String column[] = {"customer_firstname", "customer_middle_initial", "customer_lastname", "customer_gender", "customer_cellphone_no", "customer_landline", "customer_street", "customer_barangay", "customer_city_municipality", "customer_province", "customer_country", "customer_date", "customer_status", "account_name"};
                    String values[] = {txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), MI, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1), groupGender.getSelectedToggle().getUserData().toString(), txtCellphoneNumber.getText(), txtLandline.getText(), txtAddressStreet.getText().substring(0, 1).toUpperCase() + txtAddressStreet.getText().substring(1), txtBarangay.getText().substring(0, 1).toUpperCase() + txtBarangay.getText().substring(1), txtCityMunicipality.getText().substring(0, 1).toUpperCase() + txtCityMunicipality.getText().substring(1), txtProvince.getText().substring(0, 1).toUpperCase() + txtProvince.getText().substring(1), txtCountry.getText().substring(0, 1).toUpperCase() + txtCountry.getText().substring(1), ft.format(dNow), "", setLoginDetails()[1]};
                    setInsertCustomer("customers", column, values);
                    Stage stage = (Stage) txtFirstname.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }

            /* if (formCustomerFirstname()
                || formCustomerLastname()
                || formCustomerMI()
                || forCustomerAddressStreet()
                || forCustomerAddressBarangay()
                || forCustomerAddressCityMunicipality()
                || forCustomerAddressProvince()
                || forCustomerAddressCountry()
                && forCustomerCellphone()
                && forCustomerLandline()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning message");
            alert.setContentText("* Please fill up important fields");
            Optional<ButtonType> result = alert.showAndWait();

        }*/
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            Stage stage = (Stage) txtFirstname.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
    }

    public void forCustomerSubmitBtn() throws UnknownHostException {

        if (groupGender.getSelectedToggle() == null) {
            radioMale.setStyle(getTextFillColor("red"));
            radioFemale.setStyle(getTextFillColor("red"));
        }

        if (formVal.empty(txtFirstname.getText())) {
            txtFirstname.setStyle(getBackgroundColor("#FF3D19"));
            lblFirstname.setStyle(getTextFillColor("#FF3D19"));
            lblFirstname.setText("* Firstname");
        }
        if (formVal.empty(txtLastname.getText())) {
            txtLastname.setStyle(getBackgroundColor("#FF3D19"));
            lblLastname.setStyle(getTextFillColor("#FF3D19"));
            lblLastname.setText("* Lastname");
        }
        if (formVal.empty(txtAddressStreet.getText())) {
            txtAddressStreet.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
            lblAddressStreet.setStyle(getTextFillColor("#FF3D19"));
            lblAddressStreet.setText("* Street, road, blk. no.");
        }
        if (formVal.empty(txtBarangay.getText())) {
            txtBarangay.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
            lblBarangay.setStyle(getTextFillColor("#FF3D19"));
            lblBarangay.setText("* Barangay");
        }
        if (formVal.empty(txtCityMunicipality.getText())) {
            txtCityMunicipality.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
            lblCityMunicipality.setStyle(getTextFillColor("#FF3D19"));
            lblCityMunicipality.setText("* City / Municipality");
        }
        if (formVal.empty(txtProvince.getText())) {
            txtProvince.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
            lblProvince.setStyle(getTextFillColor("#FF3D19"));
            lblProvince.setText("* Province");
        }
        if (formVal.empty(txtCountry.getText())) {
            txtCountry.setStyle(getBackgroundColorBorderBottom("#FF3D19"));
            lblCountry.setStyle(getTextFillColor("#FF3D19"));
            lblCountry.setText("* Country");
        }
        if (!formVal.empty(txtFirstname.getText())
                && !formVal.empty(txtLastname.getText())
                && !formVal.empty(txtAddressStreet.getText())
                && !formVal.empty(txtBarangay.getText())
                && !formVal.empty(txtCityMunicipality.getText())
                && !formVal.empty(txtProvince.getText())
                && !formVal.empty(txtCountry.getText())
                && txtMI.getText().length() <= 1
                && !forCustomerAddressStreet()
                // && forCustomerCellphone()
                && groupGender.getSelectedToggle() != null //  && forCustomerLandline()
                ) {
            if (btnSubmit.getText().equalsIgnoreCase("Submit")) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation message");
                alert.setContentText("Successfully registered!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 15px;\n"
                        + "   -fx-font-weight: bold;");
                if (!txtMI.getText().isEmpty()) {
                    MI = txtMI.getText().substring(0, 1).toUpperCase() + txtMI.getText().substring(1);
                }
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    String column[] = {"customer_firstname", "customer_middle_initial", "customer_lastname", "customer_gender", "customer_cellphone_no", "customer_landline", "customer_street", "customer_barangay", "customer_city_municipality", "customer_province", "customer_country", "customer_date", "customer_status", "account_name"};
                    String values[] = {txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), MI, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1), groupGender.getSelectedToggle().getUserData().toString(), txtCellphoneNumber.getText(), txtLandline.getText(), txtAddressStreet.getText().substring(0, 1).toUpperCase() + txtAddressStreet.getText().substring(1), txtBarangay.getText().substring(0, 1).toUpperCase() + txtBarangay.getText().substring(1), txtCityMunicipality.getText().substring(0, 1).toUpperCase() + txtCityMunicipality.getText().substring(1), txtProvince.getText().substring(0, 1).toUpperCase() + txtProvince.getText().substring(1), txtCountry.getText().substring(0, 1).toUpperCase() + txtCountry.getText().substring(1), ft.format(dNow), "", setLoginDetails()[1]};
                    setInsertCustomer("customers", column, values);
                    Stage stage = (Stage) txtFirstname.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
            if (btnSubmit.getText().equalsIgnoreCase("Update")) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(null);
                alert.setContentText("Are you sure you want to update this customer?");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                if (!txtMI.getText().isEmpty()) {
                    MI = txtMI.getText().substring(0, 1).toUpperCase() + txtMI.getText().substring(1);
                }
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    String column[] = {"customer_firstname", "customer_middle_initial", "customer_lastname", "customer_gender", "customer_cellphone_no", "customer_landline", "customer_street", "customer_barangay", "customer_city_municipality", "customer_province", "customer_country"};
                    String values[] = {txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), MI, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1), groupGender.getSelectedToggle().getUserData().toString(), txtCellphoneNumber.getText(), txtLandline.getText(), txtAddressStreet.getText().substring(0, 1).toUpperCase() + txtAddressStreet.getText().substring(1), txtBarangay.getText().substring(0, 1).toUpperCase() + txtBarangay.getText().substring(1), txtCityMunicipality.getText().substring(0, 1).toUpperCase() + txtCityMunicipality.getText().substring(1), txtProvince.getText().substring(0, 1).toUpperCase() + txtProvince.getText().substring(1), txtCountry.getText().substring(0, 1).toUpperCase() + txtCountry.getText().substring(1)};
                    setUpdateCustomer(column, values, "" + customer_id);
                    Stage stage = (Stage) txtFirstname.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
            if (btnSubmit.getText().equalsIgnoreCase("Delete")) {
                if (customer_balance <= 0 & customer_remaining_round <= 0 & customer_remaining_slim <= 0) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(null);
                    alert.setContentText("Are you sure you want to delete this customer?");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setStyle(" -fx-font-size: 12px;\n"
                            + "   -fx-font-weight: bold;");
                    if (!txtMI.getText().isEmpty()) {
                        MI = txtMI.getText().substring(0, 1).toUpperCase() + txtMI.getText().substring(1);
                    }
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {

                        String column[] = {"customer_firstname", "customer_middle_initial", "customer_lastname", "customer_gender", "customer_cellphone_no", "customer_landline", "customer_street", "customer_barangay", "customer_city_municipality", "customer_province", "customer_country", "customer_status", "customer_date"};
                        String values[] = {txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), MI, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1), groupGender.getSelectedToggle().getUserData().toString(), txtCellphoneNumber.getText(), txtLandline.getText(), txtAddressStreet.getText().substring(0, 1).toUpperCase() + txtAddressStreet.getText().substring(1), txtBarangay.getText().substring(0, 1).toUpperCase() + txtBarangay.getText().substring(1), txtCityMunicipality.getText().substring(0, 1).toUpperCase() + txtCityMunicipality.getText().substring(1), txtProvince.getText().substring(0, 1).toUpperCase() + txtProvince.getText().substring(1), txtCountry.getText().substring(0, 1).toUpperCase() + txtCountry.getText().substring(1), "Deleted", ft.format(dNow)};
                        setUpdateCustomer(column, values, "" + customer_id);
                        Stage stage = (Stage) txtFirstname.getScene().getWindow();
                        // do what you have to do
                        stage.close();
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setContentText("You cannot delete this customer?\n\nRemaining\n\t balance: " + customer_balance + " \n\tRound: " + customer_remaining_round + "  \n\tSlim: " + customer_remaining_slim);
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setStyle(" -fx-font-size: 12px;\n"
                            + "   -fx-font-weight: bold;");
                    alert.showAndWait();

                }
            }
        }

        /* if (formCustomerFirstname()
                || formCustomerLastname()
                || formCustomerMI()
                || forCustomerAddressStreet()
                || forCustomerAddressBarangay()
                || forCustomerAddressCityMunicipality()
                || forCustomerAddressProvince()
                || forCustomerAddressCountry()
                && forCustomerCellphone()
                && forCustomerLandline()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning message");
            alert.setContentText("* Please fill up important fields");
            Optional<ButtonType> result = alert.showAndWait();

        }*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        radioMale.setUserData("Male");
        radioFemale.setUserData("Female");
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

        TextFields.bindAutoCompletion(txtCityMunicipality, city);
//---------------------------------------------------------------------

        txtAddressProvince = getAddress("provinces.province");
        String province[] = new String[txtAddressProvince.length];
        for (int r = 0; r < txtAddressProvince.length; r++) {
            province[r] = txtAddressProvince[r][5];
        }

        TextFields.bindAutoCompletion(txtProvince, province);
//---------------------------------------------------------------------

        txtAddressCountry = getAddress("countries.country");
        String country[] = new String[txtAddressCountry.length];
        for (int r = 0; r < txtAddressCountry.length; r++) {
            country[r] = txtAddressCountry[r][7];
        }

        TextFields.bindAutoCompletion(txtCountry, country);
//---------------------------------------------------------------------

    }

}
