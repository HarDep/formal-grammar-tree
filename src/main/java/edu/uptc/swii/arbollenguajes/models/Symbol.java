package edu.uptc.swii.arbollenguajes.models;

public class Symbol {

    private String value;
    private boolean isTerminal;

    public Symbol(String value, boolean isTerminal) {
        this.value = value;
        this.isTerminal = isTerminal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public void setTerminal(boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

}
