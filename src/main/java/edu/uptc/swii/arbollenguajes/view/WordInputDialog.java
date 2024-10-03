package edu.uptc.swii.arbollenguajes.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WordInputDialog {

    private final Stage stage;

    private final TextField textField;

    public WordInputDialog(Stage owner) {
        stage = new Stage();
        stage.setTitle("Palabra a evaluar");
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
        Label titleLabel = new Label("Palabra a evaluar");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD ,20));
        Label messageLabel = new Label("Ingrese en el siguiente cuadro de texto la palabra para la cual se evaluara segun la gramatica ingresada");
        messageLabel.setTextAlignment(TextAlignment.JUSTIFY);
        textField = new TextField();
        textField.setPromptText("Ingrese la palabra");
        textField.setAlignment(Pos.CENTER);
        Button closeButton = new CloseButton(e -> stage.close());
        vbox.getChildren().addAll(titleLabel, messageLabel, textField,closeButton);
        stage.setScene(new Scene(vbox, 300, 200));
    }

    public String showAndGetWord() {
        stage.showAndWait();
        return textField.getText();
    }

}
