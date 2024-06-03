package Client.Controller;

import Client.Model.Address;
import Client.Socket.Client;
import Server.Database;
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
import java.net.Socket;
import java.sql.Connection;

public class Sign_up {
    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;
    private int port ;
    private String ip;
    @FXML
    private PasswordField con_password;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    public Sign_up() {
        Address address = new Address();
        this.port = address.PORT;
        this.ip = address.ipv4Address;
    }

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
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/login-customer.fxml"));
            Parent login = loader.load();
            Scene scene_login = new Scene(login);
            scene_login.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
            Login_custom controllerLogin = loader.getController();
            controllerLogin.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene_login);
            primaryStage.centerOnScreen();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void Login(ActionEvent event){
        Database database = new Database();
        Connection connection = database.getConection();
        if (!password.getText().equals(con_password.getText())) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "The passwords do not match.");
        } else if (username.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No empty", "Username cannot be empty.");
        } else if (email.getText().trim().isEmpty() || !email.getText().contains("@")) {
            showAlert(Alert.AlertType.WARNING, "Invalid email", "Please enter a valid email address.");
        } else {
            Platform.runLater(()-> {
                Client client = new Client();
                Socket socket = client.getSocket();
                try (
                     PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    String request = "SIGNUP " + username.getText() + " " + password.getText() + " " + email.getText();
                    output.println(request);
                    String response = input.readLine();
                    if("EXIST".equals(response)){
                        showAlert(Alert.AlertType.WARNING, "Exist", "Account Exist!");
                    }
                    else if ("SIGNUP_SUCCESS".equals(response)) {
                        showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "Account created successfully!");
                        // Load login scene
                        try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/GUI_TICKETBOOK.fxml"));
                    Parent gui_ticketbook = loader.load();
                    Scene scene_ticketbook = new Scene(gui_ticketbook);
                    scene_ticketbook.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
                    Ticket_Book controller_ticket = loader.getController();
                    controller_ticket.setPrimaryStage(primaryStage);
                    primaryStage.setScene(scene_ticketbook);
                    primaryStage.centerOnScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Account creation failed.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        primaryStage.getScene().setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });

        primaryStage.getScene().setOnMouseDragged(event1 -> {
            primaryStage.setX(event1.getScreenX() - xOffset);
            primaryStage.setY(event1.getScreenY() - yOffset);
        });
    }
//    public void Login(ActionEvent event) {
//        Database database = new Database();
//        Connection connection = database.getConection();
//
//        if (!password.getText().equals(con_password.getText())) {
//            showAlert(Alert.AlertType.ERROR, "Password is again", "The password is wrong again");
//        }else if(username.getText().trim().equals("")){
//            showAlert(Alert.AlertType.WARNING,"No empty","No empty");
//        } else if (email.getText().trim().equals("")||!email.getText().contains("@gmail.com")) {
//            showAlert(Alert.AlertType.WARNING,"No empty","No empty");
//        } else {
//            try {
//                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO khachhang (Username, Password,Email) VALUES (?, ?, ?)");
//
//                // Thiết lập giá trị cho các tham số trong câu lệnh SQL
//                preparedStatement.setString(1, username.getText());
//                preparedStatement.setString(2, password.getText());
//                preparedStatement.setString(3, email.getText());
//                // Thực thi câu lệnh SQL
//                preparedStatement.executeUpdate();
//                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Logged in successfully!");
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/GUI_TICKETBOOK.fxml"));
//                    Parent gui_ticketbook = loader.load();
//                    Scene scene_ticketbook = new Scene(gui_ticketbook);
//                    scene_ticketbook.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
//                    Ticket_Book controller_ticket = loader.getController();
//                    controller_ticket.setPrimaryStage(primaryStage);
//                    primaryStage.setScene(scene_ticketbook);
//                    primaryStage.centerOnScreen();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
