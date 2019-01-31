package de.Jung.Luciano.WebsiteButton;

import de.Jung.Luciano.Controller.ApplicationFacade;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

import java.util.Random;

public class WebsiteButton extends Button {

    private String url;
    private String imageUrl;
    private static final int BUTTONSIZE = 40;

    public WebsiteButton(){
        this(null, null, "");
    }

    public WebsiteButton(String name, String url, String imageUrl) {
        super(name);
        this.url = url;
        if (!imageUrl.equals("null") && !imageUrl.equals(""))
            this.setImageUrl(imageUrl);

        this.setStyle("-fx-background-color: " + this.getRandomColor());
        this.setMinSize(BUTTONSIZE, BUTTONSIZE);

        //Listener
        ApplicationFacade facade = new ApplicationFacade();
        this.setOnAction(event -> facade.showWebsite(url));
        this.addEventHandler(ScrollEvent.SCROLL, event -> this.setStyle("-fx-background-color: " + this.getRandomColor()));

    }

    @Override
    public String toString(){
        return this.getText() + "," + this.getUrl() + "," + this.imageUrl;
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

        return String.format("#%06x", nextInt);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;

        ImageView imageView = new ImageView(new Image(imageUrl));
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        imageView.setPreserveRatio(true);
        this.setGraphic(imageView);
    }
}
