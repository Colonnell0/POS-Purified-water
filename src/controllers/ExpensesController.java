/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.CustomerController.modal;
import static controllers.Main.stage;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Expenses_Modal;
import models.Expenses_Table_Model;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class ExpensesController extends Expenses_Modal implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static String expense = "";
    public int counterData = 0;
    private int accountID = 0;

    @FXML
    private TextField txtCashierName;

    @FXML
    private TextField txtReceived;

    @FXML
    private TextField txtExpenses;

    @FXML
    private DatePicker dateDate;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextArea txtANote;

    //-----------------------------------------------
    @FXML
    private TableView<Expenses_Table_Model> tblExpenses;

    @FXML
    private TableColumn<Expenses_Table_Model, Integer> tbcExpensesID;

    @FXML
    private TableColumn<Expenses_Table_Model, String> tbcExpenses;

    @FXML
    private TableColumn<Expenses_Table_Model, Double> tbcAmount;

    @FXML
    private TableColumn<Expenses_Table_Model, String> tbcCashierName;

    @FXML
    private TableColumn<Expenses_Table_Model, String> tbcReceived;

    @FXML
    private TableColumn<Expenses_Table_Model, String> tbcDate;

    @FXML
    private TableColumn<Expenses_Table_Model, String> tbcNote;

    //--------------------------------------------------------------------------
    final ObservableList<Expenses_Table_Model> data = FXCollections.observableArrayList();

    java.util.Date dt = new java.util.Date();

    java.text.SimpleDateFormat datetime
            = new java.text.SimpleDateFormat("yyyy-MM-dd");

    public static String expense(String source, String title, int w, int h) throws IOException {
        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(source));
        AnchorPane showModal = loader.load();

        modal.setTitle(title);

        modal.setOnCloseRequest(e -> {
            expense = "close";
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

        return expense;
    }

    public void escHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            expense = "close";
            modal.close();
        }
    }

    public void setData() throws UnknownHostException {
        if (counterData == 0) {
            TextFields.bindAutoCompletion(txtReceived, getAccountName());
            TextFields.bindAutoCompletion(txtExpenses, getExpense());
            dateDate.setValue(LocalDate.now());
            txtCashierName.setText(modal.getTitle().replace("Expenses: ", ""));
            counterData = 5;
            accountID = Integer.parseInt(getAccountID(modal.getTitle().replace("Expenses: ", "")));

            tbcExpensesID.setCellValueFactory(new PropertyValueFactory<>("ExpensesID"));
            tbcExpenses.setCellValueFactory(new PropertyValueFactory<>("Expenses"));
            tbcAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
            tbcCashierName.setCellValueFactory(new PropertyValueFactory<>("CashierName"));
            tbcReceived.setCellValueFactory(new PropertyValueFactory<>("Received"));
            tbcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
            tbcNote.setCellValueFactory(new PropertyValueFactory<>("Note"));
            tblExpenses.setItems(data);

            String result[][] = getExpenses(datetime.format(dt));
            if (!result.equals(null)) {
                for (String res[] : result) {
                    Expenses_Table_Model entry = new Expenses_Table_Model(
                            Integer.parseInt(res[0]),
                            res[1],
                            Double.parseDouble(res[2]),
                            res[3],
                            res[4],
                            res[5],
                            res[6]
                    );
                    data.add(entry);
                }
            }
        }
    }

    public void saveExpense() {
        if (!txtReceived.getText().isEmpty()
                && !txtExpenses.getText().isEmpty()
                && !txtAmount.getText().isEmpty()) {
            Double amount = Double.parseDouble(txtAmount.getText());
            String note = "";

            if (txtANote.getText().isEmpty()) {
                note = "N/A";
            }
            if (!txtANote.getText().isEmpty()) {
                note = txtANote.getText();
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to save?");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 20px;\n"
                    + "   -fx-font-weight: bold;"
            );
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String column[] = {"expenses", "expenses_amount", "expenses_description", "expenses_received", "account_id", "expenses_date"};
                String values[] = {txtExpenses.getText(), "" + amount, note, txtReceived.getText(), "" + accountID, dateDate.getValue().toString()};
                setInsertExpense("expenses", column, values);
                tblExpenses.getSelectionModel().selectLast();
                Expenses_Table_Model entry = new Expenses_Table_Model(
                        tblExpenses.getSelectionModel().getSelectedItem().getExpensesID() + 1,
                        txtExpenses.getText(),
                        amount,
                        modal.getTitle().replace("Expenses: ", ""),
                        txtReceived.getText(),
                        dateDate.getValue().toString(),
                        txtANote.getText()
                );
                data.add(entry);

                txtReceived.setText("");
                txtExpenses.setText("");
                txtAmount.setText("");
                txtANote.setText("");
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }

    }

    public void cancelExpense() {
        txtReceived.setText("");
        txtExpenses.setText("");
        txtAmount.setText("");
        txtANote.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

   
}
