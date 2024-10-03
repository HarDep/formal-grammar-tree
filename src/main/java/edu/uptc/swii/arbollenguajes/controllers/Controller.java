package edu.uptc.swii.arbollenguajes.controllers;

import edu.uptc.swii.arbollenguajes.domain.Manager;
import edu.uptc.swii.arbollenguajes.models.Production;
import edu.uptc.swii.arbollenguajes.models.Symbol;
import edu.uptc.swii.arbollenguajes.view.MessageDialog;
import edu.uptc.swii.arbollenguajes.view.ElementPanel;
import edu.uptc.swii.arbollenguajes.view.WordInputDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class Controller {

    private final Manager manager;

    @FXML
    private TextField nonTerminalText;

    @FXML
    private TextField terminalText;

    @FXML
    private TextField productionProdText;

    @FXML
    private TextField productionProductText;

    @FXML
    private TextField startSymbolText;

    @FXML
    private VBox nonTerminalSymbolsPanel;

    @FXML
    private VBox terminalSymbolsPanel;

    @FXML
    private VBox productionsPanel;

    @FXML
    private Label tileLabel;

    @FXML
    private TextFlow particularTreeText;

    private String word;

    public Controller(Manager manager) {
        this.manager = manager;
    }

    @FXML
    protected void onButtonEntered(MouseEvent e){
        ((Button) e.getSource()).setStyle("-fx-background-color: #539a89; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    @FXML
    protected void onButtonExited(MouseEvent e){
        ((Button) e.getSource()).setStyle("-fx-background-color: #42d9b3; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    public void showMessage(String message, String title) {
        Platform.runLater(() -> new MessageDialog(title, message,
                (Stage) terminalText.getScene().getWindow()).show());
    }

    @FXML
    public void initialize() {
        this.manager.setController(this);
    }

    @FXML
    protected void testGrammar(){
        //TODO: verificar que el simbolo de comienzo exista en los no terminales
        manager.addStartSymbol(startSymbolText.getText());
        word = new WordInputDialog((Stage) terminalText.getScene().getWindow()).showAndGetWord();
        boolean isValid = manager.checkGrammar();
        if (!isValid) return;
        //TODO: limpiar paneles de arboles
        manager.generateParticularTree(word);
        manager.generateGeneralTree();
    }

    @FXML
    protected void addNonTerminal(){
        boolean isValid = manager.addNonTerminal(nonTerminalText.getText());
        if (isValid) {
            ElementPanel elementPanel = new ElementPanel(nonTerminalText.getText(), "Simbolo");
            elementPanel.setCallback(() -> {
                //TODO: eliminar el simbolo del manager
                nonTerminalSymbolsPanel.getChildren().remove(elementPanel);
            });
            nonTerminalSymbolsPanel.getChildren().add(elementPanel);
        }
    }

    @FXML
    protected void addTerminal(){
        boolean isValid = manager.addTerminal(terminalText.getText());
        if (isValid) {
            ElementPanel elementPanel = new ElementPanel(terminalText.getText(), "Simbolo");
            elementPanel.setCallback(() -> {
                //TODO: eliminar el simbolo del manager
                terminalSymbolsPanel.getChildren().remove(elementPanel);
            });
            terminalSymbolsPanel.getChildren().add(elementPanel);
        }
    }

    @FXML
    protected void addProduction() {
        Production production = new Production(productionProdText.getText(), productionProductText.getText());
        boolean isValid = manager.addProduction(production);
        if (isValid) {
            //TODO: mostrar bien el producto
            String value = "%s ➡ %s".formatted(production.getProduction(), production.getProduct())
                    .replace("/,", "SOME_UNIQUE_CHAR").replace(",", "")
                    .replace("SOME_UNIQUE_CHAR", ",");
            ElementPanel elementPanel = new ElementPanel(value, "Produccion");
            elementPanel.setCallback(() -> {
                //TODO: eliminar la produccion del manager
                productionsPanel.getChildren().remove(elementPanel);
            });
            productionsPanel.getChildren().add(elementPanel);
        }
    }

    public void showParticularTreeNode(boolean isLast, boolean isValid, Symbol... values) {
        for (Symbol value : values) {
            Text text1 = new Text(value.getValue());
            text1.setFill(value.isTerminal()? Color.BLUE : Color.ORANGE);
            text1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            particularTreeText.getChildren().add(text1);
        }
        if (isValid && isLast) {
            Text last = new Text("  ✅");
            last.setFill(Color.GREEN);
            last.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            particularTreeText.getChildren().add(last);
        } else if (!isValid) {
            Text invalid = new Text("  ❌");
            invalid.setFill(Color.RED);
            invalid.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            particularTreeText.getChildren().add(invalid);
        } else {
            Text next = new Text("  ➡  ");
            next.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            next.setFill(Color.GRAY);
            particularTreeText.getChildren().add(next);
        }
    }

    public void invalidWord(){
        tileLabel.setText("La palabra '" + word + "' no pertenece a la gramatica ingresada");
    }

    public void validWord(){
        tileLabel.setText("La palabra '" + word + "' pertenece a la gramatica ingresada");
    }

}