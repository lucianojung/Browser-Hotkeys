package de.Jung.Luciano.WebsiteButton;

import de.Jung.Luciano.Controller.ApplicationFassade;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

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

        this.setOnAction(event -> handleWebsiteButton(event));
    }

    private void handleWebsiteButton(ActionEvent event) {
        System.out.println("Open Browser, Search Website: " + this.url);
        ApplicationFassade adapter = new ApplicationFassade();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
