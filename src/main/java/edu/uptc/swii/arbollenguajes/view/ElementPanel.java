package edu.uptc.swii.arbollenguajes.view;

import edu.uptc.swii.arbollenguajes.utils.Callbacks;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @implNote This class represent visually an element of the grammar
 */
public class ElementPanel extends HBox {

    private Callbacks.VoidCallback callback;

    public ElementPanel(String value, String elementName) {
        setSpacing(10);
        Label label = new Label("%s: %s".formatted(elementName, value));
        label.setTextFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        Button deleteButton = getButton();
        getChildren().addAll(label, deleteButton);
        setStyle("-fx-border-color: black black black black; -fx-background-color: #4e6a63;");
        setPadding(new javafx.geometry.Insets(4, 5, 4, 5));
        setMinWidth(Region.USE_PREF_SIZE);
        setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

    /**
     * Create a button to delete the element
     * @return a button with the specified style
     */
    private Button getButton() {
        Button deleteButton = new Button("Eliminar");
        deleteButton.setStyle("-fx-background-color: #cf5a51; -fx-border-radius: 10; -fx-background-radius: 10;");
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: red; -fx-border-radius: 10; -fx-background-radius: 10;"));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #cf5a51; -fx-border-radius: 10; -fx-background-radius: 10;"));
        deleteButton.setOnAction(e -> callback.call());
        return deleteButton;
    }

    /**
     * Sets the callback to be called when the delete button is clicked
     * @param callback the callback to be called
     */
    public void setCallback(Callbacks.VoidCallback callback) {
        this.callback = callback;
    }

}
