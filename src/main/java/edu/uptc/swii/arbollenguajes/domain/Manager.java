package edu.uptc.swii.arbollenguajes.domain;

import edu.uptc.swii.arbollenguajes.controllers.Controller;
import edu.uptc.swii.arbollenguajes.models.Production;

public interface Manager {
    void setController(Controller controller);
    boolean addNonTerminal(String value);
    boolean addTerminal(String value);
    boolean addProduction(Production production);
    void addStartSymbol(String value);
    boolean checkGrammar();
    void generateParticularTree(String word);
    void generateGeneralTree();
}
