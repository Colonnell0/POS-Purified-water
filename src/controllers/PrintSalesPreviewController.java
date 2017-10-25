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
import models.Customer_Transaction_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class PrintSalesPreviewController  implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public Label lblFromDate;

    @FXML
    public Label lbltoDate;

    @FXML
    public Label lblAddress;
    
    @FXML
    public Label lblTransactionTotal;

    @FXML
    public Label lblTransactionType;

    @FXML
    public String res[][];

    //--------------------------------------------------------------------------
    @FXML
    public TableView<Customer_Transaction_Model> tblCustomer;

    @FXML
    public TableColumn<Customer_Transaction_Model, Integer> tblCCustomerID;

    @FXML
    public TableColumn<Customer_Transaction_Model, String> tblCCustomerName;

    @FXML
    public TableColumn<Customer_Transaction_Model, Integer> tblCCustomerBorrowedRound;

    @FXML
    public TableColumn<Customer_Transaction_Model, Integer> tblCCustomerBorrowedSlim;

    @FXML
    public TableColumn<Customer_Transaction_Model, Double> tblCCustomerAmount;

    @FXML
    public TableColumn<Customer_Transaction_Model, Double> tblCCustomerTotal;

    @FXML
    public TableColumn<Customer_Transaction_Model, String> tblCCustomerRemarks;
    //--------------------------------------------------------------------------

   final ObservableList<Customer_Transaction_Model> data = FXCollections.observableArrayList();
   
    private final NodePrinter printerNode = new NodePrinter();

    @FXML
    private AnchorPane panePrint;
    final KeyCombination kb = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);

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
