package Main;

import Client.Controller.Controller_trangchu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }
// coi đung skhoong cu
    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/Select_the_purpose.fxml"));
            primaryStage.initStyle(StageStyle.TRANSPARENT); // Tắt cửa sổ viền

            Parent selectThePurpose = loader.load();

            Scene scenePurpose = new Scene(selectThePurpose);

            scenePurpose.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
            primaryStage.setScene(scenePurpose);

            Controller_trangchu controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.show();
            primaryStage.getScene().setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            primaryStage.getScene().setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
