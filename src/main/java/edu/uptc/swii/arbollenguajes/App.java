package edu.uptc.swii.arbollenguajes;

import edu.uptc.swii.arbollenguajes.configs.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Queue;

public class App extends Application {

    private ApplicationContext context;

    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void stop() {
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setTitle("Evaluacion gramatica Formal");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}