package sample;

import javafx.scene.input.KeyCode;

public class WebsiteLink {
    private String urlName;
    private String url;
    private KeyCode keyCode;

    //+++++++++++++++++++++++++++++++++++++++++++
    //constructors
    //+++++++++++++++++++++++++++++++++++++++++++

    public WebsiteLink(String urlName, String url, KeyCode keyCode) {
        System.out.println("create a new URL");
        this.urlName = urlName;
        this.url = url;
        this.keyCode = keyCode;
    }

    public WebsiteLink(String urlName, String url) {
        this(urlName, url, null);
    }

    public WebsiteLink(String url){
        this(url, url, null);
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
