package edu.uptc.swii.arbollenguajes.models;

import java.util.List;

public class Node {

    private String product;
    private List<Node> products;
    private Node father;

    public Node(String product, List<Node> products, Node father) {
        this.product = product;
        this.products = products;
        this.father = father;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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
