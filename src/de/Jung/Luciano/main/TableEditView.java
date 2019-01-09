package de.Jung.Luciano.main;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class TableEditView {
    private GridPane gridPane;
    private Label labelURLName,
            labelURL,
            labelKeyCode;
    private TextField textFieldURLName = new TextField(),
            textFieldURL = new TextField(),
            textFieldKeyCode = new TextField();
    private KeyCode keyCode;

    public TableEditView() {
        gridPane = new GridPane();
        labelURLName = new Label("URL Name: ");
        labelURL = new Label("URL :");
        labelKeyCode = new Label("KeyCode");

        textFieldURLName.setPromptText("Name der Website");
        textFieldURL.setPromptText("Website URL");
        textFieldKeyCode.setPromptText("Shortcut");

        gridPane.add(labelURLName, 0, 0);
        gridPane.add(labelURL, 0, 1);
        gridPane.add(labelKeyCode, 0, 2);
        gridPane.add(textFieldURLName, 1, 0);
        gridPane.add(textFieldURL, 1, 1);
        gridPane.add(textFieldKeyCode, 1, 2);

        textFieldKeyCode.textProperty().addListener((observable, oldValue, newValue) -> {
            textFieldKeyCode.setText(Character.toString(newValue.charAt(newValue.length()-1)));
        });

        textFieldKeyCode.setOnKeyPressed(event -> {
            keyCode = event.getCode();
        });
    }

    public TextField getTextFieldURLName() {
        return textFieldURLName;
    }

    public TextField getTextFieldURL() {
        return textFieldURL;
    }

    public TextField getTextFieldKeyCode() {
        return textFieldKeyCode;
    }

    public GridPane getRoot() {
        return gridPane;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }
}
