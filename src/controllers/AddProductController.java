/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import libs.FormValidation;
import libs.FormatNumber;
import models.EditProduct_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class AddProductController extends EditProduct_Model implements Initializable {

    @FXML
    private TextField txtProductCode;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtProductPrice;

    @FXML
    private ComboBox cmbStatus;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private AnchorPane pane;

    private List<String> Status = new ArrayList<>();

    FormValidation formVal = new FormValidation();

    FormatNumber formatNumber = new FormatNumber();
    Date dNow = new Date();
    SimpleDateFormat ft
            = new SimpleDateFormat("yyyy/MM/dd");

    public void setViewClose(KeyEvent key) throws Exception {

        if (key.getCode() == KeyCode.ESCAPE) {
            Stage stage = (Stage) txtProductCode.getScene().getWindow();
            // do what you have to do
            stage.close();
        }

    }

    public void submit() {
        if (!txtProductName.getText().isEmpty() && !txtProductPrice.getText().isEmpty() && !cmbStatus.getValue().toString().isEmpty() && !dtpDate.getValue().toString().isEmpty()) {

            double amount = Double.parseDouble(txtProductPrice.getText());
            String column[] = {"product_id", "product_name", "product_price", "product_status", "product_date"};
            String values[] = {txtProductCode.getText(), txtProductName.getText().substring(0, 1).toUpperCase() + txtProductName.getText().substring(1)+" ", "" + amount, cmbStatus.getValue().toString(),dtpDate.getValue().toString()};

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Product price");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to add this product?\n\n" + txtProductCode.getText() + " " + txtProductName.getText().substring(0, 1).toUpperCase() + txtProductName.getText().substring(1) + "\n" + "Product price: " + amount);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                setProduct("products", column, values);
                Stage stage = (Stage) pane.getScene().getWindow();
                // do what you have to do
                stage.close();
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }
    }
      public void setViewCloseClick() throws Exception {

      
            Stage stage = (Stage) txtProductCode.getScene().getWindow();
            // do what you have to do
            stage.close();
        

    }


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        txtProductCode.setText("" + getRandomNum(999999999, "product_code", "products", "product_id"));
        txtProductCode.setStyle("-fx-font: bold  11pt \"Arial\";");
        Status.add("Enable");
        Status.add("Disable");
        ObservableList data = FXCollections.observableList(Status);
        cmbStatus.setItems(data);
        cmbStatus.setValue("Enable");
        dtpDate.setValue(LocalDate.now());
    }

}
