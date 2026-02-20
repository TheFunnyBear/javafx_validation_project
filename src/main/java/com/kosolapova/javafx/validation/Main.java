package com.kosolapova.javafx.validation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

public class Main extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double DECAY_RATE = 0.95;
    private static final long UPDATE_INTERVAL_MS = 1000 / 60L;
    private static final long STATIONARY_THRESHOLD_MS = 500;

    private XYChart.Series<Number, Number> heatMapSeries;
    private boolean recording = true;
    private double lastMouseX, lastMouseY;
    private long lastActivityTime;

    @Override
    public void start(Stage primaryStage) throws Exception {
        logInfo("Запуск приложения.");

        TabPane tabPane = new TabPane();
        createTabs(tabPane);

        Scene scene = new Scene(new BorderPane(tabPane), WIDTH, HEIGHT);
        primaryStage.setTitle("Тепловая карта");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createTabs(TabPane tabPane) {
        // Вкладка редактирования
        Tab firstTab = new Tab("Редактирование");
        TextField textField = new TextField();
        Button button = new Button("Ок");
        HBox hbox = new HBox(textField, button);
        VBox vbox = new VBox(hbox);
        vbox.setPadding(new Insets(10));
        firstTab.setContent(vbox);

        vbox.addEventHandler(MouseEvent.ANY, this::handleMouse);

        button.setOnAction(e -> {
            recording = false;
            logInfo("Нажата кнопка 'Ок'. Запись карты остановлена.");
        });

// Вкладка тепловой карты
        Tab secondTab = new Tab("Тепловая карта");

        NumberAxis xAxis = new NumberAxis(0, WIDTH, 50);
        NumberAxis yAxis = new NumberAxis(HEIGHT, 0, 50); // Инвертированная ось Y
        xAxis.setLabel("X координата");
        yAxis.setLabel("Y координата");

        heatMapSeries = new XYChart.Series<>();

        javafx.scene.chart.ScatterChart<Number, Number> heatMapChart =
                new javafx.scene.chart.ScatterChart<>(xAxis, yAxis);
        heatMapChart.setTitle("Тепловая карта активности");
        heatMapChart.getData().add(heatMapSeries);

// Создаём кнопки
        Button startButton = new Button("Начать запись");
        Button stopButton = new Button("Остановить запись");
        Button clearButton = new Button("Очистить карту");
        Button saveButton = new Button("Сохранить в PNG");

// Обработчики кнопок
        startButton.setOnAction(e -> {
            recording = true;
            logInfo("Запись тепловой карты начата.");
        });

        stopButton.setOnAction(e -> {
            recording = false;
            logInfo("Запись тепловой карты остановлена.");
        });

        clearButton.setOnAction(e -> {
            Platform.runLater(() -> {
                heatMapSeries.getData().clear();
                logInfo("Тепловая карта очищена.");
            });
        });

        saveButton.setOnAction(e -> saveHeatMapAsPNG(heatMapChart));

        // Панель для кнопок
        HBox buttonBar = new HBox(10, startButton, stopButton, clearButton, saveButton);
        buttonBar.setPadding(new Insets(10));

        // Основной контейнер для вкладки
        VBox chartContainer = new VBox(buttonBar, heatMapChart);
        secondTab.setContent(chartContainer);

        tabPane.getTabs().addAll(firstTab, secondTab);

        runRenderingLoop();

    }

    private void handleMouse(MouseEvent e) {
        if (!recording || e.getEventType() != MouseEvent.MOUSE_MOVED) return;

        lastMouseX = e.getSceneX();
        lastMouseY = e.getSceneY();
        lastActivityTime = System.currentTimeMillis();
        updateHeatMap(lastMouseX, lastMouseY);
    }

    private void saveHeatMapAsPNG(javafx.scene.chart.ScatterChart<Number, Number> chart) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить тепловую карту как PNG");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                // Создаём снимок графика
                WritableImage image = chart.snapshot(new SnapshotParameters(), null);

                // Получаем буфер данных изображения
                javafx.scene.image.PixelReader pixelReader = image.getPixelReader();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                // Создаём BufferedImage напрямую
                BufferedImage bufferedImage = new BufferedImage(
                        width, height, BufferedImage.TYPE_INT_ARGB);

                // Копируем пиксели из WritableImage в BufferedImage
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int argb = pixelReader.getArgb(x, y);
                        bufferedImage.setRGB(x, y, argb);
                    }
                }

                // Сохраняем в файл
                ImageIO.write(bufferedImage, "png", file);
                logInfo("Тепловая карта сохранена в: " + file.getAbsolutePath());
            } catch (Exception ex) {
                logInfo("Ошибка при сохранении файла: " + ex.getMessage());
            }
        }
    }


    private void updateHeatMap(double x, double y) {
        Platform.runLater(() -> {
            double invertedY = HEIGHT - y;
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, invertedY);
            dataPoint.setNode(createHeatPoint(Color.RED));
            heatMapSeries.getData().add(dataPoint);

            if (heatMapSeries.getData().size() > 1000) {
                heatMapSeries.getData().remove(0);
            }
        });
    }

    private javafx.scene.shape.Circle createHeatPoint(Color color) {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(3);
        circle.setFill(color);
        return circle;
    }

    private void decayHeatMap() {
        Platform.runLater(() -> {
            for (XYChart.Data<Number, Number> data : heatMapSeries.getData()) {
                javafx.scene.shape.Circle node = (javafx.scene.shape.Circle) data.getNode();
                if (node != null) {
                    Color currentColor = (Color) node.getFill();
                    double newOpacity = Math.max(currentColor.getOpacity() * DECAY_RATE, 0.1);
                    node.setOpacity(newOpacity);
                }
            }
        });
    }


    private void enhanceStationaryAreaIfNeeded(long now) {
        long timeSinceLastMove = now - lastActivityTime;
        if (timeSinceLastMove > STATIONARY_THRESHOLD_MS) {
            updateHeatMap(lastMouseX, lastMouseY);
        }
    }


    private void runRenderingLoop() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(UPDATE_INTERVAL_MS);
                } catch (InterruptedException ignored) {
                }

                long now = System.currentTimeMillis();
                if (recording) {
                    enhanceStationaryAreaIfNeeded(now);
                }

                decayHeatMap(); // Теперь корректно оборачивается в Platform.runLater()
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private synchronized void logInfo(String message) {
        System.out.println(new Date() + " - " + message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
