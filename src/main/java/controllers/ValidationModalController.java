package com.kosolapova.javafx.validation.controllers;

import com.kosolapova.javafx.validation.DialogWindow;
import com.kosolapova.javafx.validation.UserDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    private void initialize() {
        // Заполняем список тестовыми пунктами
        for (int i = 1; i <= 15; i++) {
            lstSelections.getItems().add("Тестовый пункт " + i);
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные введены корректно!");
            alert.showAndWait();
        } else {
            Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            DialogWindow dialog = new DialogWindow(
                    ownerStage,
                    "Ошибка валидации",
                    errors.toString(),
                    true
            );
            dialog.showAndWait();
        }
    }
}