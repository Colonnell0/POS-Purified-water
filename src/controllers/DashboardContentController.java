/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import libs.FormatNumber;
import models.Account_table_Model;
import models.Dashboard_Model;

/**
 * FXML Controller class
 *
 * @author Colonnello
 */
public class DashboardContentController extends Dashboard_Model implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String date = "monthly";
    private String lblDate = "Month";

    class DashboardFrameData {

        public DashboardFrameData() {
            dashboard();
        }

        // the digital clock updates once a second.
        private void dashboard() {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            tabulateMothlyIncome.getData().clear();
                            Calendar calender = Calendar.getInstance();
                            int year = calender.get(Calendar.YEAR);

                            tabulateMothlyIncome.setTitle("Giovanz " + date + " Income in " + year);
                            XYChart.Series<String, Number> amount;
                            amount = new XYChart.Series<>();
                            XYChart.Series<String, Number> balance = new XYChart.Series<>();
                            XYChart.Series<String, Number> totalItem = new XYChart.Series<>();
                            String resultAmount[][] = getTabulateMontylData("" + year, date);
                            if (resultAmount.length > 0) {
                                for (int i = 0; i < resultAmount.length; i++) {
                                    amount.getData().add(new XYChart.Data<>(resultAmount[i][0], Double.parseDouble(resultAmount[i][1])));
                                    balance.getData().add(new XYChart.Data<>(resultAmount[i][0], Double.parseDouble(resultAmount[i][2])));
                                    totalItem.getData().add(new XYChart.Data<>(resultAmount[i][0], Double.parseDouble(resultAmount[i][3])));

                                }
                            }

                            amount.setName("Total amount");
                            tabulateMothlyIncome.getData().add(amount);

                            balance.setName("Total balance");
                            tabulateMothlyIncome.getData().add(balance);

                            totalItem.setName("Total Sold");
                            tabulateMothlyIncome.getData().add(totalItem);
                            tabulateMothlyIncome.getXAxis().setLabel(lblDate);
                            tabulateMothlyIncome.getYAxis().setLabel("Total amount and balance");

                            String total[][] = getTotal("" + year);
                            lblTotalAmount.setText("Php " + formatNumber.formatNumbers("" + total[0][0]));
                            lblTotalBalance.setText("Php " + formatNumber.formatNumbers(total[0][1]));
                            lblTotalSold.setText("Php " + formatNumber.formatNumbers(total[0][2]));

                            tblAccount.getItems().clear();
                            tblCAccountName.setCellValueFactory(new PropertyValueFactory<>("AccountName"));
                            tblCPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
                            tblCDateCreated.setCellValueFactory(new PropertyValueFactory<>("DateCreated"));
                            tblCStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
                            tblAccount.setItems(data);

                            String res[][] = getAccount();
                            if (res != null) {
                                for (String[] re : res) {
                                    Account_table_Model entry = new Account_table_Model(
                                            re[0],
                                            re[1],
                                            re[2],
                                            re[3], 0, "", "", "", "", "");//status

                                    data.add(entry);

                                }

                            }

                        }
                    }
                    ),
                    new KeyFrame(Duration.seconds(10))
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    @FXML
    private LineChart<String, Number> tabulateMothlyIncome;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private Label lblTotalBalance;

    @FXML
    private Label lblTotalSold;

    @FXML
    private ComboBox cmbDate;

    private FormatNumber formatNumber = new FormatNumber();

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

    //--------------------------------------------------------------------------
    final ObservableList<Account_table_Model> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DashboardFrameData dash = new DashboardFrameData();
        dash.dashboard();
        List<String> list = new ArrayList<>();
        list.add("Daily");
        list.add("Weekly");
        list.add("Monthly");
        ObservableList obList = FXCollections.observableList(list);

        cmbDate.setItems(obList);

    }

    public void cmbsetDate() {
        date = cmbDate.getValue().toString();
        if (date.equalsIgnoreCase("Monthly")) {
            lblDate = "Months";
        }
        if (date.equalsIgnoreCase("Daily")) {
            lblDate = "Dates";
        }
        if (date.equalsIgnoreCase("Weekly")) {
            lblDate = "Dates";
        }
        tabulateMothlyIncome.getData().clear();
        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);

        tabulateMothlyIncome.setTitle("Giovanz " + date + " Income in " + year);
        XYChart.Series<String, Number> amount;
        amount = new XYChart.Series<>();
        XYChart.Series<String, Number> balance = new XYChart.Series<>();
        XYChart.Series<String, Number> totalItem = new XYChart.Series<>();
        String resultAmount[][] = getTabulateMontylData("" + year, date);
        if (resultAmount.length > 0) {
            for (int i = 0; i < resultAmount.length; i++) {
                amount.getData().add(new XYChart.Data<>(resultAmount[i][0], Double.parseDouble(resultAmount[i][1])));
                balance.getData().add(new XYChart.Data<>(resultAmount[i][0], Double.parseDouble(resultAmount[i][2])));
                totalItem.getData().add(new XYChart.Data<>(resultAmount[i][0], Double.parseDouble(resultAmount[i][3])));

            }
        }

        amount.setName("Total amount");
        tabulateMothlyIncome.getData().add(amount);

        balance.setName("Total balance");
        tabulateMothlyIncome.getData().add(balance);

        totalItem.setName("Total Sold");
        tabulateMothlyIncome.getData().add(totalItem);
        tabulateMothlyIncome.getXAxis().setLabel(lblDate);
        tabulateMothlyIncome.getYAxis().setLabel("Total amount and balance");

        String total[][] = getTotal("" + year);
        lblTotalAmount.setText("Php " + formatNumber.formatNumbers("" + total[0][0]));
        lblTotalBalance.setText("Php " + formatNumber.formatNumbers(total[0][1]));
        lblTotalSold.setText("Php " + formatNumber.formatNumbers(total[0][2]));

        tblAccount.getItems().clear();
        tblCAccountName.setCellValueFactory(new PropertyValueFactory<>("AccountName"));
        tblCPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
        tblCDateCreated.setCellValueFactory(new PropertyValueFactory<>("DateCreated"));
        tblCStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        tblAccount.setItems(data);

        String res[][] = getAccount();
        if (res != null) {
            for (String[] re : res) {
                Account_table_Model entry = new Account_table_Model(
                        re[0],
                        re[1],
                        re[2],
                        re[3], 0, "", "", "", "", "");//status

                data.add(entry);

            }

        }

    }

}

class DashboardUtilites {

    /**
     * Creates a string left padded to the specified width with the supplied
     * padding character.
     *
     * @param fieldWidth the length of the resultant padded string.
     * @param padChar a character to use for padding the string.
     * @param s the string to be padded.
     * @return the padded string.
     */
    public static String pad(int fieldWidth, char padChar, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            sb.append(padChar);
        }
        sb.append(s);

        return sb.toString();
    }
}
