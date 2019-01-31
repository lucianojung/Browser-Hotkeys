package de.Jung.Luciano.View;

import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;

public class WebsiteDialog extends Dialog {
    //Layout with Nodes
    private GridPane gridPane;
    private TextField textFieldName, textFieldUrl;

    //+++++++++++++++++++++++++++++++
    //Constructor                   +
    //+++++++++++++++++++++++++++++++

    public WebsiteDialog() {
        super();
        gridPane = new GridPane();
        Label labelName = new Label("Website Name: ");
        Label labelUrl = new Label("Website Url: ");
        textFieldName = new TextField();
        textFieldName.setPromptText("Website XY");
        textFieldUrl = new TextField();
        textFieldUrl.setPromptText("www.thePage.de");

        gridPane.add(labelName, 0, 0);
        gridPane.add(labelUrl, 0, 1);
        gridPane.add(textFieldName, 1, 0);
        gridPane.add(textFieldUrl, 1, 1);

        this.getDialogPane().getButtonTypes().add(0, ButtonType.OK);
        this.getDialogPane().getButtonTypes().add(1, ButtonType.CANCEL);
    }

    //+++++++++++++++++++++++++++++++
    //show-Methods                  +
    //+++++++++++++++++++++++++++++++

    public WebsiteButton showDialog(WebsiteButton websiteButton){
        this.setTitle(websiteButton.getUrl() == null ? "Add new Website" : "Edit Website");
        this.textFieldName.setText(websiteButton.getText());
        this.textFieldUrl.setText(websiteButton.getUrl());
        this.getDialogPane().setContent(gridPane);


        Optional result = this.showAndWait();

        if (!result.isPresent()) return null;
        if (!result.get().equals(ButtonType.OK)) return null;
        if (textFieldName.getText().equals("")) return null;
        if (textFieldUrl.getText().equals("")) return null;
        try {
            new URL(textFieldUrl.getText()).toURI();
        } catch (Exception e) {return null;}

        websiteButton.setText(textFieldName.getText());
        websiteButton.setUrl(textFieldUrl.getText());
        return websiteButton;
    }

    public boolean showRemoveDialog(WebsiteButton websiteButton){
        this.setTitle(websiteButton != null ? "Want to Remove " + websiteButton.getText() + "?" : "Save Changes?");
        this.getDialogPane().setContent(new Label(this.getTitle()));

        Optional result = this.showAndWait();
        if (!result.isPresent()) return false;
        return result.get().equals(ButtonType.OK);
    }

    public boolean showSaveDialog() {
        return showRemoveDialog(null);
    }
}
