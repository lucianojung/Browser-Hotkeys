package de.Jung.Luciano.Controller;

import javafx.application.Application;
import javafx.stage.Stage;

import java.net.URL;

public class ApplicationAdapter extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {}

    public void showWebsite(String url){
        getHostServices().showDocument(url);
    }
}
