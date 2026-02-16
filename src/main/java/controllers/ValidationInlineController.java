package com.kosolapova.javafx.validation.controllers;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Контроллер для второй вкладки (подсказки рядом с полями)
 */
public class ValidationInlineController {
    @FXML TextField txtName;
    @FXML Label lblNameError;
    @FXML PasswordField pwdPassword;
    @FXML Label lblPwdError;
    @FXML CheckBox chkAgreement;
    @FXML Label lblChkError;
    @FXML ListView<String> lstSelections;
    @FXML Label lblLstError;

    @FXML
    public void initialize() {
        addListeners(); // Регистрация слушателей изменений
    }

    private void addListeners() {
        txtName.textProperty().addListener((observable, oldValue, newValue) -> checkName(newValue));
        pwdPassword.textProperty().addListener((observable, oldValue, newValue) -> checkPassword(newValue));
        chkAgreement.selectedProperty().addListener(this::checkCheckbox);
        lstSelections.getSelectionModel().selectedItemProperty().addListener(this::checkListSelection);
    }

    private void checkName(String value) {
        if(value.isBlank()) {
            lblNameError.setText("Обязательно укажите имя.");
        } else {
            lblNameError.setText("");
        }
    }

    private void checkPassword(String value) {
        if(value.length() < 8) {
            lblPwdError.setText("Минимальная длина пароля — 8 символов.");
        } else {
            lblPwdError.setText("");
        }
    }

    private void checkCheckbox(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (!newValue) {
            lblChkError.setText("Вы должны согласиться с условиями.");
        } else {
            lblChkError.setText("");
        }
    }

    private void checkListSelection(
            ObservableValue<? extends String> observable,
            String oldValue,
            String newValue
    ) {
        if (lstSelections.getSelectionModel().getSelectedIndex() != -1) {
            lblLstError.setText("");
        } else {
            lblLstError.setText("Выберите хотя бы один пункт.");
        }
    }

}