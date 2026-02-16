package com.kosolapova.javafx.validation.controllers;

import com.kosolapova.javafx.validation.DialogWindow;
import com.kosolapova.javafx.validation.UserDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

/**
 * Контроллер для первой вкладки (модальные окна)
 */
public class ValidationModalController {
    @FXML TextField txtName;
    @FXML PasswordField pwdPassword;
    @FXML CheckBox chkAgreement;
    @FXML Button btnSubmit;
    @FXML ListView<String> lstSelections;

    @FXML
    protected void handleSubmit(ActionEvent event) {
        UserDataModel data = new UserDataModel();
        data.setName(txtName.getText());
        data.setPassword(pwdPassword.getText());
        data.setSelectedItems(lstSelections.getSelectionModel().getSelectedItems());
        data.setIsAgreed(chkAgreement.isSelected());

        List<String> errors = data.validate();
        if(errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные введены корректно!");
            alert.showAndWait();
        } else {
            DialogWindow dialog = new DialogWindow("Ошибка валидации", errors.toString(), true);
            dialog.showAndWait();
        }
    }
}