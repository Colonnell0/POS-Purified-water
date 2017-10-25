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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libs.FormatNumber;
import models.Expenses_Modal;
import models.Expenses_Table_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class ExpensesReportsController extends Expenses_Modal implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    @FXML
    private Label lblExpenseAmount;
    //--------------------------------------------------------------------------
    @FXML
    private ComboBox cmbExpenses;
    //--------------------------------------------------------------------------
    @FXML
    private DatePicker dateTo;

    @FXML
    private DatePicker dateFrom;
    //--------------------------------------------------------------------------
    private int expensesAmount = 0;
    private String expenses = "";
    private String toDate = "";
    private String fromDate = "";

    //--------------------------------------------------------------------------
    final ObservableList<Expenses_Table_Model> data = FXCollections.observableArrayList();
    final ObservableList expense = FXCollections.observableArrayList(getExpense());

    FormatNumber formatNumber = new FormatNumber();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tbcExpensesID.setCellValueFactory(new PropertyValueFactory<>("ExpensesID"));
        tbcExpenses.setCellValueFactory(new PropertyValueFactory<>("Expenses"));
        tbcAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        tbcCashierName.setCellValueFactory(new PropertyValueFactory<>("CashierName"));
        tbcReceived.setCellValueFactory(new PropertyValueFactory<>("Received"));
        tbcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tbcNote.setCellValueFactory(new PropertyValueFactory<>("Note"));
        tblExpenses.setItems(data);
        dateTo.setValue(LocalDate.now());
        dateFrom.setValue(LocalDate.now());
        fromDate = " AND datediff(ifnull(expenses.expenses_date,'0000-00-00'), '" + dateFrom.getValue().toString() + "') >=0";
        toDate = " AND datediff( '" + dateTo.getValue().toString() + "',ifnull(expenses.expenses_date,'0000-00-00')) >=0";

        cmbExpenses.setItems(expense);
        setExpensesData("LIMIT 50");

    }

    public void setFromDateExpenses() {
        tblExpenses.getItems().clear();
        fromDate = " AND datediff(ifnull(expenses.expenses_date,'0000-00-00'), '" + dateFrom.getValue().toString() + "') >=0";
        setExpensesData("");
    }

    public void setToDateExpenses() {
        tblExpenses.getItems().clear();
        toDate = " AND datediff( '" + dateTo.getValue().toString() + "',ifnull(expenses.expenses_date,'0000-00-00')) >=0";
        setExpensesData("");
    }

    public void setExpenses() {
        tblExpenses.getItems().clear();
        expenses = cmbExpenses.getValue().toString();
        setExpensesData("");
    }

    private void setExpensesData(String limit) {
        String result[][] = getExpenses(toDate + " " + fromDate, expenses, limit);
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
                expensesAmount += Double.parseDouble(res[2]);
            }
            lblExpenseAmount.setText("Php " + formatNumber.formatNumbers("" + expensesAmount));
            expensesAmount = 0;
        }
    }

    public void print() throws IOException {

        modal = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/PrintPreviewExpenses.fxml"));
        AnchorPane showModal = loader.load();

        modal.setTitle("Print preview");
        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UTILITY);

        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(639);
        modal.setMinHeight(800);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        PrintPreviewExpensesController controller = loader.<PrintPreviewExpensesController>getController();
        controller.lblFromDate.setText(date(dateFrom.getValue()));
        controller.lbltoDate.setText(date(dateTo.getValue()));

        controller.lblExpensesCatergory.setText("Expenses category: " + expenses);
        controller.lblTotal.setText("Total: " + lblExpenseAmount.getText());

        controller.tbcExpenses.setCellValueFactory(new PropertyValueFactory<>("Expenses"));
        controller.tbcAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        controller.tbcReceived.setCellValueFactory(new PropertyValueFactory<>("Received"));
        controller.tbcNote.setCellValueFactory(new PropertyValueFactory<>("Note"));

        int size = tblExpenses.getItems().size();

        if (tblExpenses.getItems().size() > 21) {
            size = 21;
        }

        for (int i = 0; i < size; i++) {

            Expenses_Table_Model entry = new Expenses_Table_Model(
                    0,
                    tblExpenses.getItems().get(i).getExpenses(),
                    tblExpenses.getItems().get(i).getAmount(),
                    "",
                    tblExpenses.getItems().get(i).getReceived(),
                    "",
                    tblExpenses.getItems().get(i).getNote()
            );
            controller.data.add(entry);

        }
        controller.tblExpenses.setItems(controller.data);

        modal.showAndWait();

    }

    String date(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        return date.format(formatter);
    }

}
