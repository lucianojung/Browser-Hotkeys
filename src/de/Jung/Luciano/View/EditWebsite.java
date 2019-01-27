package de.Jung.Luciano.View;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class EditWebsite {
    private GridPane gridPane;
    private Label labelURLName, labelURL;
    private TextField textFieldURLName = new TextField(), textFieldURL = new TextField();

    public EditWebsite() {
        gridPane = new GridPane();
        labelURLName = new Label("URL Name: ");
        labelURL = new Label("URL :");

        textFieldURLName.setPromptText("Name der Website");
        textFieldURL.setPromptText("Website URL");

        gridPane.add(labelURLName, 0, 0);
        gridPane.add(labelURL, 0, 1);
        gridPane.add(textFieldURLName, 1, 0);
        gridPane.add(textFieldURL, 1, 1);
    }

    public TextField getTextFieldURLName() {
        return textFieldURLName;
    }

    public TextField getTextFieldURL() {
        return textFieldURL;
    }

    public GridPane getRoot() {
        return gridPane;
    }
}
