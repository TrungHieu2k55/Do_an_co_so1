package Client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_trangchu {
    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Pane select_customers;

    @FXML
    private Pane select_administrators;



    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    protected void PaneClicked(MouseEvent event) throws IOException {
        Pane clickedPane = (Pane) event.getSource();
        String paneId = clickedPane.getId();
        if (paneId.equals("select_customers")) {
            FXMLLoader login = new FXMLLoader(getClass().getResource("/Client/Views/login-customer.fxml"));
            Parent logins = login.load();
            Scene scene_login = new Scene(logins);
            scene_login.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
            primaryStage.setScene(scene_login);
            Login_custom controler = login.getController();
            controler.setPrimaryStage(primaryStage);
            primaryStage.centerOnScreen();
            primaryStage.getScene().setOnMousePressed(event1 -> {
                xOffset = event1.getSceneX();
                yOffset = event1.getSceneY();
            });

            primaryStage.getScene().setOnMouseDragged(event1 -> {
                primaryStage.setX(event1.getScreenX() - xOffset);
                primaryStage.setY(event1.getScreenY() - yOffset);
            });
        } else if (paneId.equals("select_administrators")) {
            FXMLLoader login_admin = new FXMLLoader(getClass().getResource("/Client/Views/login_admin.fxml"));
            Parent login_admins = login_admin.load();
            Scene scene_loginadmin = new Scene(login_admins);
            scene_loginadmin.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
            primaryStage.setScene(scene_loginadmin);
            Login_admin controler = login_admin.getController();
            controler.setPrimaryStage(primaryStage);
            primaryStage.centerOnScreen();
            primaryStage.getScene().setOnMousePressed(event1 -> {
                xOffset = event1.getSceneX();
                yOffset = event1.getSceneY();
            });

            primaryStage.getScene().setOnMouseDragged(event1 -> {
                primaryStage.setX(event1.getScreenX() - xOffset);
                primaryStage.setY(event1.getScreenY() - yOffset);
            });
        }

    }

    public void minimize(ActionEvent event) {
        primaryStage.setIconified(true);
    }

    public void close(ActionEvent event) {
        System.exit(0);
    }
}
