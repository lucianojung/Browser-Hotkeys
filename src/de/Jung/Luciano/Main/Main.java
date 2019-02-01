package de.Jung.Luciano.Main;

import de.Jung.Luciano.Controller.Controller;
import de.Jung.Luciano.Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @param primaryStage
     * @throws Exception
     */
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        * creates the Model
        * creates Controller with model
        */
        Model model = new Model(primaryStage);

        new Controller(model);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
