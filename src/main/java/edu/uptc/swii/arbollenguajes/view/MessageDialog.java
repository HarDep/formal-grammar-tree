package edu.uptc.swii.arbollenguajes.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
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
        messageLabel.setWrapText(true);
        messageLabel.setMinHeight(Region.USE_COMPUTED_SIZE);
        messageLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Button closeButton = new CloseButton(e -> stage.close());
        vbox.getChildren().addAll(titleLabel, messageLabel, closeButton);
        stage.setScene(new Scene(vbox, 300, 200));
    }

    public void show() {
        stage.showAndWait();
    }

}
