module com.example.docs {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.docs to javafx.fxml;
    exports com.example.docs;
    exports com.example.docs.controller;
    opens com.example.docs.controller to javafx.fxml;
}