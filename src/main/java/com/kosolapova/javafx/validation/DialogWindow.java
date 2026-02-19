package com.kosolapova.javafx.validation;

import com.kosolapova.javafx.validation.controllers.ErrorDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

import java.util.List;

public class DialogWindow {
    private final Stage dialogStage;
    private final ErrorDialogController controller;

    public DialogWindow(Stage owner, String title, List<String> messages) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/error_dialog.fxml")
            );
            Parent root = loader.load();
            controller = loader.getController();

            dialogStage = new Stage();
            dialogStage.initOwner(owner);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setTitle(title);

            controller.setDialogStage(dialogStage);
            controller.setTitle(title);
            controller.setMessages(messages);  // Передаём список

            Scene scene = new Scene(root, 550, 400);

            URL cssUrl = getClass().getResource("/styles/styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("CSS не найден: /styles/styles.css");
            }
            dialogStage.setScene(scene);
            dialogStage.setWidth(550);
            dialogStage.setHeight(400);
            dialogStage.setResizable(false);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки FXML диалога", e);
        }
    }

    public void showAndWait() {
        dialogStage.showAndWait();
    }
}
