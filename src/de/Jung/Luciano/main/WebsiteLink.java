package de.Jung.Luciano.main;

import javafx.scene.input.KeyCode;

public class WebsiteLink {
    private String urlName;
    private String url;
    private KeyCode keyCode;

    //+++++++++++++++++++++++++++++++++++++++++++
    //constructors
    //+++++++++++++++++++++++++++++++++++++++++++

    public WebsiteLink(String urlName, String url, KeyCode keyCode) {
        this.urlName = (urlName == null) ? "Website" : urlName;
        this.url = (url == null) ? "https://www.google.de" : url;
        this.keyCode = (keyCode == null) ? KeyCode.A : keyCode;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++
    //other methods
    //+++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public String toString() {
        return this.urlName;
    }

    //+++++++++++++++++++++++++++++++++++++++++
    //getter and setter
    //++++++++++++++++++++++++++++++++++++++++


    public String getUrlName() {
        return this.urlName;
    }

    public String getUrl() {
        return this.url;
    }

    public KeyCode getKeyCode() {
        return this.keyCode;
    }
}
