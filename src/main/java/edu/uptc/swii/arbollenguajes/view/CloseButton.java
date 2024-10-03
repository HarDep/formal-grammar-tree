package edu.uptc.swii.arbollenguajes.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CloseButton extends Button {

    public CloseButton(EventHandler<ActionEvent> event) {
        setStyle("-fx-background-color: #cf5a51; -fx-border-radius: 10; -fx-background-radius: 10;");
        setOnMouseEntered(e -> setStyle("-fx-background-color: red; -fx-border-radius: 10; -fx-background-radius: 10;"));
        setOnMouseExited(e -> setStyle("-fx-background-color: #cf5a51; -fx-border-radius: 10; -fx-background-radius: 10;"));
        setOnAction(event);
    }
}
