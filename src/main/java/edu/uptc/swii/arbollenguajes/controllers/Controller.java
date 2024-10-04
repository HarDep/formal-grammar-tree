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

import java.util.List;

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
        boolean validStartSymbol = manager.addStartSymbol(startSymbolText.getText());
        if (!validStartSymbol) return;
        boolean isValidGrammar = manager.checkGrammar();
        if (!isValidGrammar) return;
        word = new WordInputDialog((Stage) terminalText.getScene().getWindow()).showAndGetWord();
        //TODO: limpiar paneles de arboles
        particularTreeText.getChildren().clear();
        manager.generateParticularTree(word);
        manager.generateGeneralTree();
    }

    @FXML
    protected void addNonTerminal(){
        String value = nonTerminalText.getText();
        boolean isValid = manager.addNonTerminal(value);
        if (isValid) {
            ElementPanel elementPanel = new ElementPanel(nonTerminalText.getText(), "Simbolo");
            elementPanel.setCallback(() -> {
                manager.removeNonTerminal(value);
                nonTerminalSymbolsPanel.getChildren().remove(elementPanel);
            });
            nonTerminalSymbolsPanel.getChildren().add(elementPanel);
        }
    }

    @FXML
    protected void addTerminal(){
        String value = terminalText.getText();
        boolean isValid = manager.addTerminal(value);
        if (isValid) {
            ElementPanel elementPanel = new ElementPanel(terminalText.getText(), "Simbolo");
            elementPanel.setCallback(() -> {
                manager.removeTerminal(value);
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
            String value = "%s ➡ %s".formatted(production.getProduction(), production.getProduct()
                    .replace("/,", "SOME_UNIQUE_CHAR").replace(",", "")
                    .replace("SOME_UNIQUE_CHAR", ","));
            ElementPanel elementPanel = new ElementPanel(value, "Produccion");
            elementPanel.setCallback(() -> {
                manager.removeProduction(production);
                productionsPanel.getChildren().remove(elementPanel);
            });
            productionsPanel.getChildren().add(elementPanel);
        }
    }

    public void showParticularTreeNode(boolean isLast, boolean isValid, List<Symbol> values) {
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

    public void showGeneralTreeNode(int level, int angle, List<Symbol> values) {
        //TODO: mostrar nodo del arbol
    }

    public void invalidWord(){
        tileLabel.setText("La palabra '" + word + "' no pertenece a la gramatica ingresada");
    }

    public void validWord(){
        tileLabel.setText("La palabra '" + word + "' pertenece a la gramatica ingresada");
    }

}