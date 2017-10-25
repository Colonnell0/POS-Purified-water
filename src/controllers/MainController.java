package controllers;

import static controllers.CustomerController.modal;
import static controllers.Main.stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import libs.FormValidation;
import libs.FormatNumber;
import models.POS_model;

public class MainController extends POS_model implements Initializable {

    @FXML
    private AnchorPane viewFrame;

    @FXML
    private Label lblLoginName;

    @FXML
    public Label lblDateTime;

    private FormValidation formVal;

    public int account_id;

    public String account_name;

    public int usertype = 0;

    Date dNow = new Date();
    SimpleDateFormat ft
            = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");

    Date dNowThread = new Date();
    SimpleDateFormat date
            = new SimpleDateFormat("EEEE dd MMMM yyyy");

    FormatNumber formatNumber = new FormatNumber();

    class DigitalClock {

        public DigitalClock() {
            bindToTime();
        }

        // the digital clock updates once a second.
        private void bindToTime() {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            Calendar time = Calendar.getInstance();
                            String hourString = StringUtilities.pad(2, ' ', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                            String minuteString = StringUtilities.pad(2, '0', time.get(Calendar.MINUTE) + "");
                            String secondString = StringUtilities.pad(2, '0', time.get(Calendar.SECOND) + "");

                            String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
                            lblDateTime.setText(date.format(dNowThread) + "    " + hourString + ":" + minuteString + ":" + secondString + " " + ampmString);
                        }
                    }
                    ),
                    new KeyFrame(Duration.seconds(1))
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    public void setDataPane(Node nodes) {
        viewFrame.getChildren().clear();
        viewFrame.getChildren().addAll(nodes);

    }

