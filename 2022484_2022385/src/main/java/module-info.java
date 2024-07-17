module com.example.demo6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.swing;
    requires junit;


    opens com.example.demo6 to javafx.fxml;
    exports com.example.demo6;
}