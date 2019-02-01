package de.Jung.Luciano.Controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationFacade extends Application {
    /*
    * simple Class to show a Url in the default Browser
    * exists, so you dont have to extend Application in each Class you want to load a Website URL
    *
    * start-Method does Nothing
    * showWebiste(url)-Method uses HostServices to show the Website
    */

    @Override
    public void start(Stage primaryStage) throws Exception { System.exit(0); }

    public void showWebsite(String url){
        getHostServices().showDocument(url);
    }
}
