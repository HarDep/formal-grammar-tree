package edu.uptc.swii.arbollenguajes.domain;

import edu.uptc.swii.arbollenguajes.controllers.Controller;
import edu.uptc.swii.arbollenguajes.models.Production;

public interface Manager {
    void setController(Controller controller);
    void addNonTerminal(String value);
    void addTerminal(String value);
    void addProduction(Production production);
    void addStartSymbol(String value);
    void checkGrammar();
    void generateParticularTree(String word);
    void generateGeneralTree();
}
