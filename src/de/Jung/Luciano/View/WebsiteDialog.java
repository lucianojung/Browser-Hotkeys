package de.Jung.Luciano.View;

import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public class WebsiteDialog extends Dialog {
    //Layout with Nodes
    private GridPane gridPane;
    private TextField textFieldName, textFieldUrl;
    private Label labelImageUrl;
    private Button buttonAddImage;
    private String imageUrl;

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
        labelImageUrl = new Label("Website Image");
        buttonAddImage = new Button("Add Image");

        gridPane.add(labelName, 0, 0);
        gridPane.add(labelUrl, 0, 1);
        gridPane.add(labelImageUrl, 0, 2);
        gridPane.add(textFieldName, 1, 0);
        gridPane.add(textFieldUrl, 1, 1);
        gridPane.add(buttonAddImage, 1, 2);

        this.getDialogPane().getButtonTypes().add(0, ButtonType.OK);
        this.getDialogPane().getButtonTypes().add(1, ButtonType.CANCEL);

        //Listener
        buttonAddImage.setOnAction(this::handleAddImage);
    }

    //+++++++++++++++++++++++++++++++
    //Event Handler                 +
    //+++++++++++++++++++++++++++++++

    private void handleAddImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("ImageFile", "*.png", "*.gif", "*.jpg", "*.jpeg", "*.tiff", "*.tif", "*.bmp", "*.dib");
        fileChooser.getExtensionFilters().add(fileExtension);
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file == null) return;
        if (!file.isFile()) return;

        imageUrl = file.toURI().toString();
        buttonAddImage.setText(file.getName());
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
        if (!buttonAddImage.getText().equals("Add Image"))
            websiteButton.setImageUrl(imageUrl);
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
