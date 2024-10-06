package edu.uptc.swii.arbollenguajes.models;

import java.util.List;

/**
 * @implNote This class represents a node of the tree for the grammar
 */
public class Node {

    private List<Symbol> symbols;
    private List<Node> products;
    private Node father;
    private boolean isMapped;
    private final int level;

    public Node(List<Symbol> symbols, List<Node> products, Node father) {
        this.symbols = symbols;
        this.products = products;
        this.father = father;
        this.isMapped = false;
        this.level = father == null ? 1 : father.getLevel() + 1;
    }

    public int getLevel() {
        return level;
    }

    public boolean isMapped() {
        return isMapped;
    }

    public void setMapped(boolean isPainted) {
        this.isMapped = isPainted;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<Node> getProducts() {
        return products;
    }

    public void setProducts(List<Node> products) {
        this.products = products;
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

}
