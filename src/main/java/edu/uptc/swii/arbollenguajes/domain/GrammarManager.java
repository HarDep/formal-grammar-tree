package edu.uptc.swii.arbollenguajes.domain;

import edu.uptc.swii.arbollenguajes.controllers.Controller;
import edu.uptc.swii.arbollenguajes.models.Node;
import edu.uptc.swii.arbollenguajes.models.Production;
import edu.uptc.swii.arbollenguajes.models.Symbol;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
            controller.showMessage("El símbolo ya ha sido añadido", "Advertencia");
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
            controller.showMessage("El símbolo ya ha sido añadido", "Advertencia");
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
        for (String separatedProduct : separatedProducts) {
            String symbol = separatedProduct.replace("SOME_UNIQUE_CHAR", ",");
            if (!terminals.contains(symbol) && !nonTerminals.contains(symbol)) {
                controller.showMessage("El símbolo " + symbol + " no es válido", "Error");
                return false;
            }
        }
        boolean isAdded =productions.add(production);
        if (!isAdded) {
            controller.showMessage("La producción ya ha sido anadida", "Advertencia");
            return false;
        }
        return true;
    }

    @Override
    public boolean addStartSymbol(String value) {
        if (nonTerminals.contains(value)) {
            startSymbol = value;
            return true;
        } else {
            controller.showMessage("El símbolo de inicio no es un no terminal válido", "Error");
            return false;
        }
    }

    @Override
    public void removeNonTerminal(String value) {
        for (Production production : productions) {
            if (production.getProduction().equals(value)) {
                controller.showMessage("El no terminal '" + value + "' está siendo utilizado en una producción", "Error");
                return;
            }
            String product = production.getProduct().replace("/,", "SOME_UNIQUE_CHAR");
            String[] symbols = product.split(",");
            for (int i = 0; i < symbols.length; i++) {
                symbols[i] = symbols[i].replace("SOME_UNIQUE_CHAR", ",");
            }

            for (String symbol : symbols) {
                if (symbol.equals(value)) {
                    controller.showMessage("El no terminal '" + value + "' está siendo utilizado en el producto de una producción", "Error");
                    return;
                }
            }
        }
        nonTerminals.remove(value);
        controller.showMessage("El no terminal '" + value + "' ha sido eliminado", "Info");
    }

    @Override
    public void removeTerminal(String value) {
        for (Production production : productions) {
            String product = production.getProduct().replace("/,", "SOME_UNIQUE_CHAR");
            String[] symbols = product.split(",");
            for (int i = 0; i < symbols.length; i++) {
                symbols[i] = symbols[i].replace("SOME_UNIQUE_CHAR", ",");
            }

            for (String symbol : symbols) {
                if (symbol.equals(value)) {
                    controller.showMessage("El terminal '" + value + "' está siendo utilizado en el producto de una producción", "Error");
                    return;
                }
            }
        }
        terminals.remove(value);
        controller.showMessage("El terminal '" + value + "' ha sido eliminado", "Info");
    }

    @Override
    public void removeProduction(Production production) {
        // Verificar si la producción existe en el conjunto de producciones
        if (!productions.contains(production)) {
            controller.showMessage("La producción especificada no existe", "Error");
            return;
        }

        // Si existe, eliminarla
        productions.remove(production);
        controller.showMessage("La producción ha sido eliminada", "Info");
    }


    @Override
    public boolean checkGrammar() {
        //TODO: validar si hay por lo menos -> 3 producciones, 3 no terminales y 2 terminales

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
        List<String> wordSymbols = Arrays.asList(word.split(""));

        Node root = new Node(Collections.singletonList(new Symbol(startSymbol, false)), new ArrayList<>(), null);

        expandNode(root, wordSymbols);
    }

    private boolean expandNode(Node node, List<String> wordSymbols) {
        if (isLeafNode(node)) {
            return nodeMatchesWord(node, wordSymbols);
        }
        for (int i = 0; i < node.getSymbols().size(); i++) {
            Symbol symbol = node.getSymbols().get(i);
            if (!symbol.isTerminal()) {
                for (Production production : productions) {
                    if (production.getProduction().equals(symbol.getValue())) {
                        List<Symbol> productionSymbols = Arrays.stream(production.getProduct().replace("/,", "SOME_UNIQUE_CHAR").split(","))
                                .map(s -> new Symbol(s.replace("SOME_UNIQUE_CHAR", ","), terminals.contains(s)))
                                .collect(Collectors.toList());
                        Node childNode = new Node(productionSymbols, new ArrayList<>(), node);
                        node.getProducts().add(childNode);
                        if (expandNode(childNode, wordSymbols)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isLeafNode(Node node) {
        return node.getSymbols().stream().allMatch(Symbol::isTerminal);
    }

    private boolean nodeMatchesWord(Node node, List<String> wordSymbols) {
        List<String> nodeTerminals = node.getSymbols().stream()
                .filter(Symbol::isTerminal)
                .map(Symbol::getValue)
                .collect(Collectors.toList());
        return nodeTerminals.equals(wordSymbols);
    }

    @Override
    public void generateGeneralTree(){
        //TODO: generar el arbol solo hasta nivel 5
    }

}
