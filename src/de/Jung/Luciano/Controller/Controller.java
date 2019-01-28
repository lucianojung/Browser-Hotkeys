package de.Jung.Luciano.Controller;

import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.View.Overview;
import de.Jung.Luciano.View.WebsiteDialog;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.event.ActionEvent;
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
        view.show(model.getStage());
        view.getFlowPane().getChildren().addAll(model.getWebsiteButtons());
    }

    //+++++++++++++++++++++++++++++++
    //Event Handler Methods         +
    //+++++++++++++++++++++++++++++++

    private void handleOpenFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("TextFiles","*.txt");
        fileChooser.getExtensionFilters().add(fileExtension);
        File file = fileChooser.showOpenDialog(model.getStage());
        //load Data from File, ?save it?
    }

    private void handleSave(ActionEvent event){
        model.saveData(model.getWebsiteButtons(), false);
    }

    private void handleSaveAs(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(model.getStage());
        //save Data in Directory "file"
    }

    private void handleAddWebsite(ActionEvent event){
        /*
        * opens a Dialog with Param of an empty WebsiteButton
        * returns an WebsiteButton with the Text from the Dialog
        * returns null if ButtonType.Cancel
        * else: add the new WebsiteButton to the List
        */
        WebsiteButton websiteButton = dialog.showDialog(new WebsiteButton());
        if (websiteButton == null) return;
        model.addWebsiteButton(websiteButton);
        view.getFlowPane().getChildren().clear();
        view.getFlowPane().getChildren().addAll(model.getWebsiteButtons());
    }

    private void handleEditWebsite(ActionEvent event){
        /*
        * opens a Dialog with the WebsiteButton got from the event*/

        WebsiteButton websiteButton = event.getSource() instanceof WebsiteButton ? dialog.showDialog((WebsiteButton) event.getSource()) : null;
        if (websiteButton == null) return;

        model.removeWebsiteButton((WebsiteButton) event.getSource());
        model.addWebsiteButton(websiteButton);
        view.getFlowPane().getChildren().clear();
        view.getFlowPane().getChildren().addAll(model.getWebsiteButtons());
    }

    private void handleRemoveWebsite(ActionEvent event){
        if (!(event.getSource() instanceof WebsiteButton)) return;
        if (!dialog.showRemoveDialog((WebsiteButton) event.getSource())) return;

        model.removeWebsiteButton((WebsiteButton) event.getSource());
    }
}
