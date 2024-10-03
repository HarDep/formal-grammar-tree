module edu.uptc.swii.arbollenguajes {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.beans;


    //opens edu.uptc.swii.arbollenguajes to javafx.fxml, spring.core;
    opens edu.uptc.swii.arbollenguajes to javafx.fxml;
    opens edu.uptc.swii.arbollenguajes.configs to spring.beans;
    opens edu.uptc.swii.arbollenguajes.controllers to javafx.fxml;
    exports edu.uptc.swii.arbollenguajes;
    exports edu.uptc.swii.arbollenguajes.controllers;
    exports edu.uptc.swii.arbollenguajes.domain;
    exports edu.uptc.swii.arbollenguajes.models;
}