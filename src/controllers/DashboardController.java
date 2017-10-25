package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane viewFrame;


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

    public void dashboard(ActionEvent event) throws IOException {

        setDataPane(fadeAnimate("/views/DashboardContent.fxml"));

    }

    public void product(ActionEvent event) throws IOException {

        setDataPane(fadeAnimate("/views/ProductDashboard.fxml"));

    }

    public void sales(ActionEvent event) throws IOException {

        setDataPane(fadeAnimate("/views/SalesDashboard.fxml"));

    }

    public void transaction(ActionEvent event) throws IOException {

        setDataPane(fadeAnimate("/views/ViewHistory.fxml"));

    }

    public void accounts(ActionEvent event) throws IOException {

        setDataPane(fadeAnimate("/views/AccountsDashboard.fxml"));

    }

    public void customer(ActionEvent event) throws IOException {

        setDataPane(fadeAnimate("/views/CustomerDashboard.fxml"));

    }

    public void expenses(ActionEvent event) throws IOException {

        setDataPane(fadeAnimate("/views/ExpensesReports.fxml"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            setDataPane(fadeAnimate("/views/DashboardContent.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
