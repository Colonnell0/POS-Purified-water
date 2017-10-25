/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.Main.stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libs.FormatNumber;
import models.Customer;

/**
 *
 * @author Colonnello
 */
public class PAYController extends Customer {
    
    @FXML
    private Label lblName;
    
    @FXML
    private Label lblAddress;
    
    @FXML
    private Label lblRound;
    
    @FXML
    private Label lblSlim;
    
    @FXML
    private TextArea displayAllprice;
    
    @FXML
    private TextField txtRound;
    
    @FXML
    private TextField txtSlim;
    
    @FXML
    private Button btnSubmit;
    
    @FXML
    private TextField txtAmount;

    //--------------------------------------------------------
    //--------------------------------------------------------
    static Stage modal;
    public int trap = 0;
    static String value = "";
    private double amount = 0;
    private String[] transactionID;
    private int round = 0;
    private int slim = 0;
    private double balance = 0;
    private double total = 0;
    private String id;
    String res[][];
    String additionalRes[][];
    String column;
    
    FormatNumber fm = new FormatNumber();
    
    public static String payTransaction(String source, String title, int w, int h) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(source));
        AnchorPane showModal = loader.load();
        
        modal.setOnCloseRequest(e -> {
            value = "close" + modal.getTitle();
            
        });
        
        modal.setTitle(title);
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
        
        return value;
    }
    
    public void trigger() {
        
        String[] splited = modal.getTitle().split("\\s+");
        String account_name = "";
        for (int i = 0; i < splited.length; i++) {
            if (i >= 3) {
                account_name += splited[i] + " ";
            }
        }
        
        if (trap == 0) {
            transactionID = getTransactionLastID(account_name);
            System.out.println(Arrays.toString(transactionID));
            
            if (transactionID[1].equalsIgnoreCase("0")) {
                res = getCustomerTransactionNotYetReg(transactionID[0], "unconfirm");
               
                displayAllprice.setText(" " + res[0][7] + " : " + res[0][8] + "\n----------------------------------------------\n");
                txtRound.setText("0");
                txtSlim.setText("0");
                lblName.setText("N/A");
                lblAddress.setText("N/A");

                if (res[0][3].equalsIgnoreCase("0")) {
                    lblRound.setText("0");
                }
                if (!res[0][3].equalsIgnoreCase("0")) {
                    lblRound.setText(res[0][3]);
                }

                if (res[0][4].equalsIgnoreCase("0")) {
                    lblSlim.setText("0");
                }
                if (!res[0][4].equalsIgnoreCase("0")) {
                    lblSlim.setText(res[0][4]);
                }

                String[] transactionProduct = res[0][9].split(",");
                for (int i = 0; i < transactionProduct.length; i++) {
                    displayAllprice.appendText(transactionProduct[i] + "\n");
                }
                displayAllprice.appendText("----------------------------------------------\n");

                displayAllprice.appendText("  Total:  " + res[0][5]);

                trap++;

            }
            
            if (!transactionID[1].equalsIgnoreCase("0")) {
                res = getCustomerTransactionReg(transactionID[1], transactionID[0], "unconfirm");
                
                if (!getCustomerBalanceCount(transactionID[1], transactionID[0]).equalsIgnoreCase("0")) {
                    additionalRes = getCustomerBalance(transactionID[1], transactionID[0]);
                    slim = Integer.parseInt(additionalRes[0][3]);
                    round = Integer.parseInt(additionalRes[0][2]);
                    balance = Double.parseDouble(additionalRes[0][4]);
                    if (balance <= 0) {
                        balance = 0;
                    }
                    
                }
                
                displayAllprice.setText(" " + res[0][7] + " : " + res[0][8] + "\n----------------------------------------------\n");
                
                lblName.setText(res[0][1]);
                lblAddress.setText(res[0][2]);
                round = Integer.parseInt(res[0][3]) + round;
                lblRound.setText("" + round);
                slim = Integer.parseInt(res[0][4]) + slim;
                lblSlim.setText("" + slim);
                txtRound.setText("0");
                txtSlim.setText("0");
                String[] transactionProduct = res[0][9].split(",");
                for (int i = 0; i < transactionProduct.length; i++) {
                    displayAllprice.appendText(transactionProduct[i] + "\n");
                }
                displayAllprice.appendText("----------------------------------------------\n");
                
                displayAllprice.appendText("  Subtotal:  " + res[0][5] + "\n");
                displayAllprice.appendText("  Balance:  " + balance + "\n");
                total = Double.parseDouble(res[0][5]) + balance;
                displayAllprice.appendText("  Total:  " + total + "\n");
                displayAllprice.appendText("----------------------------------------------\n");
                trap++;
            }
            
        }
        
    }
    
    public void submitClick() throws IOException {
        if (!txtAmount.getText().isEmpty()) {
            
            amount = Double.parseDouble(txtAmount.getText()) - (Double.parseDouble(res[0][5]) + balance);
            
            if (!lblName.getText().equalsIgnoreCase("N/A")) {
                if (amount < 0) {
                    balance();
                    
                }
                if (amount >= 0) {
                    change();
                }
                
            }
            //-------------------------------------------------------------------------------------------------------

            if (lblName.getText().equalsIgnoreCase("N/A")) {
                if (Double.parseDouble(txtAmount.getText()) >= Double.parseDouble(res[0][5]) && txtRound.getText().equalsIgnoreCase(lblRound.getText()) && txtSlim.getText().equalsIgnoreCase(lblSlim.getText())) {
                    change();
                } else {
                    notAllowed();
                    
                }
                
            }
        } else {
            notAllowed();
        }
    }
    
    public void submitEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            if (!txtAmount.getText().isEmpty()) {
                
                amount = Double.parseDouble(txtAmount.getText()) - (Double.parseDouble(res[0][5]) + balance);
                
                if (!lblName.getText().equalsIgnoreCase("N/A")) {
                    if (amount < 0) {
                        balance();
                        
                    }
                    if (amount >= 0) {
                        change();
                    }
                    
                }
                //-------------------------------------------------------------------------------------------------------

                if (lblName.getText().equalsIgnoreCase("N/A")) {
                    if (Double.parseDouble(txtAmount.getText()) >= Double.parseDouble(res[0][5]) && txtRound.getText().equalsIgnoreCase(lblRound.getText()) && txtSlim.getText().equalsIgnoreCase(lblSlim.getText())) {
                        change();
                    } else {
                        notAllowed();
                        
                    }
                    
                }
            } else {
                notAllowed();
            }
        }
        
    }
    
    public void setViewClose(KeyEvent key) throws Exception {
        
        if (key.getCode() == KeyCode.ESCAPE) {
            value = "close" + modal.getTitle();
            modal.close();
        }
        
    }
    
    public void balance() {
        int r = 0;
        int s = 0;
        
        r = Integer.parseInt(txtRound.getText());
        s = Integer.parseInt(txtSlim.getText());
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("CHANGE");
        alert.setHeaderText(null);
        alert.setContentText("BALANCE " + (amount * -1));
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(" -fx-font-size: 25px;\n"
                + "   -fx-font-weight: bold;");
        
        String column[] = {"transaction_returned_round", "transaction_returned_slim", "transaction_amount", "transaction_balance", "status"};
        String values[] = {"" + r, "" + s, txtAmount.getText(), "" + (((balance + Double.parseDouble(txtAmount.getText())) - total) - balance), "confirm"};
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            setUpdateTransactionReg(column, values, transactionID[0]);
            value = "submitted";
            modal.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
    public void change() {
        int r = 0;
        int s = 0;
        
        r = Integer.parseInt(txtRound.getText());
        s = Integer.parseInt(txtSlim.getText());
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("CHANGE");
        alert.setHeaderText(null);
        
        alert.setContentText("YOUR CHANGE " + amount);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(" -fx-font-size: 25px;\n"
                + "   -fx-font-weight: bold;");
        
        String column[] = {"transaction_returned_round", "transaction_returned_slim", "transaction_amount", "transaction_balance", "transaction_change", "status"};
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            
            String values[] = {"" + r, "" + s, txtAmount.getText(), "0", "" + amount, "confirm"};
            setUpdateTransactionReg(column, values, transactionID[0]);
            value = "submitted";
            modal.close();
            
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
    public void notAllowed() {
        Alert alert = new Alert(AlertType.WARNING);
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
