/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.Main.stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Account_table_Model;
import models.Dashboard_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class AccountsDashboardController extends Dashboard_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    //--------------------------------------------------------------------------
    @FXML
    private TableView<Account_table_Model> tblAccount;

    @FXML
    private TableColumn<Account_table_Model, String> tblCAccountName;

    @FXML
    private TableColumn<Account_table_Model, String> tblCPosition;

    @FXML
    private TableColumn<Account_table_Model, String> tblCDateCreated;

    @FXML
    private TableColumn<Account_table_Model, String> tblCStatus;

    @FXML
    private TableColumn<Account_table_Model, Integer> tblCAccountID;

    @FXML
    private TableColumn<Account_table_Model, String> tblCGender;

    @FXML
    private TableColumn<Account_table_Model, String> tblCAddress;

    @FXML
    private TableColumn<Account_table_Model, String> tblCContact;

    @FXML
    private TableColumn<Account_table_Model, String> edit;

    @FXML
    private TableColumn<Account_table_Model, String> delete;

    private Stage modal = new Stage();

    //--------------------------------------------------------------------------
    @FXML
    public TextField txtAccountName;

    final ObservableList<Account_table_Model> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        tblCAccountName.setCellValueFactory(new PropertyValueFactory<>("AccountName"));
        tblCPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
        tblCDateCreated.setCellValueFactory(new PropertyValueFactory<>("DateCreated"));
        tblCStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        tblCAccountID.setCellValueFactory(new PropertyValueFactory<>("AccountID"));
        tblCGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        tblCAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tblCContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        edit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        delete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        tblAccount.setItems(data);

        String res[][] = getAccountAll(" ");
        if (res != null) {
            for (String[] re : res) {
                Account_table_Model entry = new Account_table_Model(
                        re[0],
                        re[1],
                        re[2],
                        re[3],
                        Integer.parseInt(re[4]),
                        re[5],
                        re[6],
                        re[7],
                        "edit", "delete");

                data.add(entry);

            }

        }

        edit.setCellFactory(tc -> {
            TableCell<Account_table_Model, String> cell = new TableCell<Account_table_Model, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    String userId = cell.getItem();
                    try {
                        setAccount("" + tblAccount.getSelectionModel().getSelectedItem().getAccountID(), "Update");
                    } catch (IOException ex) {
                        Logger.getLogger(AccountsDashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    findAccount();
                }
            });
            return cell;
        });

        delete.setCellFactory(tc -> {
            TableCell<Account_table_Model, String> cell = new TableCell<Account_table_Model, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    String userId = cell.getItem();
                    try {
                        // do something with id...
                        setAccount("" + tblAccount.getSelectionModel().getSelectedItem().getAccountID(), "Delete");
                    } catch (IOException ex) {
                        Logger.getLogger(AccountsDashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    findAccount();

                }
            });
            return cell;
        });

    }

    public void findAccount() {
        tblAccount.getItems().clear();
        String res[][] = getAccountAll(txtAccountName.getText());
        if (res != null) {
            for (String[] re : res) {
                Account_table_Model entry = new Account_table_Model(
                        re[0],
                        re[1],
                        re[2],
                        re[3],
                        Integer.parseInt(re[4]),
                        re[5],
                        re[6],
                        re[7],
                        "edit", "delete");

                data.add(entry);

            }

        }
    }

    public void addAccount() throws IOException {
        Main.modal("/views/AddAccount.fxml", "Giovanz Purified Water | Add accounts", 400, 500);
        findAccount();

    }

    public void setAccount(String id, String action) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/AddAccount.fxml"));
        AnchorPane showModal = loader.load();

        modal.setTitle(action + " Account");
        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);

        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(400);
        modal.setMinHeight(500);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        AddAccountController controller = loader.<AddAccountController>getController();
        controller.lblTitle.setText(action + " Account");

        String res[][] = getAccountTabulate(id);
        if (res != null) {
            for (String[] re : res) {
                controller.cmbAccountStatus.setValue(re[0]);
                controller.txtFirstname.setText(re[1]);
                controller.txtMI.setText(re[2]);
                controller.txtLastname.setText(re[3]);
                controller.txtStreet.setText(re[4]);
                controller.txtBarangay.setText(re[5]);
                controller.txtCity.setText(re[6]);
                controller.txtProvince.setText(re[7]);
                if (re[8].equalsIgnoreCase("Male")) {
                    controller.radioMale.setSelected(true);
                }
                if (re[8].equalsIgnoreCase("Female")) {
                    controller.radioFemale.setSelected(true);
                }
                controller.txtContact.setText(re[9]);
                controller.cmbAccountType.setValue(re[10]);
                controller.txtUsername.setText(re[11]);
                controller.txtUsername.setDisable(true);
                controller.account_type_id = Integer.parseInt(re[13]);
                controller.account_id = Integer.parseInt(re[14]);
                controller.btnSave.setText(action);
                

            }

        }
        modal.showAndWait();

    }

}
