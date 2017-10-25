/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.CustomerController.modal;
import static controllers.Main.stage;
import static controllers.PAYController.value;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class CustomerDeliveryController extends Customer {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label productName;

    @FXML
    private TextField quantity;

    @FXML
    private TextArea displayAllPrice;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtRound;

    @FXML
    private TextField txtSlim;

    @FXML
    private TextArea txtNote;

    private static String delivery;
    private static String res[][];
    private String additionalRes[][];

    private double amount;
    private double total;
    private double balance;

    private String note = "N/A";

    private int counter = 0;

    public static String customerDelivery(String source, String title, int w, int h) throws IOException {
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

    public void setProductName() {
        String discount = "";
        if (counter == 0) {
            if (!modal.getTitle().isEmpty()) {
                res = getCustomerTransaction(modal.getTitle(), "unconfirm");

                if (!res[0][11].equalsIgnoreCase("0000-00-00")) {
                    Text txt = new Text();
                    discount = " ( Discount )";
                }

                if (!getCustomerBalanceCount(res[0][0], modal.getTitle()).equalsIgnoreCase("0")) {
                    additionalRes = getCustomerBalance(res[0][0], modal.getTitle());

                    balance = Double.parseDouble(additionalRes[0][4]);
                    if (balance <= 0) {
                        balance = 0;
                    }

                }

                displayAllPrice.setText(" " + res[0][7] + " : " + res[0][8] + "\n----------------------------------------------------------------\n");
                System.out.println(balance+"--------------------------------------<");
                String[] transactionProduct = res[0][9].split(",");
                for (int i = 0; i < transactionProduct.length; i++) {
                    displayAllPrice.appendText(transactionProduct[i] + "\n");
                }

                displayAllPrice.appendText("----------------------------------------------------------------\n");

                displayAllPrice.appendText("  Subtotal:  " + res[0][5] + "\n");
                displayAllPrice.appendText("  Balance:  " + balance + "\n");
                total = Double.parseDouble(res[0][5]) + balance;
                displayAllPrice.appendText("  Total:  " + total + discount + "\n");
                displayAllPrice.appendText("----------------------------------------------------------------\n");

            }
            counter = 5;

        }
    }

    public void submitClick() throws IOException {
        if (!txtAmount.getText().isEmpty()) {
            if (!txtNote.getText().isEmpty()) {
                note = txtNote.getText();
            }
            amount = Double.parseDouble(txtAmount.getText()) - (Double.parseDouble(res[0][5]) + balance);

            if (amount < 0) {
                balance();
                delivery = "submitted";

            }
            if (amount >= 0) {
                change();
                delivery = "submitted";
            }

            //-------------------------------------------------------------------------------------------------------
        } else {
            notAllowed();
        }
    }

    public void balance() {
        int r = 0;
        int s = 0;

        if (!txtRound.getText().isEmpty()) {
            r = Integer.parseInt(txtRound.getText());
        }
        if (!txtSlim.getText().isEmpty()) {
            s = Integer.parseInt(txtSlim.getText());
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CHANGE");
        alert.setHeaderText(null);
        alert.setContentText("BALANCE " + (amount * -1));

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(" -fx-font-size: 25px;\n"
                + "   -fx-font-weight: bold;");

        String column[] = {"transaction_returned_round", "transaction_returned_slim", "transaction_amount", "transaction_balance", "status", "transaction_remarks"};
        String values[] = {"" + r, "" + s, txtAmount.getText(), "" + (((balance + Double.parseDouble(txtAmount.getText())) - total) - balance), "confirm", note};

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            setUpdateTransactionReg(column, values, modal.getTitle());
            value = "submitted";
            modal.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public void change() {
        int r = 0;
        int s = 0;

        if (!txtRound.getText().isEmpty()) {
            r = Integer.parseInt(txtRound.getText());
        }
        if (!txtSlim.getText().isEmpty()) {
            s = Integer.parseInt(txtSlim.getText());
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CHANGE");
        alert.setHeaderText(null);

        alert.setContentText("YOUR CHANGE " + amount);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(" -fx-font-size: 25px;\n"
                + "   -fx-font-weight: bold;");

        String column[] = {"transaction_returned_round", "transaction_returned_slim", "transaction_amount", "transaction_balance", "transaction_change", "status", "transaction_remarks"};

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            String values[] = {"" + r, "" + s, txtAmount.getText(), "0", "" + amount, "confirm", note};
            setUpdateTransactionReg(column, values, modal.getTitle());
            value = "submitted";
            modal.close();

        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public void notAllowed() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("NOW ALLOWED");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(" -fx-font-size: 25px;\n"
                + "   -fx-font-weight: bold;"
                + "   -fx-text-fill: red;");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

}
