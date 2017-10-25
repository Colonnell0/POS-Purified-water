/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Customer_Transaction_Model;
import models.Dashboard_Model;
import models.Discount_table_Model;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class CustomerDashbaordController extends Dashboard_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Customer_Transaction_Model> tblCustomer;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerID;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerName;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerGender;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerCellphoneNum;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerLandlineNum;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressStreet;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressBarangay;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressCityMunicipality;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressProvince;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerAddressCountry;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerBorrowedRound;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerBorrowedSlim;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerReturnedRound;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerReturnedSlim;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerRemainingRound;

    @FXML
    private TableColumn<Customer_Transaction_Model, Integer> tblCCustomerRemainingSlim;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerOthers;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerTotal;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerBalance;

    @FXML
    private TableColumn<Customer_Transaction_Model, Double> tblCCustomerAmount;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerDate;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> tblCCustomerRemarks;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> edit;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> addDiscount;

    @FXML
    private TableColumn<Customer_Transaction_Model, String> delete;

    //----------------------------------------------------------------------------
    @FXML
    private TableView<Discount_table_Model> tblDiscount;

    @FXML
    private TableColumn<Discount_table_Model, Integer> tblCDiscountID;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountCustomerName;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountGender;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountCellphone;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountLandline;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountStreet;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountBarangay;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountCity;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountProvince;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountCountry;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountProduct;

    @FXML
    private TableColumn<Discount_table_Model, Integer> tblCDiscountNumberOfProduct;

    @FXML
    private TableColumn<Discount_table_Model, Double> tblCDiscountDiscount;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountDate;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountEdit;

    @FXML
    private TableColumn<Discount_table_Model, String> tblCDiscountDelete;

    //----------------------------------------------------------------------------
    @FXML
    private TextField txtCustomer;

    @FXML
    private static Stage stage;

    @FXML
    public String customerName;

    @FXML
    private ComboBox txtComboTotal;

    @FXML
    private ComboBox cmbAccount;

    @FXML
    private ToggleButton tgbTotal;

    private Stage modal = new Stage();

    private String accounts = "";

    //---------------------------------------------------------------------
    final ObservableList<Customer_Transaction_Model> Customerdata = FXCollections.observableArrayList();
    final ObservableList<Discount_table_Model> DisountData = FXCollections.observableArrayList();

    //---------------------------------------------------------------------
    public void findCustomerForPOS() {
        tabulateData();
    }

    public void setAccounts() {
        accounts = cmbAccount.getValue().toString();
        tabulateData();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tblCCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        tblCCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        tblCCustomerGender.setCellValueFactory(new PropertyValueFactory<>("CustomerGender"));
        tblCCustomerCellphoneNum.setCellValueFactory(new PropertyValueFactory<>("CustomerCellphoneNum"));
        tblCCustomerLandlineNum.setCellValueFactory(new PropertyValueFactory<>("CustomerLandlineNum"));
        tblCCustomerAddressStreet.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressStreet"));
        tblCCustomerAddressBarangay.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressBarangay"));
        tblCCustomerAddressCityMunicipality.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressCityMunicipality"));
        tblCCustomerAddressProvince.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressProvince"));
        tblCCustomerAddressCountry.setCellValueFactory(new PropertyValueFactory<>("CustomerAddressCountry"));
        tblCCustomerBorrowedRound.setCellValueFactory(new PropertyValueFactory<>("CustomerBorrowedRound"));
        tblCCustomerBorrowedSlim.setCellValueFactory(new PropertyValueFactory<>("CustomerBorrowedSlim"));
        tblCCustomerReturnedRound.setCellValueFactory(new PropertyValueFactory<>("CustomerReturnedRound"));
        tblCCustomerReturnedSlim.setCellValueFactory(new PropertyValueFactory<>("CustomerReturnedSlim"));
        tblCCustomerRemainingRound.setCellValueFactory(new PropertyValueFactory<>("CustomerRemainingRound"));
        tblCCustomerRemainingSlim.setCellValueFactory(new PropertyValueFactory<>("CutomerRemainingSlim"));
        tblCCustomerOthers.setCellValueFactory(new PropertyValueFactory<>("CustomerOthers"));
        tblCCustomerTotal.setCellValueFactory(new PropertyValueFactory<>("CustomerTotal"));
        tblCCustomerAmount.setCellValueFactory(new PropertyValueFactory<>("CustomerAmount"));
        tblCCustomerBalance.setCellValueFactory(new PropertyValueFactory<>("CustomerBalance"));
        tblCCustomerDate.setCellValueFactory(new PropertyValueFactory<>("CustomerDate"));
        tblCCustomerRemarks.setCellValueFactory(new PropertyValueFactory<>("CustomerRemarks"));
        edit.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        addDiscount.setCellValueFactory(new PropertyValueFactory<>("AddDiscount"));
        delete.setCellValueFactory(new PropertyValueFactory<>("Delete"));
        tblCustomer.setItems(Customerdata);

        //-----------------------------------------------------------------------------
        tblCDiscountID.setCellValueFactory(new PropertyValueFactory<>("DiscountID"));
        tblCDiscountCustomerName.setCellValueFactory(new PropertyValueFactory<>("DiscountCustomerName"));
        tblCDiscountGender.setCellValueFactory(new PropertyValueFactory<>("DiscountGender"));
        tblCDiscountCellphone.setCellValueFactory(new PropertyValueFactory<>("DiscountCellphone"));
        tblCDiscountLandline.setCellValueFactory(new PropertyValueFactory<>("DiscountLandline"));
        tblCDiscountStreet.setCellValueFactory(new PropertyValueFactory<>("DiscountStreet"));
        tblCDiscountBarangay.setCellValueFactory(new PropertyValueFactory<>("DiscountBarangay"));
        tblCDiscountCity.setCellValueFactory(new PropertyValueFactory<>("DiscountCity"));
        tblCDiscountProvince.setCellValueFactory(new PropertyValueFactory<>("DiscountProvince"));
        tblCDiscountCountry.setCellValueFactory(new PropertyValueFactory<>("DiscountCountry"));
        tblCDiscountProduct.setCellValueFactory(new PropertyValueFactory<>("DiscountProduct"));
        tblCDiscountNumberOfProduct.setCellValueFactory(new PropertyValueFactory<>("DiscountNumberOfProduct"));
        tblCDiscountDiscount.setCellValueFactory(new PropertyValueFactory<>("DiscountDiscount"));
        tblCDiscountDate.setCellValueFactory(new PropertyValueFactory<>("DiscountDate"));
        tblCDiscountEdit.setCellValueFactory(new PropertyValueFactory<>("DiscountEdit"));
        tblCDiscountDelete.setCellValueFactory(new PropertyValueFactory<>("DiscountDelete"));
        tblDiscount.setItems(DisountData);

        List<String> list = new ArrayList<>();
        String[] result = getAccountList();
        for (int i = 0; i < result.length; i++) {
            list.add(result[i]);
        }
        ObservableList obList = FXCollections.observableList(list);

        cmbAccount.setItems(obList);
        cmbAccount.setValue(result[0]);
        setAccounts();

        edit.setCellFactory(tc -> {
            TableCell<Customer_Transaction_Model, String> cell = new TableCell<Customer_Transaction_Model, String>() {
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
                        setCustomer("" + tblCustomer.getSelectionModel().getSelectedItem().getCustomerID(), "Update");
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerDashbaordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tabulateData();
                }
            });
            return cell;
        });
        addDiscount.setCellFactory(tc -> {
            TableCell<Customer_Transaction_Model, String> cell = new TableCell<Customer_Transaction_Model, String>() {
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
                        addCustomerDiscount(tblCustomer.getSelectionModel().getSelectedItem().getCustomerID(), tblCustomer.getSelectionModel().getSelectedItem().getCustomerName(), tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressStreet() + ", " + tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressBarangay() + ", " + tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressCityMunicipality() + ", " + tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressProvince() + ", " + tblCustomer.getSelectionModel().getSelectedItem().getCustomerAddressCountry());
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerDashbaordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     tabulateData();

                }
            });
            return cell;
        });

        delete.setCellFactory(tc -> {
            TableCell<Customer_Transaction_Model, String> cell = new TableCell<Customer_Transaction_Model, String>() {
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
                        setCustomer("" + tblCustomer.getSelectionModel().getSelectedItem().getCustomerID(), "Delete");
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerDashbaordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tabulateData();

                }
            });
            return cell;
        });

        //----------------------------------------------------------------------
        tblCDiscountEdit.setCellFactory(tc -> {
            TableCell<Discount_table_Model, String> cell = new TableCell<Discount_table_Model, String>() {
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
                        editDeleteCustomerDiscount(tblDiscount.getSelectionModel().getSelectedItem().getDiscountID(),
                                tblDiscount.getSelectionModel().getSelectedItem().getDiscountCustomerName(),
                                tblDiscount.getSelectionModel().getSelectedItem().getDiscountSteet() + ", " + tblDiscount.getSelectionModel().getSelectedItem().getDiscountBarangay() + ", " + tblDiscount.getSelectionModel().getSelectedItem().getDiscountCity() + ", " + tblDiscount.getSelectionModel().getSelectedItem().getDiscountProvince() + ", " + tblDiscount.getSelectionModel().getSelectedItem().getDiscountCountry(),
                                tblDiscount.getSelectionModel().getSelectedItem().getDiscountProduct(),
                                "" + tblDiscount.getSelectionModel().getSelectedItem().getDiscountNumberOfProduct(),
                                "" + tblDiscount.getSelectionModel().getSelectedItem().getDiscountDiscount(),
                                "Update");
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerDashbaordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tabulateData();

                }
            });
            return cell;
        });
        tblCDiscountDelete.setCellFactory(tc -> {
            TableCell<Discount_table_Model, String> cell = new TableCell<Discount_table_Model, String>() {
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
                        editDeleteCustomerDiscount(tblDiscount.getSelectionModel().getSelectedItem().getDiscountID(),
                                tblDiscount.getSelectionModel().getSelectedItem().getDiscountCustomerName(),
                                tblDiscount.getSelectionModel().getSelectedItem().getDiscountSteet() + ", " + tblDiscount.getSelectionModel().getSelectedItem().getDiscountBarangay() + ", " + tblDiscount.getSelectionModel().getSelectedItem().getDiscountCity() + ", " + tblDiscount.getSelectionModel().getSelectedItem().getDiscountProvince() + ", " + tblDiscount.getSelectionModel().getSelectedItem().getDiscountCountry(),
                                tblDiscount.getSelectionModel().getSelectedItem().getDiscountProduct(),
                                "" + tblDiscount.getSelectionModel().getSelectedItem().getDiscountNumberOfProduct(),
                                "" + tblDiscount.getSelectionModel().getSelectedItem().getDiscountDiscount(),
                                "Delete");
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerDashbaordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tabulateData();

                }
            });
            return cell;
        });
    }

    public void tabulateData() {
        tblCustomer.getItems().clear();
        tblDiscount.getItems().clear();

        String res[][] = viewAllCustomerProfileGroup(txtCustomer.getText(), accounts);
        if (res != null) {
            for (String[] re : res) {
                Customer_Transaction_Model entry = new Customer_Transaction_Model(
                        Integer.parseInt(re[0]),
                        re[1],
                        re[2],
                        re[3],
                        re[4],
                        re[5],
                        re[6],
                        re[7],
                        re[8],
                        re[9],
                        Integer.parseInt(re[10]),
                        Integer.parseInt(re[11]),
                        Integer.parseInt(re[12]),
                        Integer.parseInt(re[13]),
                        Integer.parseInt(re[14]),
                        Integer.parseInt(re[15]),
                        Double.parseDouble(re[16]),
                        Double.parseDouble(re[17]),
                        Double.parseDouble(re[18]),
                        Double.parseDouble(re[19]),
                        0,
                        re[20],
                        re[21], "", "", "",
                        "Edit",//type
                        "Add Discount",//cashier
                        "Delete");
                Customerdata.add(entry);

            }

        }
        String resDiscount[][] = viewAllCustomerDiscount(txtCustomer.getText(), accounts);
        if (resDiscount != null) {
            for (String[] re : resDiscount) {
                Discount_table_Model entry = new Discount_table_Model(
                        Integer.parseInt(re[0]),
                        re[1],
                        re[2],
                        re[3],
                        re[4],
                        re[5],
                        re[6],
                        re[7],
                        re[8],
                        re[9],
                        re[10],
                        Integer.parseInt(re[11]),
                        Double.parseDouble(re[12]),
                        re[13],
                        "Edit",
                        "Delete"
                );
                DisountData.add(entry);

            }

        }
    }

    public void addCustomer(ActionEvent event) throws IOException {
        Main.customer("/views/AddCustomer.fxml", "Giovanz Purified Water | Add Customer", 600, 700);
        tabulateData();
    }

    public void setCustomer(String id, String action) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/AddCustomer.fxml"));
        AnchorPane showModal = loader.load();

        modal.setTitle(action + " Customer");
        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UTILITY);

        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(639);
        modal.setMinHeight(800);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        ModalController controller = loader.<ModalController>getController();
        controller.btnSubmit.setText(action);
        String res[][] = getCustomer(id);
        if (res.length > 0) {

            for (String[] re : res) {
                controller.txtFirstname.setText(re[0]);
                controller.txtMI.setText(re[1]);
                controller.txtLastname.setText(re[2]);
                if (re[3].equalsIgnoreCase("Male")) {
                    controller.radioMale.setSelected(true);
                }
                if (re[3].equalsIgnoreCase("Female")) {
                    controller.radioFemale.setSelected(true);
                }
                controller.txtAddressStreet.setText(re[4]);
                controller.txtBarangay.setText(re[5]);
                controller.txtCityMunicipality.setText(re[6]);
                controller.txtProvince.setText(re[7]);
                controller.txtCountry.setText(re[8]);
                controller.txtCellphoneNumber.setText(re[9]);
                controller.txtLandline.setText(re[10]);
                controller.customer_id = Integer.parseInt(re[11]);
                controller.customer_balance = tblCustomer.getSelectionModel().getSelectedItem().getCustomerBalance();
                controller.customer_remaining_round = tblCustomer.getSelectionModel().getSelectedItem().getCustomerRemainingRound();
                controller.customer_remaining_slim = tblCustomer.getSelectionModel().getSelectedItem().getCutomerRemainingSlim();
            }

        }

        modal.showAndWait();

    }

    public void addCustomerDiscount(int id, String name, String address) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/Discount.fxml"));
        AnchorPane showModal = loader.load();

        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);

        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(450);
        modal.setMinHeight(300);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        DiscountController controller = loader.<DiscountController>getController();
        controller.customer_id = id;
        controller.lblName.setText(name);
        controller.lblAddress.setText(address);
        String product_list[] = getProduct();
        TextFields.bindAutoCompletion(controller.txtProductName, product_list);

        modal.showAndWait();

    }

    public void editDeleteCustomerDiscount(int id, String name, String address, String product, String numberOFProduct, String Discount, String button) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/Discount.fxml"));
        AnchorPane showModal = loader.load();

        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);

        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(450);
        modal.setMinHeight(300);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        DiscountController controller = loader.<DiscountController>getController();
        controller.customer_id = id;
        controller.lblName.setText(name);
        controller.lblAddress.setText(address);
        String product_list[] = getProduct();
        TextFields.bindAutoCompletion(controller.txtProductName, product_list);
        controller.txtProductName.setText(product);
        controller.txtProductName.setDisable(true);
        controller.txtUnitPrice.setDisable(true);
        controller.txtNumOfProduct.setText(numberOFProduct);
        controller.txtDiscountPrice.setText(Discount);
        controller.btnDiscount.setText(button);

        modal.showAndWait();

    }
}
