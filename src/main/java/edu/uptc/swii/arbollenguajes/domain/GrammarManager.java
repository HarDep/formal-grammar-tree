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
        if (terminals.contains(value)) {
            controller.showMessage("El símbolo ya está en los terminales", "Error");
            return false;
        }
        if (nonTerminals.add(value)) {
            return true;
        } else {
            controller.showMessage("El símbolo no pudo ser añadido ya está en los no terminales", "Error");
            return false;
        }
    }


    @Override
    public boolean addTerminal(String value) {
        if (nonTerminals.contains(value)) {
            controller.showMessage("El símbolo ya está en los no terminales", "Error");
            return false;
        }
        if (terminals.add(value)) {
            return true;
        } else {
            controller.showMessage("El símbolo no pudo ser añadido ya está en los terminales", "Error");
            return false;
        }
    }


    @Override
    public boolean addProduction(Production production) {
        String product = production.getProduct().replace("/,", "SOME_UNIQUE_CHAR");
        String[] separatedProducts = product.split(",");

        if (!nonTerminals.contains(production.getProduction())) {
            controller.showMessage("El símbolo de producción no es un no terminal válido", "Error");
            return false;
        }
        for (int i = 0; i < separatedProducts.length; i++) {
            String symbol = separatedProducts[i].replace("SOME_UNIQUE_CHAR", ",");
            if (!terminals.contains(symbol) && !nonTerminals.contains(symbol)) {
                controller.showMessage("El símbolo " + symbol + " no es válido", "Error");
                return false;
            }
        }
        productions.add(production);
        return true;
    }




    @Override
    public void addStartSymbol(String value) {
        if (nonTerminals.contains(value)) {
            startSymbol = value;
        } else {
            controller.showMessage("El símbolo de inicio no es un no terminal válido", "Error");
        }
    }
    @Override
    public boolean checkGrammar() {
        for (String terminal : terminals) {
            boolean terminalUsed = false;
            for (Production production : productions) {
                String product = production.getProduct().replace("/,", "SOME_UNIQUE_CHAR");
                String[] symbols = product.split(",");
                for (String symbol : symbols) {
                    symbol = symbol.replace("SOME_UNIQUE_CHAR", ",");
                    if (symbol.equals(terminal)) {
                        terminalUsed = true;
                        break;
                    }
                }
                if (terminalUsed) break;
            }
            if (!terminalUsed) {
                controller.showMessage("El terminal '" + terminal + "' no ha sido utilizado", "Error");
                return false;
            }
        }

        for (String nonTerminal : nonTerminals) {
            boolean nonTerminalUsed = false;
            for (Production production : productions) {
                if (production.getProduction().equals(nonTerminal)) {
                    nonTerminalUsed = true;
                    break;
                }

                String product = production.getProduct().replace("/,", "SOME_UNIQUE_CHAR");
                String[] symbols = product.split(",");
                for (String symbol : symbols) {
                    symbol = symbol.replace("SOME_UNIQUE_CHAR", ",");
                    if (symbol.equals(nonTerminal)) {
                        nonTerminalUsed = true;
                        break;
                    }
                }
                if (nonTerminalUsed) break;
            }
            if (!nonTerminalUsed) {
                controller.showMessage("El no terminal '" + nonTerminal + "' no ha sido utilizado", "Error");
                return false;
            }
        }

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
