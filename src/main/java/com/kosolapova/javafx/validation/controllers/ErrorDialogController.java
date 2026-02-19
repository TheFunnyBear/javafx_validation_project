package com.kosolapova.javafx.validation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.List;

public class ErrorDialogController {
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnOK;

    private Stage dialogStage;

    @FXML
    private void initialize() {}

    public void setTitle(String title) {
        lblTitle.setText(title);
    }

    // Новый метод: принимает список строк
    public void setMessages(List<String> messages) {
        // Объединяем строки через перенос
        String combined = String.join("\n", messages);
        lblMessage.setText(combined);
        // Задаём красный цвет для всего текста
        lblMessage.setStyle("-fx-font-size: 14px; -fx-text-fill: #e53935; -fx-line-spacing: 5;");
    }

    @FXML
    private void onOKClicked() {
        dialogStage.close();
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }
}
