package edu.uptc.swii.arbollenguajes.models;

import java.util.List;

public class Node {

    private List<Symbol> symbols;
    private List<Node> products;
    private Node father;
    private boolean isPainted;

    public Node(List<Symbol> symbols, List<Node> products, Node father) {
        this.symbols = symbols;
        this.products = products;
        this.father = father;
        this.isPainted = false;
    }

    public boolean isPainted() {
        return isPainted;
    }

    public void setPainted(boolean isPainted) {
        this.isPainted = isPainted;
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
