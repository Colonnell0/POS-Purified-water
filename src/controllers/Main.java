package controllers;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage stage;
    private MainController main;

    @Override
    public void start(Stage stage) throws Exception {

        String[] accountDetails = LoginController.login("/views/login.fxml", "Giovanz Purified Water", 484, 318);

        if (accountDetails.length > 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            MainController controller = fxmlLoader.<MainController>getController();
            controller.setUser(Integer.parseInt(accountDetails[0]), accountDetails[1]);
            controller.usertype = Integer.parseInt(accountDetails[2]);
            Scene scene = new Scene(root);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.getIcons().add(new Image("/assets/images/giovanz.png"));

            stage.show();
        }

    }

    public static void customer(String source, String title, int w, int h) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(source));
        AnchorPane showModal = loader.load();

        Stage modal = new Stage();
        modal.setTitle(title);
        modal.getIcons().add(new Image("/assets/images/giovanz.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(w);
        modal.setMinHeight(h);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        modal.showAndWait();

    }

    public static void modal(String source, String title, int w, int h) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(source));
        AnchorPane showModal = loader.load();

        Stage modal = new Stage();
        modal.setTitle(title);
        //  adminpane.getIcons().add(new Image("/assets/images/login_header.png"));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);
        modal.initOwner(stage);
        modal.setResizable(false);
        modal.setMinWidth(w);
        modal.setMinHeight(h);
        Scene scene = new Scene(showModal);
        modal.setScene(scene);
        modal.showAndWait();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
