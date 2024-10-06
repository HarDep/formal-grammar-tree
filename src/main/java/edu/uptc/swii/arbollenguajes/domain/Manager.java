package edu.uptc.swii.arbollenguajes.domain;

import edu.uptc.swii.arbollenguajes.controllers.Controller;
import edu.uptc.swii.arbollenguajes.models.Production;

/**
 * @implNote This interface is used by the {@link Controller}
 */
public interface Manager {
    void setController(Controller controller);
    boolean addNonTerminal(String value);
    boolean addTerminal(String value);
    boolean addProduction(Production production);
    boolean addStartSymbol(String value);
    void removeNonTerminal(String value);
    void removeTerminal(String value);
    void removeProduction(Production production);
    boolean checkGrammar();
    void generateParticularTree(String word);
    void generateGeneralTree();
}
