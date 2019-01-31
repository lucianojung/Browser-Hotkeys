package de.Jung.Luciano.Controller;

import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.View.Overview;
import de.Jung.Luciano.View.WebsiteDialog;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class Controller {
    //objects
    private Model model;
    private Overview view;
    private WebsiteDialog dialog;
    private ApplicationFacade facade;
    private FileChooser fileChooser;

    public Controller(Model model) {
        /*
         * 1. initialize object properties
         * 2. add Listener
         * 3. config FileChooser
         * 4. bind (if it works how i want it)
         * 5. refresh and show View
         */
        //initialize
        this.model = model;
        this.view = new Overview();
        this.dialog = new WebsiteDialog();
        this.facade = new ApplicationFacade();

        //Listener
        view.getMenuItemOpenFile().setOnAction(this::handleOpenFile);
        view.getMenuItemSave().setOnAction(this::handleSave);
        view.getMenuItemSaveAs().setOnAction(this::handleSaveAs);
        view.getMenuItemExit().setOnAction(event -> model.getStage().fireEvent(new WindowEvent(model.getStage(), WindowEvent.WINDOW_CLOSE_REQUEST)));   //fires WindowCloseRequest-Event of Stage
        view.getMenuItemAdd().setOnAction(this::handleAddWebsite);
        view.getMenuItemEdit().setOnAction(this::handleEditWebsite);
        view.getMenuItemRemove().setOnAction(this::handleRemoveWebsite);
        view.getFlowPane().setOnContextMenuRequested(event -> view.getContextMenuEdit().show(view.getFlowPane(), event.getScreenX(), event.getScreenY()));
        view.getAddButton().setOnAction(this::handleAddWebsite);
        model.getStage().setOnCloseRequest(event -> handleExit());
        view.getFlowPane().setOnKeyPressed(this::handleKeyPressed);


        //config FileChooser
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("TextFiles", "*.txt");
        fileChooser.getExtensionFilters().add(fileExtension);
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("save.txt");

        //bind
        //Bindings.bindContent(view.getFlowPane().getChildren(), model.getShownWebsiteButtons());  //-> dont know how it works with addButton

        //show View
        view.refreshView(model.getShownWebsiteButtons());
        view.show(model.getStage());
    }


    //+++++++++++++++++++++++++++++++
    //Event Handler Methods         +
    //+++++++++++++++++++++++++++++++
    private void handleOpenFile(ActionEvent event) {
        /*
         * show FileChooser to open a txt-File
         * -> returned file is new FileName
         * refreshView because binding missed
         */
        File file = fileChooser.showOpenDialog(model.getStage());

        if (file == null) return;
        model.setFileName(file.toString()); //loads when constructor called
        model.loadData();
        view.refreshView(model.getShownWebsiteButtons());
    }

    private void handleSave(ActionEvent event) {
        /*
         * opens a saveDialog if Data is changed since last Save
         * save if result is true
         */
        if (model.isTempDataChanged() && dialog.showSaveDialog())
            model.saveData(model.getFileName());
    }

    private void handleSaveAs(ActionEvent event) {
        /*
         * shows SaveAs-Dialog
         * -> returned File is new FileName
         * -> Load Data from new Path
         */
        File file = fileChooser.showSaveDialog(model.getStage());
        //save Data in Directory "file"
        if (file == null) return;
        model.setFileName(file.toString());
        model.loadData();
    }

    private void handleExit() {
        /*
         * handles Save Data
         * then saves the Path of the actual File in the default Path "save.txt"
         * -> saves actual file temporary (tempFileName)
         * -> load Data from default
         * -> saves data in default with temp fileName
         */
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
        /*
         * get the Button clicked on
         * shows edit Dialog
         * -> saves returned WebsiteButton
         * -> look if edited Button is different
         * remove old Button
         * add edited Button
         */
        WebsiteButton websiteButton = getChosenButton();
        if (websiteButton == null) return;

        WebsiteButton editedWebsiteButton = dialog.showDialog(websiteButton);
        if (editedWebsiteButton == null) return;
        if (!editedWebsiteButton.getText().equals(websiteButton.getText()) || !editedWebsiteButton.getUrl().equals(websiteButton.getUrl()))
            return;

        model.removeWebsiteButton(websiteButton);
        addWebsiteButton(editedWebsiteButton);
    }

    private void handleRemoveWebsite(ActionEvent event) {
        /*
         * get the Button clicked on
         * show Remove Dialog
         * if answer is true
         * -> remove WebisteButton and refresh View
         *
         */
        WebsiteButton websiteButton = getChosenButton();
        if (websiteButton == null) return;
        if (!dialog.showRemoveDialog(websiteButton)) return;

        model.removeWebsiteButton(websiteButton);
        view.refreshView(model.getShownWebsiteButtons());
    }

    private void handleKeyPressed(KeyEvent keyEvent) {
        /*
         * handles all the Keyboard Input
         * - ENTER
         *   -> opens all shown Websites
         *   -> Exit
         * - BACK_SPACE
         *   -> removes the last Char of the Input
         * - ESCAPE
         *   -> reset the Input
         * - SHIFT
         *   -> did nothing
         * - else
         *   -> append to Input
         *
         * each time the Input changed
         * -> the Shown WebsiteButtons will be filtered
         * -> looks if there is only one shown
         *   -> if true fires Button (setOnAction)
         *   -> Exit
         * */
        if (keyEvent.getCode() == KeyCode.ENTER) {
            for (Node websiteButton : model.getShownWebsiteButtons()) {
                if (!(websiteButton instanceof Button)) continue;
                ((Button) websiteButton).fire();
            }
            System.exit(0);
        } else if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            int inputLength = model.getInputText().length();
            if (inputLength <= 0)
                return;                                                                               //to pretend StringIndexOutOfBoundsException
            model.setInputText(model.getInputText().substring(0, inputLength - 1));
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            model.setInputText("");
        } else if (keyEvent.getCode() == KeyCode.SHIFT) return;
        else {
            model.appendInputText(keyEvent.getCode().toString());
        }

        System.out.println("Detect Key pressed. InputTest: " + model.getInputText());
        view.refreshView(model.getShownWebsiteButtons());
        if (model.getShownWebsiteButtons().size() != 1) return;
        if (!(model.getShownWebsiteButtons().get(0) instanceof Button)) return;
        ((Button) model.getShownWebsiteButtons().get(0)).fire();
        System.exit(0);
    }

    //+++++++++++++++++++++++++++++++
    //other methods                 +
    //+++++++++++++++++++++++++++++++

    private WebsiteButton getChosenButton() {
        /*
         * returns focused Button
         * if nothing focused returns first Button
         */
        for (Node node : view.getFlowPane().getChildren()) {
            if (!node.isFocused()) continue;
            return node instanceof WebsiteButton ? (WebsiteButton) node : null;
        }
        return null;
    }

    private void addWebsiteButton(WebsiteButton websiteButton) {
        /*
         * add the given WebsiteButton to the ButtonList in Model
         * refresh's View
         */
        model.addWebsiteButton(websiteButton);
        view.refreshView(model.getShownWebsiteButtons());
    }

}
