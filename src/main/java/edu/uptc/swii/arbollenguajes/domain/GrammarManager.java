package edu.uptc.swii.arbollenguajes.domain;

import edu.uptc.swii.arbollenguajes.controllers.Controller;
import edu.uptc.swii.arbollenguajes.models.Production;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class GrammarManager implements Manager {

    private Controller controller;

    private final Set<String> terminals = new HashSet<>();

    private final Set<String> nonTerminals = new HashSet<>();

    private final Set<Production> productions = new HashSet<>();

    private String startSymbol;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean addNonTerminal(String value) {
        //TODO: verificar si no existe en los no terminales
        //TODO: añadirlo si es valido y si no enviar mensaje de error al controlador
        return true;
    }

    @Override
    public boolean addTerminal(String value) {
        //TODO: verificar si no existe en los terminales
        //TODO: añadirlo si es valido y si no enviar mensaje de error al controlador
        return true;
    }

    @Override
    public boolean addProduction(Production production) {
        //TODO: verificar si hay "/," en el producto y cambiarlo a un caracter que no exista en la cadena
        //TODO: separar el producto
        //TODO: cambiar el simbolo que se cambio por la ","
        //TODO: verificar el simbolo de la produccion de la produccion existe en los no terminales
        //TODO: verificar si los simbolos del producto existen en los terminales y/o no terminales
        //TODO: añadirlo si es valido y si no enviar mensaje de error al controlador
        return true;
    }

    @Override
    public void addStartSymbol(String value) {
        //TODO: verificar si existe en los no terminales
        //TODO: añadirlo si es valido y si no enviar mensaje de error al controlador
    }

    @Override
    public boolean checkGrammar() {
        //TODO: verificar que las producciones utilicen todos los terminales y todos los no terminales
        //TODO: si no es valido enviar mensaje de error al controlador y no seguir con la operacion
        return true;
    }

    @Override
    public void generateParticularTree(String word) {
        //TODO: generar el arbol
    }

    @Override
    public void generateGeneralTree() {
        //TODO: generar el arbol solo hasta nivel 5
    }

}
