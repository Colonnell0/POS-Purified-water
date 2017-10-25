/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Colonnello
 */
public class dbConnection {

    String DBDriver = "com.mysql.jdbc.Driver";

    public Connection setConnection(Connection conn, String username, String password, String database) {
        try {
            Scanner in = new Scanner(new FileReader("ip.txt"));
            Class.forName(DBDriver);
            conn = DriverManager.getConnection(in.nextLine() + database, username, password);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please contact your administrator!");

            alert.showAndWait();
        }

        return conn;
    }
}
