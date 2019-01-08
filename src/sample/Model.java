package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

public class Model {

    ObservableList<WebsiteLink> WebsiteLinks = FXCollections.observableArrayList();

    public Model() {
        for (int i = 0; i < 2; i++) {
            WebsiteLinks.add(new WebsiteLink("TestURL", "https://www.google.com", KeyCode.S));
        }
    }

    //+++++++++++++++++++++++++
    //getter and setter
    //++++++++++++++++++++++++++++++

    public ObservableList<WebsiteLink> getWebsiteLinks() {
        return WebsiteLinks;
    }
}
