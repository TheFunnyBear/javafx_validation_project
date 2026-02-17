module com.kosolapova.javafx.validation {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens com.kosolapova.javafx.validation to javafx.fxml;
    exports com.kosolapova.javafx.validation;
}
