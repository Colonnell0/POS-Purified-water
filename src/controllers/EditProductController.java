/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.Main.stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.EditProduct_Model;
import models.EditProduct_Table_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class EditProductController extends EditProduct_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<EditProduct_Table_Model> tblTransactionHistory;

    @FXML
    private TableColumn<EditProduct_Table_Model, Integer> tblCTransactionID;

    @FXML
    private TableColumn<EditProduct_Table_Model, String> tblCTransactionName;

    @FXML
    private TableColumn<EditProduct_Table_Model, Double> tblCTransactionPrice;

    @FXML
    private TableColumn<EditProduct_Table_Model, String> tblCTransactionStatus;

    @FXML
    private TableColumn<EditProduct_Table_Model, String> tblCTransactionDate;

    @FXML
    private TableColumn<EditProduct_Table_Model, String> edit;

    @FXML
    private TableColumn<EditProduct_Table_Model, String> delete;

    final ObservableList<EditProduct_Table_Model> data = FXCollections.observableArrayList();

    String productName = " ";

    final Button btnSave = new Button("Save");

    @FXML
    private TextField txtProductName;

    Stage modal = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblCTransactionID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        tblCTransactionName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        tblCTransactionPrice.setCellValueFactory(new PropertyValueFactory<>("ProductPrice"));
        tblCTransactionStatus.setCellValueFactory(new PropertyValueFactory<>("ProductStatus"));
        tblCTransactionDate.setCellValueFactory(new PropertyValueFactory<>("ProductDate"));
        edit.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        delete.setCellValueFactory(new PropertyValueFactory<>("Delete"));
        tblTransactionHistory.setItems(data);
        tabulateData();

        edit.setCellFactory(tc -> {
            TableCell<EditProduct_Table_Model, String> cell = new TableCell<EditProduct_Table_Model, String>() {
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
                        setEditProduct("" + tblTransactionHistory.getSelectionModel().getSelectedItem().getProductID(), tblTransactionHistory.getSelectionModel().getSelectedItem().getProductName(), "" + tblTransactionHistory.getSelectionModel().getSelectedItem().getProductPrice(), tblTransactionHistory.getSelectionModel().getSelectedItem().getProductStatus(), tblTransactionHistory.getSelectionModel().getSelectedItem().getProductDate(), "Update");
                    } catch (IOException ex) {
                        Logger.getLogger(EditProductController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // do something with id...
                    System.out.println(tblTransactionHistory.getSelectionModel().getSelectedItem().getProductID() + " " + userId);
                    tabulateData();
                }
            });
            return cell;
        });

        delete.setCellFactory(tc -> {
            TableCell<EditProduct_Table_Model, String> cell = new TableCell<EditProduct_Table_Model, String>() {
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
                        setEditProduct("" + tblTransactionHistory.getSelectionModel().getSelectedItem().getProductID(), tblTransactionHistory.getSelectionModel().getSelectedItem().getProductName(), "" + tblTransactionHistory.getSelectionModel().getSelectedItem().getProductPrice(), tblTransactionHistory.getSelectionModel().getSelectedItem().getProductStatus(), tblTransactionHistory.getSelectionModel().getSelectedItem().getProductDate(), "Delete");
                    } catch (IOException ex) {
                        Logger.getLogger(EditProductController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tabulateData();

                }
            });
            return cell;
        });

    }

    public void setProductName() {
        productName = txtProductName.getText();
        tabulateData();
    }

    public void setProductNameEnter() {
        productName = txtProductName.getText();
        tabulateData();
    }

    public void setResetFields() {
        txtProductName.setText("");
        tblTransactionHistory.getItems().clear();
    }

    public void tabulateData() {
        tblTransactionHistory.getItems().clear();
        String res[][] = getProduct(productName);
        if (res != null) {
            for (String[] re : res) {
                EditProduct_Table_Model entry = new EditProduct_Table_Model(
                        Integer.parseInt(re[0]),
                        re[1],
                        Double.parseDouble(re[2]),
                        re[3],
                        re[4],
                        "Edit",
                        "Delete"
                );

                data.add(entry);

            }

        }
    }

    public void setEditProduct(String productCode, String productName, String productPrice, String productStatus, String productDate, String action) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/EditProductModal.fxml"));
        AnchorPane showModal = loader.load();

        modal.setTitle("Edit product");
        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);

        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(639);
        modal.setMinHeight(800);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        EditProductModalController controller = loader.<EditProductModalController>getController();
        controller.txtProductCode.setText(productCode);
        controller.txtProductCode.setStyle("-fx-font: bold  11pt \"Arial\";");
        controller.txtProductName.setText(productName);
        controller.Status.add("Enable");
        controller.Status.add("Disable");
        ObservableList data = FXCollections.observableList(controller.Status);
        controller.cmbStatus.setItems(data);
        controller.txtProductPrice.setText(productPrice);
        controller.cmbStatus.setValue(productStatus);
        controller.dtpDate.setValue(LOCAL_DATE(productDate));
        controller.btnUpdateDelete.setText(action);
        controller.dtpDate.setDisable(true);
        if (controller.txtProductCode.getText().equalsIgnoreCase("96901344") || controller.txtProductCode.getText().equalsIgnoreCase("924447151")) {
            controller.txtProductName.setEditable(false);

        }

        modal.showAndWait();

    }

    public void addProduct() throws IOException {
        Main.modal("/views/AddProduct.fxml", "Giovanz Purified Water | Add Product", 420, 400);
        tabulateData();

    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

}
