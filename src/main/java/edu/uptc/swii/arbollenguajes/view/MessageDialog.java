package edu.uptc.swii.arbollenguajes.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MessageDialog {

    private final Stage stage;


    public MessageDialog(String title, String message, Stage owner) {
        stage = new Stage();
        stage.setTitle("Message");
        stage.setMinWidth(300);
        stage.setMinHeight(200);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-color: black black black black;");
        vbox.setSpacing(10);
        vbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label(title);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD ,20));
        Label messageLabel = new Label(message);
        messageLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Button closeButton = getButton();
        vbox.getChildren().addAll(titleLabel, messageLabel, closeButton);
        stage.setScene(new Scene(vbox, 300, 200));
    }

    private Button getButton() {
        Button closeButton = new Button("Cerrar");
        closeButton.setStyle("-fx-background-color: #cf5a51; -fx-border-radius: 10; -fx-background-radius: 10;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: red; -fx-border-radius: 10; -fx-background-radius: 10;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: #cf5a51; -fx-border-radius: 10; -fx-background-radius: 10;"));
        closeButton.setOnAction(e -> stage.close());
        return closeButton;
    }

    public void show() {
        stage.showAndWait();
    }

}
