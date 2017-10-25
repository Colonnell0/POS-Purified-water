/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
import libs.Hash;
import models.Dashboard_Model;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class AddAccountController extends Dashboard_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public ComboBox cmbAccountStatus;

    @FXML
    public TextField txtFirstname;

    @FXML
    public TextField txtMI;

    @FXML
    public TextField txtLastname;

    @FXML
    public TextField txtStreet;

    @FXML
    public TextField txtBarangay;

    @FXML
    public TextField txtCity;

    @FXML
    public TextField txtProvince;

    @FXML
    public ToggleGroup groupGender;

    @FXML
    public RadioButton radioMale;

    @FXML
    public RadioButton radioFemale;

    @FXML
    public TextField txtContact;

    @FXML
    public ComboBox cmbAccountType;

    @FXML
    public TextField txtUsername;

    @FXML
    public TextField txtPassword;

    @FXML
    public AnchorPane pane;

    public String[][] typeArray;

    @FXML
    public Label lblTitle;

    @FXML
    public Button btnSave;

    @FXML
    public Label lblFirstname;

    @FXML
    public Label lblMI;

    @FXML
    public Label lblLastname;

    @FXML
    public Label lblStreet;

    @FXML
    public Label lblBarangay;

    @FXML
    public Label lblCity;

    @FXML
    public Label lblProvince;

    @FXML
    public Label lblGender;

    @FXML
    public Label lblUsername;

    @FXML
    public Label lblContact;

    private String MI = "";

    public int account_type_id = 0;
    public int account_id = 0;
    private String password = "";

    Hash hash = new Hash();
    FormValidation formVal = new FormValidation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //--------------------------------------------------------------------
        String address[][] = getAddress("");
        String barangay[] = new String[address.length];
        for (int r = 0; r < address.length; r++) {
            barangay[r] = address[r][1];
        }

        TextFields.bindAutoCompletion(txtBarangay, barangay);
//---------------------------------------------------------------------
        String[][] txtAddressCity = getAddress("cities.city");
        String city[] = new String[txtAddressCity.length];
        for (int r = 0; r < txtAddressCity.length; r++) {
            city[r] = txtAddressCity[r][3];
        }

        TextFields.bindAutoCompletion(txtCity, city);
//---------------------------------------------------------------------

        String[][] txtAddressProvince = getAddress("provinces.province");
        String province[] = new String[txtAddressProvince.length];
        for (int r = 0; r < txtAddressProvince.length; r++) {
            province[r] = txtAddressProvince[r][5];
        }

        TextFields.bindAutoCompletion(txtProvince, province);
