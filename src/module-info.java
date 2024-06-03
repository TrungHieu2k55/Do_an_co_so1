module DuAn {
    opens Main to javafx.fxml;
    opens Client.Controller to javafx.fxml;
    opens Client.Model to javafx.base;

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;

    exports Main;
    exports Client.Controller;
}