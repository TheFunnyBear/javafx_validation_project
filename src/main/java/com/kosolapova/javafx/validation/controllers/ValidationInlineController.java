package com.kosolapova.javafx.validation.controllers;

import com.kosolapova.javafx.validation.DialogWindow;
import com.kosolapova.javafx.validation.UserDataModel;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

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
    @FXML Button btnSubmit;

    @FXML
    public void initialize() {

        // Заполняем список тестовыми пунктами
        for (int i = 1; i <= 15; i++) {
            lstSelections.getItems().add("Тестовый пункт " + i);
        }

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

    private void checkCheckboxIsSelected() {
        if (!chkAgreement.isSelected()) {
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


    private void checkListSelection(
    ) {
        if (lstSelections.getSelectionModel().getSelectedIndex() != -1) {
            lblLstError.setText("");
        } else {
            lblLstError.setText("Выберите хотя бы один пункт.");
        }
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
        UserDataModel data = new UserDataModel();
        data.setName(txtName.getText());
        data.setPassword(pwdPassword.getText());
        data.setSelectedItems(lstSelections.getSelectionModel().getSelectedItems());
        data.setIsAgreed(chkAgreement.isSelected());

        List<String> errors = data.validate();
        if(errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Проверка пройдена");
            alert.setContentText("Данные введены корректно!");

            Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            alert.initOwner(ownerStage);

            alert.showAndWait();
        } else {
            checkName(txtName.getText());
            checkPassword(pwdPassword.getText());
            checkCheckboxIsSelected();
            checkListSelection();
        }
    }

}