package de.Jung.Luciano.Controller;

import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.View.Overview;
import de.Jung.Luciano.View.WebsiteDialog;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    //objects
    Model model;
    Overview view;
    WebsiteDialog dialog;
    ApplicationFassade fassade;

    public Controller(Model model) {


        //initialize
        this.model = model;
        this.view = new Overview();
        this.dialog = new WebsiteDialog();
        this.fassade = new ApplicationFassade();

        //Listener
        view.getMenuItemOpenFile().setOnAction(event -> handleOpenFile(event));
        view.getMenuItemSave().setOnAction(event -> handleSave(event));
        view.getMenuItemSaveAs().setOnAction(event -> handleSaveAs(event));
        view.getMenuItemExit().setOnAction(event -> System.exit(0));
        view.getMenuItemAdd().setOnAction(event -> handleAddWebsite(event));
        view.getMenuItemEdit().setOnAction(event -> handleEditWebsite(event));
        view.getMenuItemRemove().setOnAction(event -> handleRemoveWebsite(event));

        //show first View (Overview)
        view.show(model);
    }

    //+++++++++++++++++++++++++++++++
    //Event Handler Methods         +
    //+++++++++++++++++++++++++++++++

    private void handleOpenFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("TextFiles", "*.txt");
        fileChooser.getExtensionFilters().add(fileExtension);
        File file = fileChooser.showOpenDialog(model.getStage());
        //load Data from File, ?save it?
        if (file == null) return;
        model.setFileName(file.toString()); //loads when constructor called
        view.show(model);
    }

    private void handleSave(ActionEvent event) {
        model.saveData(true);
    }

    private void handleSaveAs(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(model.getStage());
        //save Data in Directory "file"
        if (file == null) return;
        model.setFileName(file.toString() + "/save.txt");
        model.saveData(dialog.showRemoveDialog(null));
    }

    private void handleAddWebsite(ActionEvent event) {
        /*
         * opens a Dialog with Param of an empty WebsiteButton
         * returns an WebsiteButton with the Text from the Dialog
         * returns null if ButtonType.Cancel
         * else: add the new WebsiteButton to the List
         */
        WebsiteButton websiteButton = dialog.showDialog(new WebsiteButton());
        if (websiteButton == null) return;
        model.addWebsiteButton(websiteButton);
        view.show(model);
    }

    private void handleEditWebsite(ActionEvent event) {
        /*
         * opens a Dialog with the WebsiteButton got from the event*/

        WebsiteButton websiteButton = getChoosenButton();
        if (websiteButton == null) return;

        model.removeWebsiteButton(websiteButton);
        WebsiteButton backUpButton = websiteButton;
        websiteButton = dialog.showDialog(websiteButton);
        model.addWebsiteButton(websiteButton != null ? websiteButton : backUpButton);
        view.show(model);
    }

    private void handleRemoveWebsite(ActionEvent event) {
        WebsiteButton websiteButton = getChoosenButton();
        if (websiteButton == null) return;
        if (websiteButton != null ? dialog.showRemoveDialog(websiteButton) : false)
            model.removeWebsiteButton(websiteButton);
        view.show(model);
    }

    private WebsiteButton getChoosenButton() {
        for (Node node : view.getFlowPane().getChildren()) {
            if (!node.isFocused()) continue;
            return node instanceof WebsiteButton ? (WebsiteButton) node : null;
        }
        return null;
    }

}
