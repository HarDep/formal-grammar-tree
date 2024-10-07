package edu.uptc.swii.arbollenguajes.domain;

import edu.uptc.swii.arbollenguajes.controllers.Controller;
import edu.uptc.swii.arbollenguajes.models.Node;
import edu.uptc.swii.arbollenguajes.models.Production;
import edu.uptc.swii.arbollenguajes.models.Symbol;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

/**
 * @implNote this class implements the {@link Manager} interface
 * contains the logic to manage the grammar
 */
@Component
@Scope(SCOPE_SINGLETON)
public class GrammarManager implements Manager {

    private Controller controller;

    private final Set<String> terminals = new HashSet<>();

    private final Set<String> nonTerminals = new HashSet<>();

    private final Set<Production> productions = new HashSet<>();

    private String startSymbol;

    private int level = 1;

    private Node father;

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
        // Verificar que haya al menos 3 producciones
        if (productions.size() < 3) {
            controller.showMessage("Debe haber al menos 3 producciones", "Error");
            return false;
        }

        // Verificar que haya al menos 3 no terminales
        if (nonTerminals.size() < 3) {
            controller.showMessage("Debe haber al menos 3 no terminales", "Error");
            return false;
        }

        // Verificar que haya al menos 2 terminales
        if (terminals.size() < 2) {
            controller.showMessage("Debe haber al menos 2 terminales", "Error");
            return false;
        }

        // Validar que todos los terminales se utilicen en alguna producción
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

        // Validar que todos los no terminales se utilicen en alguna producción
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
        Node nodeParticular = getParticularTreeNode(word);
        if (nodeParticular == null) {
            controller.showParticularTreeNode(true, false, new ArrayList<>());
            return;
        }
        List<Node> path = new ArrayList<>();
        path.add(nodeParticular);
        Node particularFather = nodeParticular.getFather();
        path.add(particularFather);
        while (particularFather.getFather() != null) {
            path.add(particularFather.getFather());
            particularFather = particularFather.getFather();
        }
        path.sort(Comparator.comparing(Node::getLevel));
        for (Node node : path) {
            controller.showParticularTreeNode(node == nodeParticular, true, node.getSymbols());
        }
    }

    private Node getParticularTreeNode(String word) {
        List<Node> possibles = new ArrayList<>(father.getProducts());
        List<Node> productsLevel3 = new ArrayList<>();
        father.getProducts().forEach(node -> {
            if (node.getProducts() != null) productsLevel3.addAll(node.getProducts());
        });
        possibles.addAll(productsLevel3);
        List<Node> productsLevel4 = new ArrayList<>();
        productsLevel3.forEach(node -> {
            if (node.getProducts() != null) productsLevel4.addAll(node.getProducts());
        });
        possibles.addAll(productsLevel4);
        List<Node> productsLevel5 = new ArrayList<>();
        productsLevel4.forEach(node -> {
            if (node.getProducts() != null) productsLevel5.addAll(node.getProducts());
        });
        possibles.addAll(productsLevel5);
        possibles.add(father);

        List<Node> onlyLeaf = possibles.stream().filter(n -> n.getSymbols().stream()
                .allMatch(Symbol::isTerminal)).toList();

        for (Node node : onlyLeaf) {
            String value = String.join("", node.getSymbols().stream().map(Symbol::getValue).toList());
            if (value.equals(word)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void generateGeneralTree(){
        level = 1;
        father = new Node(Collections.singletonList(new Symbol(startSymbol, false)), null, null);
        generateNode(father);
        int maxTotalNodesPerLevelInAll = getCantNodesInTheMaxLevel(father);
        controller.setCanvasLevelsWidth(maxTotalNodesPerLevelInAll);
        controller.showGeneralTreeLevel(1, Collections.singletonList(father));
    }

    private void generateNode(Node node) {
        if (node.getProducts() != null && level == 1) return;
        if (node.isMapped()) {
            generateNode(nextNode(node));
            return;
        }
        node.setMapped(true);
        if (level == 5) {
            level = node.getFather().getLevel();
            generateNode(node.getFather());
            return;
        }
        List<Node> children = new ArrayList<>();
        int count = 0;
        for (Symbol symbol : node.getSymbols()) {
            if (!symbol.isTerminal()) {
                List<Production> productions = getProductions(symbol);
                for (Production production : productions) {
                    Node child = new Node(getSymbols(production, node.getSymbols(), count), null, node);
                    children.add(child);
                }
            }
            count++;
        }
        node.setProducts(children.isEmpty() ? null : children);
        generateNode(nextNode(node));
    }

    private List<Symbol> getSymbols(Production production, List<Symbol> symbols, int count) {
        List<Symbol> symbols1 = new ArrayList<>();
        int counter = 0;
        for (Symbol symbol : symbols) {
            if (counter == count && !symbol.isTerminal()) {
                String product = production.getProduct().replace("/,", "SOME_UNIQUE_CHAR");
                String[] separatedProducts = product.split(",");
                for (int i = 0; i < separatedProducts.length; i++) {
                    separatedProducts[i] = separatedProducts[i].replace("SOME_UNIQUE_CHAR", ",");
                }
                symbols1.addAll(Arrays.stream(separatedProducts)
                        .map(v -> new Symbol(v, this.terminals.contains(v))).toList());
            } else symbols1.add(symbol);
            counter++;
        }
        return symbols1;
    }

    private List<Production> getProductions(Symbol symbol) {
        List<Production> productions = new ArrayList<>();
        for (Production production : this.productions) {
            if (production.getProduction().equals(symbol.getValue())) {
                productions.add(production);
            }
        }
        return productions;
    }

    private Node nextNode(Node node) {
        if (node.getProducts() != null) {
            for (Node product : node.getProducts()) {
                if (!product.isMapped()) {
                    level = product.getLevel();
                    return product;
                }
            }
        }
        level = node.getFather() != null ? node.getFather().getLevel() : 1;
        return node.getFather();
    }

    private int getCantNodesInTheMaxLevel(Node father) {
        int cantLevel1 = 1;
        int cantLevel2 = father.getProducts().size();
        List<Node> productsLevel3 = new ArrayList<>();
        father.getProducts().forEach(node -> {
            if (node.getProducts() != null) productsLevel3.addAll(node.getProducts());
        });
        int cantLevel3 = productsLevel3.size();
        List<Node> productsLevel4 = new ArrayList<>();
        productsLevel3.forEach(node -> {
            if (node.getProducts() != null) productsLevel4.addAll(node.getProducts());
        });
        int cantLevel4 = productsLevel4.size();
        List<Node> productsLevel5 = new ArrayList<>();
        productsLevel4.forEach(node -> {
            if (node.getProducts() != null) productsLevel5.addAll(node.getProducts());
        });
        int cantLevel5 = productsLevel5.size();
        return Stream.of(cantLevel1, cantLevel2, cantLevel3, cantLevel4, cantLevel5).max(Integer::compareTo).get();
    }

}
