package com.kosolapova.javafx.validation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorDialogController {

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnOK;

    private Stage dialogStage;

    // Инициализация после загрузки FXML
    @FXML
    private void initialize() {
        // Можно добавить дополнительные настройки при необходимости
    }

    // Сеттеры для данных
    public void setTitle(String title) {
        lblTitle.setText(title);
    }

    public void setMessage(String message) {
        lblMessage.setText(message);
    }

    // Обработчик кнопки OK
    @FXML
    private void onOKClicked() {
        dialogStage.close();
    }

    // Установка Stage (для управления окном)
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }
}
