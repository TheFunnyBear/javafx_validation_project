package com.kosolapova.javafx.validation;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Окно для вывода ошибок
 */
public class DialogWindow extends Stage {
    public DialogWindow(String title, String message, boolean modal) {
        setTitle(title);
        initModality(modal ? Modality.APPLICATION_MODAL : Modality.NONE);

        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);
        layout.getChildren().addAll(
                new Label(message),
                new Label("Нажмите OK, чтобы закрыть.")
        );

        Scene scene = new Scene(layout, 300, 150);
        setScene(scene);
    }
}