//---------------------------------------------------------------------
        List<String> listStatus = new ArrayList<>();
        listStatus.add("Enable");
        listStatus.add("Disable");
        ObservableList status = FXCollections.observableList(listStatus);
        cmbAccountStatus.setItems(status);
        cmbAccountStatus.setValue("Enable");

        List<String> listType = new ArrayList<>();
        String result[][] = getAccountType();
        typeArray = new String[result.length][2];

        for (int i = 0; i < result.length; i++) {
            listType.add(result[i][1]);
            typeArray[i][0] = result[i][0];
            typeArray[i][1] = result[i][1];
        }

        ObservableList type = FXCollections.observableList(listType);
        cmbAccountType.setItems(type);
        cmbAccountType.setValue("Cashier");
        radioMale.setUserData("Male");
        radioFemale.setUserData("Female");

    }

    public void setViewClose(KeyEvent key) throws Exception {

        if (key.getCode() == KeyCode.ESCAPE) {
            Stage stage = (Stage) pane.getScene().getWindow();
            // do what you have to do
            stage.close();
        }

    }

    public void setViewCloseClick() throws Exception {

        Stage stage = (Stage) pane.getScene().getWindow();
        // do what you have to do
        stage.close();

    }

    public void submit() throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (firstname()
                & mi()
                & lastname()
                & street()
                & barangay()
                & city()
                & province()
                & gender()
                & contact()
                & username()
                & password() & btnSave.getText().equalsIgnoreCase("Submit")) {

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat datetime
                    = new java.text.SimpleDateFormat("yyyy-MM-dd");
            int typeID = 0;
            for (int i = 0; i < cmbAccountType.getItems().size(); i++) {

                if (typeArray[i][1].equalsIgnoreCase(cmbAccountType.getValue().toString())) {
                    typeID = Integer.parseInt(typeArray[i][0]);

                    break;
                }

            }
            if (!txtMI.getText().isEmpty()) {
                MI = txtMI.getText().substring(0, 1).toUpperCase() + txtMI.getText().substring(1);
            }
            String[] column = {"accounts.account_type_id", "accounts.account_username", "accounts.account_password", "accounts.account_firstname", "accounts.account_middle_initial", "accounts.account_lastname", "accounts.account_gender", "accounts.account_street", "accounts.account_barangay", "accounts.account_city_municipality", "accounts.account_province", "accounts.account_contact", "accounts.account_status", "account_date","account_key"};
            String[] values = {"" + typeID, txtUsername.getText(), hash.hash(txtPassword.getText()), txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), MI, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1) + " ", groupGender.getSelectedToggle().getUserData().toString(), txtStreet.getText().substring(0, 1).toUpperCase() + txtStreet.getText().substring(1), txtBarangay.getText(), txtCity.getText(), txtProvince.getText(), txtContact.getText(), cmbAccountStatus.getValue().toString(), datetime.format(dt),"FMbJpSusGJYCxydXRezxjQ== - v99xUxa77O - fZqnJ7sWV0 - X98RXrsDTZ - oIftz76VYT - 7ZBt80ioNO - BjQzR5DTnA"};
            setInsetAccount("accounts", column, values);
            Stage stage = (Stage) pane.getScene().getWindow();
            // do what you have to do
            stage.close();

        }
        if (btnSave.getText().equalsIgnoreCase("Delete")) {
            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat datetime
                    = new java.text.SimpleDateFormat("yyyy-MM-dd");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account info");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this account?\n\n" + "Position: " + cmbAccountType.getValue().toString() + "\nName: " + txtFirstname.getText() + " " + txtMI.getText() + ". " + txtLastname.getText());
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            if (!txtMI.getText().isEmpty()) {
                MI = txtMI.getText().substring(0, 1).toUpperCase() + txtMI.getText().substring(1);
            }

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) pane.getScene().getWindow();
                // do what you have to do
                String[] column = {"accounts.account_type_id", "accounts.account_username", "accounts.account_firstname", "accounts.account_middle_initial", "accounts.account_lastname", "accounts.account_gender", "accounts.account_street", "accounts.account_barangay", "accounts.account_city_municipality", "accounts.account_province", "accounts.account_contact", "accounts.account_status", "accounts.account_date"};
                String[] values = {"" + account_type_id, txtUsername.getText(), txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), MI, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1) + " ", groupGender.getSelectedToggle().getUserData().toString(), txtStreet.getText().substring(0, 1).toUpperCase() + txtStreet.getText().substring(1), txtBarangay.getText(), txtCity.getText(), txtProvince.getText(), txtContact.getText(), "Deleted", datetime.format(dt)};
                updateAccount(column, values, "" + account_id);

                stage.close();

            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }

        if (btnSave.getText().equalsIgnoreCase("Update") & firstname()
                & mi()
                & lastname()
                & street()
                & barangay()
                & city()
                & province()
                & gender()
                & contact()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Account info");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update this account?\n\n" + "Position: " + cmbAccountType.getValue().toString() + "\nName: " + txtFirstname.getText() + " " + txtMI.getText() + ". " + txtLastname.getText());
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            if (!txtMI.getText().isEmpty()) {
                MI = txtMI.getText().substring(0, 1).toUpperCase() + txtMI.getText().substring(1);
            }
            int typeID = 0;
            for (int i = 0; i < cmbAccountType.getItems().size(); i++) {

                if (typeArray[i][1].equalsIgnoreCase(cmbAccountType.getValue().toString())) {
                    typeID = Integer.parseInt(typeArray[i][0]);

                    break;
                }

            }

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (!txtPassword.getText().isEmpty()) {
                    String[] column = {"accounts.account_type_id", "accounts.account_username", "accounts.account_password", "accounts.account_firstname", "accounts.account_middle_initial", "accounts.account_lastname", "accounts.account_gender", "accounts.account_street", "accounts.account_barangay", "accounts.account_city_municipality", "accounts.account_province", "accounts.account_contact", "accounts.account_status"};
                    String[] values = {"" + typeID, txtUsername.getText(), hash.hash(txtPassword.getText()), txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), MI, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1) + " ", groupGender.getSelectedToggle().getUserData().toString(), txtStreet.getText().substring(0, 1).toUpperCase() + txtStreet.getText().substring(1), txtBarangay.getText(), txtCity.getText(), txtProvince.getText(), txtContact.getText(), cmbAccountStatus.getValue().toString()};
                    updateAccount(column, values, "" + account_id);
                    Stage stage = (Stage) pane.getScene().getWindow();
                    // do what you have to do
                    stage.close();

                }
                if (txtPassword.getText().isEmpty()) {
                    String[] column = {"accounts.account_type_id", "accounts.account_username", "accounts.account_firstname", "accounts.account_middle_initial", "accounts.account_lastname", "accounts.account_gender", "accounts.account_street", "accounts.account_barangay", "accounts.account_city_municipality", "accounts.account_province", "accounts.account_contact", "accounts.account_status"};
                    String[] values = {"" + typeID, txtUsername.getText(), txtFirstname.getText().substring(0, 1).toUpperCase() + txtFirstname.getText().substring(1), MI, txtLastname.getText().substring(0, 1).toUpperCase() + txtLastname.getText().substring(1) + " ", groupGender.getSelectedToggle().getUserData().toString(), txtStreet.getText().substring(0, 1).toUpperCase() + txtStreet.getText().substring(1), txtBarangay.getText(), txtCity.getText(), txtProvince.getText(), txtContact.getText(), cmbAccountStatus.getValue().toString()};
                    updateAccount(column, values, "" + account_id);
                    Stage stage = (Stage) pane.getScene().getWindow();
                    // do what you have to do
                    stage.close();

                }

            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }

    }

    //--------------------------------------------------------------------------
    public boolean firstname() {
        boolean tf = false;
        if (formVal.minLength(txtFirstname.getText().length(), 3)) {
            txtFirstname.setStyle(getTextFillColor("black"));
            lblFirstname.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            txtFirstname.setStyle(getTextFillColor("red"));
            lblFirstname.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean mi() {
        boolean tf = false;
        if (formVal.maxLength(txtMI.getText().length(), 1)) {
            txtMI.setStyle(getTextFillColor("black"));
            lblMI.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            txtMI.setStyle(getTextFillColor("red"));
            lblMI.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean lastname() {
        boolean tf = false;
        if (formVal.minLength(txtLastname.getText().length(), 3)) {
            txtLastname.setStyle(getTextFillColor("black"));
            lblLastname.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            txtLastname.setStyle(getTextFillColor("red"));
            lblLastname.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean street() {
        boolean tf = false;
        if (formVal.minLength(txtStreet.getText().length(), 3)) {
            txtStreet.setStyle(getTextFillColor("black"));
            lblStreet.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            txtStreet.setStyle(getTextFillColor("red"));
            lblStreet.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean barangay() {
        boolean tf = false;
        if (formVal.minLength(txtBarangay.getText().length(), 3)) {
            txtBarangay.setStyle(getTextFillColor("black"));
            lblBarangay.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            txtBarangay.setStyle(getTextFillColor("red"));
            lblBarangay.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean city() {
        boolean tf = false;
        if (formVal.minLength(txtCity.getText().length(), 3)) {
            txtCity.setStyle(getTextFillColor("black"));
            lblCity.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            txtCity.setStyle(getTextFillColor("red"));
            lblCity.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean province() {
        boolean tf = false;
        if (formVal.minLength(txtProvince.getText().length(), 3)) {
            txtProvince.setStyle(getTextFillColor("black"));
            lblProvince.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            txtProvince.setStyle(getTextFillColor("red"));
            lblProvince.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean gender() {
        boolean tf = false;
        if (radioMale.isSelected() | radioFemale.isSelected()) {
            radioMale.setStyle(getTextFillColor("black"));
            radioFemale.setStyle(getTextFillColor("black"));
            lblGender.setText("");
            lblGender.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            radioMale.setStyle(getTextFillColor("red"));
            radioFemale.setStyle(getTextFillColor("red"));
            lblGender.setText("*");
            lblGender.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean contact() {
        boolean tf = false;
        if (!formVal.empty(txtContact.getText())) {
            txtContact.setStyle(getTextFillColor("black"));
            lblContact.setText("");
            lblContact.setStyle(getTextFillColor("black"));
            tf = true;
        } else {

            txtContact.setStyle(getTextFillColor("red"));
            lblContact.setText("*");
            lblContact.setStyle(getTextFillColor("red"));

            tf = false;
        }
        return tf;
    }

    public boolean username() {
        boolean tf = false;

        if (formVal.minLength(txtUsername.getText().length(), 3) & formVal.maxLength(txtUsername.getText().length(), 12)) {

            if (getUsername(txtUsername.getText()).length() > 0) {
                txtUsername.setStyle(getTextFillColor("red"));
                lblUsername.setText("already taken");
                lblUsername.setStyle(getTextFillColor("red"));
                tf = false;
            } else {
                txtUsername.setStyle(getTextFillColor("black"));
                lblUsername.setText(" ");
                lblUsername.setStyle(getTextFillColor("black"));
                tf = true;
            }
        } else {
            txtUsername.setStyle(getTextFillColor("red"));
            lblUsername.setText("*");
            lblUsername.setStyle(getTextFillColor("red"));
            tf = false;
        }
        return tf;
    }

    public boolean password() {
        boolean tf = false;
        if (formVal.minLength(txtPassword.getText().length(), 3) & formVal.maxLength(txtPassword.getText().length(), 15)) {

            txtPassword.setStyle(getTextFillColor("black"));
            lblUsername.setText(" ");
            lblUsername.setStyle(getTextFillColor("black"));
            tf = true;

        } else {
            txtPassword.setStyle(getTextFillColor("red"));
            lblUsername.setText("*");
            lblUsername.setStyle(getTextFillColor("red"));
            tf = false;
        }
        return tf;
    }

    /**
     *
     * @param border
     * @param background
     * @return
     */
    protected String getTextFillColor(String color) {
        return " -fx-text-fill:" + color + ";";
    }

}
