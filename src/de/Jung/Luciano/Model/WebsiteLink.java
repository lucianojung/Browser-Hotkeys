package de.Jung.Luciano.Model;

import javafx.scene.input.KeyCode;

public class WebsiteLink {
    private String urlName;
    private String url;
    private KeyCode keyCode;

    //+++++++++++++++++++++++++++++++++++++++++++
    //constructors
    //+++++++++++++++++++++++++++++++++++++++++++

    public WebsiteLink(String urlName, String url, KeyCode keyCode) {
        this.urlName = urlName;
        this.url = url;
        this.keyCode = keyCode;
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

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setKeyCode(KeyCode keyCode) {
        this.keyCode = keyCode;
    }
}