    public AnchorPane fadeAnimate(String url) throws IOException {
        AnchorPane view = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200));
        fadeTransition.setNode(view);
        fadeTransition.setFromValue(0.1);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();
        return view;
    }

    public void POS() throws IOException {
        if (usertype > 0) {

            setDataPane(fadeAnimate("/views/POS.fxml"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }
    }

    public void findCustomer(ActionEvent event) throws IOException {
        if (usertype > 1) {
            setDataPane(fadeAnimate("/views/FindCustomer.fxml"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    public void generateSales(ActionEvent event) throws IOException {
        if (usertype > 1) {
            String result_2[][] = viewAllCustomerProfile(lblLoginName.getText(), "POS");
            String result_3[][] = viewAllCustomerProfile(lblLoginName.getText(), "Delivery");
            if (result_2.length > 0 || result_3.length > 0) {
                double numberTotal = 0;

                double posAmount = 0;
                double posBalance = 0;
                double posTotal = 0;

                double deliveryAmount = 0;
                double deliveryBalance = 0;
                double deliveryTotal = 0;

                Date dNow = new Date();
                SimpleDateFormat ft
                        = new SimpleDateFormat("E MM/dd/yyyy 'at' hh:mm:ss a");

                modal = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/GenerateSales.fxml"));
                AnchorPane showModal = loader.load();

                modal.setTitle("Print preview");
                //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
                modal.initModality(Modality.APPLICATION_MODAL);
                modal.initStyle(StageStyle.UTILITY);

                modal.initOwner(stage);
                modal.setResizable(false);
                modal.setMinWidth(650);
                modal.setMinHeight(500);
                Scene scene = new Scene(showModal);
                modal.setScene(scene);
                GenerateSalesController controller = loader.<GenerateSalesController>getController();

                String result[][] = getAccountDetails(lblLoginName.getText());

                controller.lblCashierName_1.setText(result[0][1]);
                controller.lblCashierName_2.setText(result[0][1]);
                controller.lblAddress.setText(result[0][2]);
                controller.lblContact.setText(result[0][3]);
                controller.lblDate.setText(ft.format(dNow));

                if (result_2.length > 0) {

                    controller.lblNumberOfRoundPOS.setText(result_2[0][0]);
                    controller.lblNumberOfSlimPOS.setText(result_2[0][1]);
                    controller.lblNumberOfOthersPOS.setText(result_2[0][2]);
                    controller.lblPOSAmount.setText(result_2[0][3]);
                    controller.lblPOSTotal.setText(result_2[0][4]);
                    controller.lblPOSBalance.setText(result_2[0][5]);
                    numberTotal += Double.parseDouble(result_2[0][0]);
                    numberTotal += Double.parseDouble(result_2[0][1]);
                    numberTotal += Double.parseDouble(result_2[0][2]);
                    posTotal = Double.parseDouble(result_2[0][4]);
                    posAmount = Double.parseDouble(result_2[0][3]);
                    posBalance = Double.parseDouble(result_2[0][5]);
                }

                if (result_3.length > 0) {
                    controller.lblNumberOfRoundDelivery.setText(result_3[0][0]);
                    controller.lblNumberOfSlimDelivery.setText(result_3[0][1]);
                    controller.lblNumberOfOthersDelivery.setText(result_3[0][2]);
                    controller.lblDeliveryAmount.setText(result_3[0][3]);
                    controller.lblDeliveryTotal.setText(result_3[0][4]);
                    controller.lblDeliveryBalance.setText(result_3[0][5]);
                    numberTotal += Double.parseDouble(result_3[0][0]);
                    numberTotal += Double.parseDouble(result_3[0][1]);
                    numberTotal += Double.parseDouble(result_3[0][2]);
                    deliveryTotal = Double.parseDouble(result_3[0][4]);
                    deliveryAmount = Double.parseDouble(result_3[0][3]);
                    deliveryBalance = Double.parseDouble(result_3[0][5]);

                }

                double totalSales = posTotal + deliveryTotal;
                double totalAmount = posAmount + deliveryAmount;
                double totalBalance = posBalance + deliveryBalance;

                controller.lblNumberTotal.setText(formatNumber.formatNumbers("" + numberTotal));
                controller.lblTotalProductSales.setText(formatNumber.formatNumbers("Product sales: " + totalSales));
                controller.lblTotalProductAmount.setText(formatNumber.formatNumbers("Amount : " + totalAmount));
                controller.lblTotalProductBalance.setText(formatNumber.formatNumbers("Balance : " + totalBalance));
                controller.lblTotalAmountReceived.setText(formatNumber.formatNumbers("Php " + totalAmount));

                modal.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(null);
                alert.setContentText("NO TRANSACTION");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    public void expensesReports(ActionEvent event) throws IOException {
        if (usertype == 2 | usertype == 3) {
            setDataPane(fadeAnimate("/views/ExpensesReports.fxml"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    public void collectionCollectibles(ActionEvent event) throws IOException {
        if (usertype == 2 | usertype == 3) {
            setDataPane(fadeAnimate("/views/CollectionCollectibles.fxml"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    public void dashboard(ActionEvent event) throws IOException {

        if (usertype == 1 | usertype == 3) {
            setDataPane(fadeAnimate("/views/Dashboard.fxml"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }

    }

    public void addCustomer(ActionEvent event) throws IOException {
        if (usertype == 2 | usertype == 3) {
            Main.customer("/views/AddCustomer.fxml", "Giovanz Purified Water | Add Customer", 600, 700);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("You're not allowed of this module");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(" -fx-font-size: 12px;\n"
                    + "   -fx-font-weight: bold;");
            alert.showAndWait();
        }
    }

    public void directPOS(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.F1) {
            try {
                if (usertype > 0) {
                    setDataPane(fadeAnimate("/views/POS.fxml"));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setContentText("You're not allowed of this module");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setStyle(" -fx-font-size: 12px;\n"
                            + "   -fx-font-weight: bold;");
                    alert.showAndWait();
                }

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        if (event.getCode() == KeyCode.F2) {
            if (usertype == 1 | usertype == 3) {
                setDataPane(fadeAnimate("/views/Dashboard.fxml"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }

        }
        if (event.getCode() == KeyCode.F3) {
            if (usertype == 2 | usertype == 3) {
                setDataPane(fadeAnimate("/views/ExpensesReports.fxml"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }

        }
        if (event.getCode() == KeyCode.F4) {
            if (usertype == 2 | usertype == 3) {
                setDataPane(fadeAnimate("/views/CollectionCollectibles.fxml"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }

        }

//-------------------------------------------------------------------------------------------------
        if (event.getCode() == KeyCode.F5) {
            if (usertype == 2 | usertype == 3) {
                Main.customer("/views/AddCustomer.fxml", "Giovanz Purified Water | Add Customer", 600, 700);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }
        }
        if (event.getCode() == KeyCode.F6) {
            try {
                if (usertype == 2 | usertype == 3) {
                    setDataPane(fadeAnimate("/views/FindCustomer.fxml"));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setContentText("You're not allowed of this module");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setStyle(" -fx-font-size: 12px;\n"
                            + "   -fx-font-weight: bold;");
                    alert.showAndWait();
                }

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        if (event.getCode() == KeyCode.F7) {

            if (usertype > 1) {
                String result_2[][] = viewAllCustomerProfile(lblLoginName.getText(), "POS");
                String result_3[][] = viewAllCustomerProfile(lblLoginName.getText(), "Delivery");
                if (result_2.length > 0 || result_3.length > 0) {
                    double numberTotal = 0;

                    double posAmount = 0;
                    double posBalance = 0;
                    double posTotal = 0;

                    double deliveryAmount = 0;
                    double deliveryBalance = 0;
                    double deliveryTotal = 0;

                    Date dNow = new Date();
                    SimpleDateFormat ft
                            = new SimpleDateFormat("E MM/dd/yyyy 'at' hh:mm:ss a");

                    modal = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("/views/GenerateSales.fxml"));
                    AnchorPane showModal = loader.load();

                    modal.setTitle("Print preview");
                    //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
                    modal.initModality(Modality.APPLICATION_MODAL);
                    modal.initStyle(StageStyle.UTILITY);

                    modal.initOwner(stage);
                    modal.setResizable(false);
                    modal.setMinWidth(650);
                    modal.setMinHeight(500);
                    Scene scene = new Scene(showModal);
                    modal.setScene(scene);
                    GenerateSalesController controller = loader.<GenerateSalesController>getController();

                    String result[][] = getAccountDetails(lblLoginName.getText());

                    controller.lblCashierName_1.setText(result[0][1]);
                    controller.lblCashierName_2.setText(result[0][1]);
                    controller.lblAddress.setText(result[0][2]);
                    controller.lblContact.setText(result[0][3]);
                    controller.lblDate.setText(ft.format(dNow));

                    if (result_2.length > 0) {

                        controller.lblNumberOfRoundPOS.setText(result_2[0][0]);
                        controller.lblNumberOfSlimPOS.setText(result_2[0][1]);
                        controller.lblNumberOfOthersPOS.setText(result_2[0][2]);
                        controller.lblPOSAmount.setText(result_2[0][3]);
                        controller.lblPOSTotal.setText(result_2[0][4]);
                        controller.lblPOSBalance.setText(result_2[0][5]);
                        numberTotal += Double.parseDouble(result_2[0][0]);
                        numberTotal += Double.parseDouble(result_2[0][1]);
                        numberTotal += Double.parseDouble(result_2[0][2]);
                        posTotal = Double.parseDouble(result_2[0][4]);
                        posAmount = Double.parseDouble(result_2[0][3]);
                        posBalance = Double.parseDouble(result_2[0][5]);
                    }

                    if (result_3.length > 0) {
                        controller.lblNumberOfRoundDelivery.setText(result_3[0][0]);
                        controller.lblNumberOfSlimDelivery.setText(result_3[0][1]);
                        controller.lblNumberOfOthersDelivery.setText(result_3[0][2]);
                        controller.lblDeliveryAmount.setText(result_3[0][3]);
                        controller.lblDeliveryTotal.setText(result_3[0][4]);
                        controller.lblDeliveryBalance.setText(result_3[0][5]);
                        numberTotal += Double.parseDouble(result_3[0][0]);
                        numberTotal += Double.parseDouble(result_3[0][1]);
                        numberTotal += Double.parseDouble(result_3[0][2]);
                        deliveryTotal = Double.parseDouble(result_3[0][4]);
                        deliveryAmount = Double.parseDouble(result_3[0][3]);
                        deliveryBalance = Double.parseDouble(result_3[0][5]);

                    }

                    double totalSales = posTotal + deliveryTotal;
                    double totalAmount = posAmount + deliveryAmount;
                    double totalBalance = posBalance + deliveryBalance;

                    controller.lblNumberTotal.setText(formatNumber.formatNumbers("" + numberTotal));
                    controller.lblTotalProductSales.setText(formatNumber.formatNumbers("Product sales: " + totalSales));
                    controller.lblTotalProductAmount.setText(formatNumber.formatNumbers("Amount : " + totalAmount));
                    controller.lblTotalProductBalance.setText(formatNumber.formatNumbers("Balance : " + totalBalance));
                    controller.lblTotalAmountReceived.setText(formatNumber.formatNumbers("Php " + totalAmount));

                    modal.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(null);
                    alert.setContentText("NO TRANSACTION");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setStyle(" -fx-font-size: 12px;\n"
                            + "   -fx-font-weight: bold;");
                    alert.showAndWait();

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("You're not allowed of this module");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle(" -fx-font-size: 12px;\n"
                        + "   -fx-font-weight: bold;");
                alert.showAndWait();
            }
        }

    }

    public void logout() throws IOException {
        setDataPane(fadeAnimate("/views/POS.fxml"));
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat datetime
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String column[] = {"account_id", "ipaddress", "date_login", "account_status"};
        String values[] = {"" + account_id, InetAddress.getLocalHost().toString(), datetime.format(dt), " "};

        updateAccountLogs(column, values, "" + account_id);

        String[] accountDetails = LoginController.login("/views/login.fxml", "Giovanz Purified Water", 484, 318);

        if (accountDetails.length > 0) {
            setUser(Integer.parseInt(accountDetails[0]), accountDetails[1]);
            usertype = Integer.parseInt(accountDetails[2]);
        }
    }

    public void setUser(int id, String accountName) throws UnknownHostException {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat datetime
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        account_id = id;
        lblLoginName.setText(accountName);

        String column[] = {"account_id", "ipaddress", "date_login", "account_status"};
        String values[] = {"" + account_id, InetAddress.getLocalHost().toString(), datetime.format(dt), "Available"};
        setAccountLogs("account_logs", column, values);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DigitalClock clock = new DigitalClock();
        clock.bindToTime();
        try {

            setDataPane(fadeAnimate("/views/POS.fxml"));

        } catch (IOException e) {
            System.out.println(e);

        }

    }

}

class StringUtilities {

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
