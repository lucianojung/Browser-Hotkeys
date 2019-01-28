package de.Jung.Luciano.WebsiteButton;

import de.Jung.Luciano.Controller.ApplicationFassade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.Random;

public class WebsiteButton extends Button {

    String url;

    public WebsiteButton(){
        this(null, null);
    }

    public WebsiteButton(String name, String url) {
        super(name);
        this.url = url;
        this.setStyle("-fx-background-color: " + this.getRandomColor());

        //Listener
        this.setOnAction(event -> handleWebsiteButton(event));
        this.addEventHandler(ScrollEvent.SCROLL, event -> this.setStyle("-fx-background-color: " + this.getRandomColor()));
    }

    private void handleWebsiteButton(ActionEvent event) {
        System.out.println("Open Browser, Search Website: " + this.url);
        ApplicationFassade fassade = new ApplicationFassade();
        fassade.showWebsite(url);
    }

    @Override
    public String toString(){
        return this.getText() + "," + this.getUrl();
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++
    //getter and setter
    //+++++++++++++++++++++++++++++++++++++++++++++++++++

    public String getRandomColor() {
        /*
        * create Object of Class Random
        * create randomNumber with max ffffff (hex) = 16777215
        * format it as Hexadezimal String
        * return color
        */
        Random random = new Random();
        int nextInt;
        do {
            nextInt = random.nextInt(0xffffff + 1);
        }
        while (nextInt < 10000000);

        String color = String.format("#%06x", nextInt);
        return color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
