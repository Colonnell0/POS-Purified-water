/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import libs.NodePrinter;
import models.GenerateSales_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class GenerateSalesController extends GenerateSales_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public Label lblCashierName_1;

    @FXML
    public Label lblCashierName_2;

    @FXML
    public Label lblAddress;

    @FXML
    public Label lblContact;

    @FXML
    public Label lblNumberOfRoundPOS;

    @FXML
    public Label lblNumberOfSlimPOS;

    @FXML
    public Label lblNumberOfOthersPOS;

    @FXML
    public Label lblNumberOfRoundDelivery;

    @FXML
    public Label lblNumberOfSlimDelivery;

    @FXML
    public Label lblNumberOfOthersDelivery;

    @FXML
    public Label lblNumberTotal;

    @FXML
    public Label lblPOSAmount;

    @FXML
    public Label lblPOSTotal;

    @FXML
    public Label lblPOSBalance;

    @FXML
    public Label lblDeliveryAmount;

    @FXML
    public Label lblDeliveryTotal;

    @FXML
    public Label lblDeliveryBalance;

    @FXML
    public Label lblTotalProductSales;

    @FXML
    public Label lblTotalProductAmount;

    @FXML
    public Label lblTotalProductBalance;

    @FXML
    public Label lblTotalAmountReceived;

    @FXML
    public Label lblDate;

    @FXML
    private AnchorPane panePrint;

    final KeyCombination kb = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
    private final NodePrinter printerNode = new NodePrinter();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void print() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
       
        Paper photo = PrintHelper.createPaper("10x15", 100, 150, Units.MM);
       
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout
                = printer.createPageLayout(photo, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        double scaleX
                = pageLayout.getPrintableWidth() / panePrint.getBoundsInParent().getWidth();
        double scaleY
                = pageLayout.getPrintableHeight() / panePrint.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        panePrint.getTransforms().add(scale);

        if (job != null && job.showPrintDialog(panePrint.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, panePrint);
            if (success) {
                job.endJob();

            }
        }
        panePrint.getTransforms().remove(scale);

    }

}
