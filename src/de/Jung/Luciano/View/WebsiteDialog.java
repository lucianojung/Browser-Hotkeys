package de.Jung.Luciano.View;

import com.sun.deploy.util.URLUtil;
import com.sun.jndi.toolkit.url.UrlUtil;
import com.sun.webkit.network.URLs;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.omg.CosNaming.NamingContextExtPackage.URLStringHelper;

import javax.print.DocFlavor;
import java.net.URL;
import java.util.Optional;

public class WebsiteDialog extends Dialog {
    //Layout with Nodes
    GridPane gridPane;
    Label labelName, labelUrl;
    TextField textFieldName, textFieldUrl;

    //+++++++++++++++++++++++++++++++
    //Constructor                   +
    //+++++++++++++++++++++++++++++++

    public WebsiteDialog() {
        super();
        gridPane = new GridPane();
        labelName = new Label("Website Name: ");
        labelUrl = new Label("Website Url: ");
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
        if (textFieldName.getText() == "") return null;
        if (textFieldUrl.getText() == "") return null;
        try {
            new URL(textFieldUrl.getText()).toURI();
        } catch (Exception e) {return null;}

        websiteButton.setText(textFieldName.getText());
        websiteButton.setUrl(textFieldUrl.getText());
        return websiteButton;
    }

    public boolean showRemoveDialog(WebsiteButton websiteButton){
        this.setTitle("Want to Remove " + websiteButton.getText() + "?");
        this.getDialogPane().setContent(null);

        Optional result = this.showAndWait();
        if (!result.isPresent()) return false;
        return result.get().equals(ButtonType.OK);
    }
}
