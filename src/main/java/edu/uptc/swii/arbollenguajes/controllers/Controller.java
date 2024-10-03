package edu.uptc.swii.arbollenguajes.controllers;

import edu.uptc.swii.arbollenguajes.domain.Manager;
import edu.uptc.swii.arbollenguajes.models.Symbol;
import edu.uptc.swii.arbollenguajes.view.MessageDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
        //TODO: verificar que el texto del simbolo de inicio no este vacio
        if (startSymbolText.getText().isEmpty()) showMessage("Ingrese el simbolo de inicio", "Error");
    }

    @FXML
    protected void addNonTerminal(){
    }

    @FXML
    protected void addTerminal(){
        //TODO: verificar que el texto del simbolo no este vacio
    }

    @FXML
    protected void addProduction() {
        //TODO: verificar que el texto del simbolo no este vacio
    }

    public void showParticularTreeNode(boolean isLast, boolean isValid, Symbol... values) {
        //TODO: mostrar node del arbol
    }

    public void invalidWord(){

    }

}