/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.POSController.modal;
import static controllers.Main.stage;
import java.io.IOException;
import java.net.UnknownHostException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import libs.FormValidation;
import libs.Hash;
import models.Login_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class LoginController extends Login_Model {

    /**
     * Initializes the controller class.
     */
    private static String[] login = new String[3];

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSubmit;

    @FXML
    private MainController mainController;

    @FXML
    private Label lblInvalid;

    Hash hash = new Hash();
    FormValidation formVal = new FormValidation();

   
    Date dNow = new Date();
    SimpleDateFormat ft
            = new SimpleDateFormat("yyyy/MM/dd");

    public static String[] login(String source, String title, int w, int h) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(POSController.class.getResource(source));
        AnchorPane showModal = loader.load();

        modal.setTitle(title);

        modal.setOnCloseRequest(e -> {
            System.exit(0);

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
        modal.toFront();
        modal.showAndWait();

        return login;
    }

    public void login() throws NoSuchAlgorithmException, InvalidKeySpecException, UnknownHostException {
       
        if (Integer.parseInt(time_date(ft.format(dNow))) >= 0) {

            String[][] login_name = login_account(txtUsername.getText(), hash.hash(txtPassword.getText()));
            if (login_name.length > 0) {
                login[0] = login_name[0][0];
                login[1] = login_name[0][1];
                login[2] = login_name[0][4];

                modal.close();

            } else {
                txtUsername.requestFocus();
                lblInvalid.setText("Invalid user");
                txtUsername.setText("");
                txtPassword.setText("");

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're clock is behind");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    public void setEmpty() {
        lblInvalid.setText("");
        txtUsername.setText("");
        txtPassword.setText("");

    }

    public void loginEnter(KeyEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, UnknownHostException {

        if (event.getCode() == KeyCode.ENTER) {
            if (Integer.parseInt(time_date(ft.format(dNow))) >= 0) {
                String[][] login_name = login_account(txtUsername.getText(), hash.hash(txtPassword.getText()));
                if (login_name.length > 0) {
                    login[0] = login_name[0][0];
                    login[1] = login_name[0][1];
                    login[2] = login_name[0][4];

                    modal.close();

                } else {
                    txtUsername.requestFocus();
                    lblInvalid.setText("Invalid user");
                    txtUsername.setText("");
                    txtPassword.setText("");

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're clock is behind");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }

        }

    }

}
