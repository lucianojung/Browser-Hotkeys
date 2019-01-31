package de.Jung.Luciano.Controller;

import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.View.Overview;
import de.Jung.Luciano.View.WebsiteDialog;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;

import java.io.File;

public class Controller {
    //objects
    private Model model;
    private Overview view;
    private WebsiteDialog dialog;
    private ApplicationFacade facade;
    private FileChooser fileChooser;

    public Controller(Model model) {


        //initialize
        this.model = model;
        this.view = new Overview();
        this.dialog = new WebsiteDialog();
        this.facade = new ApplicationFacade();

        //config FileChooser
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("TextFiles", "*.txt");
        fileChooser.getExtensionFilters().add(fileExtension);
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("save.txt");

        //Listener
        view.getMenuItemOpenFile().setOnAction(this::handleOpenFile);
        view.getMenuItemSave().setOnAction(this::handleSave);
        view.getMenuItemSaveAs().setOnAction(this::handleSaveAs);
        view.getMenuItemExit().setOnAction(event -> model.getStage().fireEvent(new WindowEvent(model.getStage(), WindowEvent.WINDOW_CLOSE_REQUEST)));
        view.getMenuItemAdd().setOnAction(this::handleAddWebsite);
        view.getMenuItemEdit().setOnAction(this::handleEditWebsite);
        view.getMenuItemRemove().setOnAction(this::handleRemoveWebsite);
        view.getFlowPane().setOnContextMenuRequested(event -> view.getContextMenuEdit().show(view.getFlowPane(), event.getScreenX(), event.getScreenY()));
        view.getAddButton().setOnAction(this::handleAddWebsite);
        model.getStage().setOnCloseRequest(event -> handleExit());
        view.getFlowPane().setOnKeyPressed(this::handleKeyPressed);

        //show first View (Overview)
        view.refreshView(model.getShownWebsiteButtons());
        view.show(model.getStage());
    }


    //+++++++++++++++++++++++++++++++
    //Event Handler Methods         +
    //+++++++++++++++++++++++++++++++
    private void handleOpenFile(ActionEvent event) {
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("TextFiles", "*.txt");
        fileChooser.getExtensionFilters().add(fileExtension);
        File file = fileChooser.showOpenDialog(model.getStage());
        //load Data from File, ?save it?
        if (file == null) return;
        model.setFileName(file.toString()); //loads when constructor called
        model.loadData();
        view.refreshView(model.getShownWebsiteButtons());
    }

    private void handleSave(ActionEvent event) {
        if (model.isTempDataChanged() && dialog.showSaveDialog())
            model.saveData(model.getFileName());
    }

    private void handleSaveAs(ActionEvent event) {
        File file = fileChooser.showSaveDialog(model.getStage());
        //save Data in Directory "file"
        if (file == null) return;
        model.setFileName(file.toString());
        model.loadData();
        model.saveData(model.getFileName());
    }

    private void handleExit() {
        handleSave(null);
        String tempFileName = model.getFileName();
        model.setFileName("save.txt");
        model.loadData();
        model.saveData(tempFileName);
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
        addWebsiteButton(websiteButton);
    }

    private void handleEditWebsite(ActionEvent event) {
        WebsiteButton websiteButton = getChosenButton();
        if (websiteButton == null) return;

        WebsiteButton backUpButton = websiteButton;
        websiteButton = dialog.showDialog(websiteButton);
        if (websiteButton == null) return;
        if (!websiteButton.getText().equals(backUpButton.getText()) || !websiteButton.getUrl().equals(backUpButton.getUrl())) return;

        model.removeWebsiteButton(backUpButton);
        addWebsiteButton(websiteButton);
    }

    private void handleRemoveWebsite(ActionEvent event) {
        WebsiteButton websiteButton = getChosenButton();
        if (websiteButton == null) return;
        if (dialog.showRemoveDialog(websiteButton))
            model.removeWebsiteButton(websiteButton);
        view.refreshView(model.getShownWebsiteButtons());
    }

    private void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            for (WebsiteButton websiteButton : model.getShownWebsiteButtons())
                websiteButton.fire();
        }
        else if (keyEvent.getCode() == KeyCode.BACK_SPACE){
            int inputLength = model.getInputText().length();
            if (inputLength <= 0) return;       //otherwise you have a StringIndexOutOfBoundsException
            model.setInputText(model.getInputText().substring(0, inputLength-1));
        }
        else if (keyEvent.getCode() == KeyCode.ESCAPE){
            model.setInputText("");
        }
        else if (keyEvent.getCode() == KeyCode.SHIFT) return;
        else {
            model.appendInputText(keyEvent.getCode().toString());
        }
        System.out.println("Detect Key pressed. InputTest: " + model.getInputText());
        view.refreshView(model.getShownWebsiteButtons());
        if (model.getShownWebsiteButtons().size() == 1)
            model.getShownWebsiteButtons().get(0).fire();
    }

    //+++++++++++++++++++++++++++++++
    //other methods                 +
    //+++++++++++++++++++++++++++++++

    private WebsiteButton getChosenButton() {
        for (Node node : view.getFlowPane().getChildren()) {
            if (!node.isFocused()) continue;
            return node instanceof WebsiteButton ? (WebsiteButton) node : null;
        }
        return null;
    }

    private void addWebsiteButton(WebsiteButton websiteButton){
        model.addWebsiteButton(websiteButton);
        view.refreshView(model.getShownWebsiteButtons());
    }

}
