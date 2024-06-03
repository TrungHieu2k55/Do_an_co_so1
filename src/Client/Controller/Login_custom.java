package Client.Controller;

import Client.Model.Address;
import Client.Socket.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Login_custom {
    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;
    public static String user = "";
    @FXML
    public  TextField username_email;

    @FXML
    private PasswordField password_login;
    @FXML
    private TextField passwordVisibleText;

    private boolean isPasswordVisible = false;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void minimize(ActionEvent event) {
        primaryStage.setIconified(true);
    }

    public void close(ActionEvent event) {
        System.exit(0);
    }

    public void Back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/Select_the_purpose.fxml"));
            Parent select_the_purpose = loader.load();
            Scene scene_purpose = new Scene(select_the_purpose);
            scene_purpose.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
            Controller_trangchu controllerTrangchu = loader.getController();
            controllerTrangchu.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene_purpose);
            primaryStage.centerOnScreen();
            primaryStage.getScene().setOnMousePressed(event1 -> {
                xOffset = event1.getSceneX();
                yOffset = event1.getSceneY();
            });

            primaryStage.getScene().setOnMouseDragged(event1 -> {
                primaryStage.setX(event1.getScreenX() - xOffset);
                primaryStage.setY(event1.getScreenY() - yOffset);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sign_upcontroller(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/signup_cus.fxml"));
            Parent signup_cus = loader.load();
            Scene scene_signup = new Scene(signup_cus);
            scene_signup.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
            Sign_up controller_signup = loader.getController();
            controller_signup.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene_signup);
            primaryStage.centerOnScreen();
            primaryStage.getScene().setOnMousePressed(event1 -> {
                xOffset = event1.getSceneX();
                yOffset = event1.getSceneY();
            });

            primaryStage.getScene().setOnMouseDragged(event1 -> {
                primaryStage.setX(event1.getScreenX() - xOffset);
                primaryStage.setY(event1.getScreenY() - yOffset);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void login_cus(ActionEvent event) {

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                // Gửi yêu cầu đăng nhập
                String request = "LOGIN " + username_email.getText() + " " + password_login.getText();
                System.out.println("Đang gửi yêu cầu đăng nhập: " + request);
                output.println(request);

                // Nhận phản hồi
                String response = input.readLine();
                System.out.println("Đã nhận phản hồi từ server: " + response);

                Platform.runLater(() -> {
                    if ("LOGIN_SUCCESS".equals(response)) {
                        user = username_email.getText();
                        showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Logged in successfully!");
                        // Load next scene
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/GUI_TICKETBOOK.fxml"));
                            Parent gui_ticketbook = loader.load();
                            Scene scene_ticketbook = new Scene(gui_ticketbook);
                            scene_ticketbook.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
                            Ticket_Book controller_ticket = loader.getController();
                            controller_ticket.setPrimaryStage(primaryStage);
                            primaryStage.setScene(scene_ticketbook);
                            primaryStage.centerOnScreen();
                            primaryStage.getScene().setOnMousePressed(event1 -> {
                                xOffset = event1.getSceneX();
                                yOffset = event1.getSceneY();
                            });

                            primaryStage.getScene().setOnMouseDragged(event1 -> {
                                primaryStage.setX(event1.getScreenX() - xOffset);
                                primaryStage.setY(event1.getScreenY() - yOffset);
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
                    }
                });

            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void Btn_Hide(ActionEvent event) {
        isPasswordVisible = !isPasswordVisible;
        if (isPasswordVisible) {
            passwordVisibleText.setText(password_login.getText());
            passwordVisibleText.setVisible(true);
            password_login.setVisible(false);
        } else {
            password_login.setText(passwordVisibleText.getText());
            password_login.setVisible(true);
            passwordVisibleText.setVisible(false);
        }
    }


}
