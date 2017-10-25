/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import libs.NodePrinter;
import models.Expenses_Table_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class PrintPreviewExpensesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public TableView<Expenses_Table_Model> tblExpenses;

    @FXML
    public TableColumn<Expenses_Table_Model, String> tbcExpenses;

    @FXML
    public TableColumn<Expenses_Table_Model, Double> tbcAmount;

    @FXML
    public TableColumn<Expenses_Table_Model, String> tbcReceived;

    @FXML
    public TableColumn<Expenses_Table_Model, String> tbcNote;

    @FXML
    public Label lblExpensesCatergory;

    @FXML
    public Label lblTotal;

    @FXML
    public Label lblFromDate;

    @FXML
    public Label lbltoDate;

    final ObservableList<Expenses_Table_Model> data = FXCollections.observableArrayList();

    @FXML
    private AnchorPane panePrint;
    final KeyCombination kb = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
    private final NodePrinter printerNode = new NodePrinter();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void print() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        printerNode.printNode(panePrint);
    }

    public void printShorcut(KeyEvent event) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        if (kb.match(event)) {
            printerNode.printNode(panePrint);
        }
    }

}
