package de.Jung.Luciano;

import de.Jung.Luciano.Controller.Controller;
import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model(primaryStage);

        new Controller(model);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
