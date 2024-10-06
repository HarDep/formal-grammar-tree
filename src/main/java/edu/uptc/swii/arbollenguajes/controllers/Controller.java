package edu.uptc.swii.arbollenguajes.controllers;

import edu.uptc.swii.arbollenguajes.domain.Manager;
import edu.uptc.swii.arbollenguajes.models.Node;
import edu.uptc.swii.arbollenguajes.models.Production;
import edu.uptc.swii.arbollenguajes.models.Symbol;
import edu.uptc.swii.arbollenguajes.view.MessageDialog;
import edu.uptc.swii.arbollenguajes.view.ElementPanel;
import edu.uptc.swii.arbollenguajes.view.WordInputDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

/**
 * @implNote This class is used by the {@link edu.uptc.swii.arbollenguajes.App},
 * to link the view (the view.fxml file inside the src/main/resources folder),
 * and the domain manager {@link Manager} for the interaction events
 */
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

    @FXML
    private HBox symbolsLevel1;

    @FXML
    private VBox generalTreePanel;

    @FXML
    private Canvas canvasFromLevel1;

    @FXML
    private HBox symbolsLevel2;

    @FXML
    private Canvas canvasFromLevel2;

    @FXML
    private HBox symbolsLevel3;

    @FXML
    private Canvas canvasFromLevel3;

    @FXML
    private HBox symbolsLevel4;

    @FXML
    private Canvas canvasFromLevel4;

    @FXML
    private HBox symbolsLevel5;

    private final Map<Integer, HBox> levels = new HashMap<>();

    private final Map<Integer, Canvas> canvas = new HashMap<>();

    private final int NODE_WIDTH = 150;

    private String word;

    public Controller(Manager manager) {
        this.manager = manager;
    }

    /**
     * Called when the mouse enters the button.
     * Changes the color of the button to a color to indicate that it's being hovered.
     */
    @FXML
    protected void onButtonEntered(MouseEvent e){
        ((Button) e.getSource()).setStyle("-fx-background-color: #539a89; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    /**
     * Called when the mouse exits the button.
     * Changes the color of the button back to its original color.
     */
    @FXML
    protected void onButtonExited(MouseEvent e){
        ((Button) e.getSource()).setStyle("-fx-background-color: #42d9b3; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    /**
     * Shows a message dialog with the given message and title.
     * The dialog is shown in the same thread as the UI.
     * @param message the message to show in the dialog
     * @param title the title of the dialog
     */
    public void showMessage(String message, String title) {
        Platform.runLater(() -> new MessageDialog(title, message,
                (Stage) terminalText.getScene().getWindow()).show());
    }

    /**
     * Initializes the controller.
     * This method is called after the FXML file has been loaded.
     * It makes some visual configurations.
     */
    @FXML
    public void initialize() {
        this.manager.setController(this);
        levels.put(1, symbolsLevel1);
        levels.put(2, symbolsLevel2);
        levels.put(3, symbolsLevel3);
        levels.put(4, symbolsLevel4);
        levels.put(5, symbolsLevel5);
        canvas.put(1, canvasFromLevel1);
        canvas.put(2, canvasFromLevel2);
        canvas.put(3, canvasFromLevel3);
        canvas.put(4, canvasFromLevel4);
        generalTreePanel.getChildren().clear();
    }

    /**
     * This method validates the grammar and the start symbol, and the call the logic to generate the trees.
     * It first checks if the start symbol is valid, and if it is, it checks if the grammar is valid.
     * If the grammar is valid, it clears all the visible components and generates the trees.
     */
    @FXML
    protected void testGrammar(){
        boolean validStartSymbol = manager.addStartSymbol(startSymbolText.getText());
        if (!validStartSymbol) return;
        boolean isValidGrammar = manager.checkGrammar();
        if (!isValidGrammar) return;
        Platform.runLater(() -> {
            word = new WordInputDialog((Stage) terminalText.getScene().getWindow()).showAndGetWord();
            levels.forEach((e, v) -> v.getChildren().clear());
            canvas.forEach((e, v) -> {
                v.getGraphicsContext2D().setFill(Color.WHITE);
                v.getGraphicsContext2D().fillRect(0, 0, v.getWidth(), v.getHeight());
            });
            particularTreeText.getChildren().clear();
            //manager.generateParticularTree(word);
            manager.generateGeneralTree();
        });
    }

    /**
     * This method add a new non-terminal symbol to the grammar.
     * It gets the text from the non-terminal text field, and call the checks
     */
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

    /**
     * This method adds a new terminal symbol to the grammar.
     * It gets the text from the terminal text field, and call the checks.
     */
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

    /**
     * This method adds a new production to the grammar.
     * It gets the text from the production and product text fields, and call the checks.
     * If the production is valid, it creates a new {@link ElementPanel} with the production.
     */
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

    /**
     * Shows the next node in the particular tree.
     * @param isLast     whether this is the last node of the tree
     * @param isValid    whether the node is valid
     * @param values     the symbols of the node
     */
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
            validWord();
        } else if (!isValid) {
            Text invalid = new Text("  ❌");
            invalid.setFill(Color.RED);
            invalid.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            particularTreeText.getChildren().add(invalid);
            invalidWord();
        } else {
            Text next = new Text("  ➡  ");
            next.setFont(Font.font("Arial", FontWeight.BOLD, 25));
            next.setFill(Color.GRAY);
            particularTreeText.getChildren().add(next);
        }
    }

    /**
     * Sets the width of each canvas in the canvas map to the maximum width needed
     * @param maxTotalNodesPerLevelInAll the maximum number of nodes per level in
     *                                    all the tree
     */
    public void setCanvasLevelsWidth(int maxTotalNodesPerLevelInAll) {
        canvas.forEach((e, v) -> v.setWidth(maxTotalNodesPerLevelInAll * NODE_WIDTH));
    }

    /**
     * This method shows the level of the general tree given by the parameter level.
     * And, it calls itself with the next level.
     * @param level the level of the tree
     * @param values the nodes of the level
     */
    public void showGeneralTreeLevel(int level, List<Node> values) {
        if (values.isEmpty()) {
            Stage stage = (Stage) generalTreePanel.getScene().getWindow();
            stage.setFullScreen(true);
            return;
        }
        HBox levelPanel = levels.get(level);
        int totalAllChildren = values.stream().mapToInt(e -> {
            if (e.getProducts() != null) return e.getProducts().size();
            return 0;
        }).sum();
        Canvas canvasLevel = null;
        double posFather = 0;
        double posChild = 0;
        if (level < 5) {
            canvasLevel = canvas.get(level);
            posChild = (canvasLevel.getWidth()/2) - ((((double) totalAllChildren * NODE_WIDTH) /2) - ((double) NODE_WIDTH /2));
            posFather = (canvasLevel.getWidth()/2) - ((((double) values.size() * NODE_WIDTH) /2) - ((double) NODE_WIDTH /2));
            canvasLevel.getGraphicsContext2D().setStroke(Color.BLACK);
            canvasLevel.getGraphicsContext2D().setLineWidth(2);
        }
        List<Node> children = new ArrayList<>();
        for (Node value : values) {
            TextFlow textFlow = new TextFlow();
            textFlow.setTextAlignment(TextAlignment.CENTER);
            textFlow.setMinWidth(Region.USE_PREF_SIZE);
            textFlow.setPrefWidth(NODE_WIDTH);
            for (Symbol symbol : value.getSymbols()) {
                Text text1 = new Text(symbol.getValue());
                text1.setFill(symbol.isTerminal()? Color.BLUE : Color.ORANGE);
                text1.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                textFlow.getChildren().add(text1);
            }
            levelPanel.getChildren().add(textFlow);
            if (value.getProducts() != null && level < 5) {
                for (Node product : value.getProducts()) {
                    canvasLevel.getGraphicsContext2D().strokeLine(posFather, 0, posChild, canvasLevel.getHeight());
                    posChild += NODE_WIDTH;
                    children.add(product);
                }
            }
            posFather += NODE_WIDTH;
        }
        if (level == 5) generalTreePanel.getChildren().add(levelPanel);
        else generalTreePanel.getChildren().addAll(levelPanel, canvasLevel);
        showGeneralTreeLevel(level + 1, children);
    }

    /**
     * Shows that the given word does not belong to the grammar.
     */
    private void invalidWord(){
        tileLabel.setText("La palabra '" + word + "' no pertenece a la gramatica ingresada");
    }

    /**
     * Shows that the given word belongs to the grammar.
     */
    private void validWord(){
        tileLabel.setText("La palabra '" + word + "' pertenece a la gramatica ingresada");
    }

}