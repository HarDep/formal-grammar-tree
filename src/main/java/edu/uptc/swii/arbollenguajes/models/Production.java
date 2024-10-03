package edu.uptc.swii.arbollenguajes.models;

import java.util.Objects;

public class Production {

    private String production;
    private String product;

    public Production(String production, String product) {
        this.production = production;
        this.product = product;
    }

    public String getProduction() {
        return production;
    }

    public String getProduct() {
        return product;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Production other) {
            return this.getProduction().equals(other.getProduction()) && this.getProduct().equals(other.getProduct());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(production, product);
    }

}
