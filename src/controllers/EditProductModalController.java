/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.EditProduct_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class EditProductModalController extends EditProduct_Model implements Initializable {

    @FXML
    public TextField txtProductCode;

    @FXML
    public TextField txtProductName;

    @FXML
    public TextField txtProductPrice;

    @FXML
    public ComboBox cmbStatus;

    @FXML
    public DatePicker dtpDate;

    @FXML
    public Button btnUpdateDelete;

    @FXML
    public AnchorPane pane;

    public List<String> Status = new ArrayList<>();

    public void setViewClose(KeyEvent key) throws Exception {

        if (key.getCode() == KeyCode.ESCAPE) {
            Stage stage = (Stage) txtProductCode.getScene().getWindow();
            // do what you have to do
            stage.close();
        }

    }

    public void setViewCloseClick() throws Exception {

      
            Stage stage = (Stage) txtProductCode.getScene().getWindow();
            // do what you have to do
            stage.close();
        

    }

    public void submit() {

        if (btnUpdateDelete.getText().equalsIgnoreCase("Update")) {
            if (!txtProductName.getText().isEmpty() && !txtProductPrice.getText().isEmpty() && !cmbStatus.getValue().toString().isEmpty() && !dtpDate.getValue().toString().isEmpty()) {

                double amount = Double.parseDouble(txtProductPrice.getText());
                String column[] = {"product_id", "product_name", "product_price", "product_status", "product_date"};
                String values[] = {txtProductCode.getText(), txtProductName.getText().substring(0, 1).toUpperCase() + txtProductName.getText().substring(1), "" + amount, cmbStatus.getValue().toString(), dtpDate.getValue().toString()};

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Product info");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update this product?\n\n" + txtProductCode.getText() + " " + txtProductName.getText().substring(0, 1).toUpperCase() + txtProductName.getText().substring(1) + "\n" + "Product price: " + amount);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    updateProduct(column, values, txtProductCode.getText());
                    Stage stage = (Stage) pane.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }

            }
        }
        if (btnUpdateDelete.getText().equalsIgnoreCase("Delete")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product info");
            alert.setHeaderText(null);
            if (txtProductCode.getText().equalsIgnoreCase("96901344") || txtProductCode.getText().equalsIgnoreCase("924447151")) {
                alert.setContentText("You cannot delete this product!\n\n" + txtProductCode.getText() + " " + txtProductName.getText().substring(0, 1).toUpperCase() + txtProductName.getText().substring(1) + "\n" + "Product price: " + txtProductPrice.getText());
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            } else {
                alert.setContentText("Are you sure you want to delete this product?\n\n" + txtProductCode.getText() + " " + txtProductName.getText().substring(0, 1).toUpperCase() + txtProductName.getText().substring(1) + "\n" + "Product price: " + txtProductPrice.getText());
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    setDeleteProduct("products", "product_id", txtProductCode.getText());
                    Stage stage = (Stage) pane.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }

        }
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
    }

}
