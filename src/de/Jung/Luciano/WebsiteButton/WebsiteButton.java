package de.Jung.Luciano.WebsiteButton;

import de.Jung.Luciano.Controller.ApplicationAdapter;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.Random;

public class WebsiteButton extends Button {

    String url;
    String color;
    String identifier;

    public WebsiteButton(String name, String url) {
        super(name);
        this.url = url;
        color = getRandomColor();
        //this.setStyle("-fx-background-color: " + this.color);  //de-Comment to show Colorful Buttons

        this.setOnAction(event -> handleWebsiteButton(event));
    }

    private void handleWebsiteButton(ActionEvent event) {
        System.out.println("Open Browser, Search Website: " + this.url);
        ApplicationAdapter adapter = new ApplicationAdapter();
        adapter.showWebsite(url);
    }

    @Override
    public String toString(){
        return this.getText() + "," + this.getUrl();
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++
    //getter and setter
    //+++++++++++++++++++++++++++++++++++++++++++++++++++

    public String getRandomColor() {
        // create random object - reuse this as often as possible
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(0xffffff + 1);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String color = String.format("#%06x", nextInt);

        // print it
        System.out.println(color);
        return color;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
