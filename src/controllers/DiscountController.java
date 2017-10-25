/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import libs.FormatNumber;
import models.Dashboard_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class DiscountController extends Dashboard_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    FormatNumber formatNumber = new FormatNumber();
    Date dNow = new Date();
    SimpleDateFormat ft
            = new SimpleDateFormat("yyyy/MM/dd");

    @FXML
    public Label lblName;

    @FXML
    private AnchorPane pane;

    @FXML
    public Label lblAddress;

    @FXML
    public TextField txtProductName;

    @FXML
    public TextField txtNumOfProduct;

    @FXML
    public TextField txtUnitPrice;

    @FXML
    public TextField txtDiscountPrice;

    @FXML
    private Label lblCatch;

    @FXML
    public Button btnDiscount;

    public int customer_id = 0;

    String result = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void setTotalPrice() {

        txtUnitPrice.setText("" + (Double.parseDouble(result) * Double.parseDouble(txtNumOfProduct.getText())));
    }

    public boolean getProductName() {
        boolean tf = false;

        String[] splited = txtProductName.getText().split("\\s+");

        tf = getDiscountedCustomer("" + customer_id, splited[0]);
        if (tf) {
            lblCatch.setText("already set");
        } else {
            lblCatch.setText(" ");
        }

        result = getUnitPrice(splited[0]);

        txtUnitPrice.setText(result);

        return tf;

    }

    public void submit(ActionEvent event) {

        if (!txtProductName.getText().isEmpty() & !txtNumOfProduct.getText().isEmpty() & !txtUnitPrice.getText().isEmpty() & btnDiscount.getText().equalsIgnoreCase("Submit")) {
            if (!getProductName()) {
                String[] splited = txtProductName.getText().split("\\s+");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("Successfully set!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
                String column[] = {"customer_id", "product_id", "number_product", "discount", "discount_date", "discount_status"};
                String values[] = {"" + customer_id, splited[0], "" + Integer.parseInt(txtNumOfProduct.getText()), "" + Double.parseDouble(txtDiscountPrice.getText()), ft.format(dNow), " "};
                setDiscount("discounts", column, values);
                Stage stage = (Stage) pane.getScene().getWindow();
                // do what you have to do
                stage.close();
            }

        }
        if (!txtDiscountPrice.getText().isEmpty() & !txtNumOfProduct.getText().isEmpty() & btnDiscount.getText().equalsIgnoreCase("Update")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account info");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update this customer?\n\n" + "Name: " + lblName.getText() + "\nProduct: " + txtProductName.getText() + "\nNum. of Product: " + txtNumOfProduct.getText() + "\nDiscount:  ( ₱ ): " + txtDiscountPrice.getText());
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) pane.getScene().getWindow();
                // do what you have to do
                String[] column = {"number_product", "discount"};
                String[] values = {txtNumOfProduct.getText(), txtDiscountPrice.getText()};
                updateDiscount(column, values, "" + customer_id);

                stage.close();

            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }
        if (btnDiscount.getText().equalsIgnoreCase("Delete")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account info");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this customer?\n\n" + "Name: " + lblName.getText() + "\nProduct: " + txtProductName.getText() + "\nNum. of Product: " + txtNumOfProduct.getText() + "\nDiscount:  ( ₱ ): " + txtDiscountPrice.getText());
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) pane.getScene().getWindow();
                // do what you have to do
                String[] column = {"discount_status"};
                String[] values = {"Deleted"};
                updateDiscount(column, values, "" + customer_id);

                stage.close();

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    public void setViewClose() throws Exception {

        Stage stage = (Stage) pane.getScene().getWindow();
        // do what you have to do
        stage.close();

    }

}
