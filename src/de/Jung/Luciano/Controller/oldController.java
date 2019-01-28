package de.Jung.Luciano.Controller;

import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.View.EditWebsite;
import de.Jung.Luciano.View.Overview;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.*;


public class oldController {
    //model
    private Model model;

    //View
    Overview view;

    //++++++++++++++++++++++++++++++++++++
    //constructor
    //+++++++++++++++++++++++++++++++++++

    public oldController(Model model) {
        this.model = model;
        this.view = new Overview();

        generateEventHandler();
        loadData();
        showData();
        show();
    }

    private void loadData() {
        //load Data
        List<WebsiteButton> dataList = model.loadData();
        if (dataList == null) return;
    }

    private void showData() {
        view.getFlowPane().getChildren().clear();
        for (WebsiteButton websiteButton : model.getWebsiteButtons()){
            view.getFlowPane().getChildren().add(websiteButton);
        }
    }

    private void show() {
        Stage primaryStage = model.getStage();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(view.getRoot(), 850, 500));
        primaryStage.show();
    }

    private void generateEventHandler() {
        view.getMenuItemSave().setOnAction(event -> handleMenuItemSave(event));
        view.getMenuItemOpenFile().setOnAction(event -> handleMenuItemLoad(event));
        view.getMenuItemExit().setOnAction(event -> System.exit(0));
        view.getMenuItemAdd().setOnAction(event -> handleMenuItemAdd(event));
        view.getMenuItemEdit().setOnAction(event -> handleMenuItemEdit(event));
        view.getMenuItemRemove().setOnAction(event -> handleMenuItemDelete(event));
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Event Handler
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    private void handleMenuItemSave(ActionEvent event) {
        model.saveData(model.getWebsiteButtons(), true);
    }

    private void handleMenuItemLoad(ActionEvent event) {
        //model.getWebsiteButtons().setAll(model.loadData());
        showData();
    }

    private void handleMenuItemAdd(ActionEvent event) {
        WebsiteButton websiteButton;

        //Add new Website
        websiteButton = handleEditWebsite(null);
        if (websiteButton == null) return;
        //else
        model.getWebsiteButtons().add(websiteButton);
    }

    private void handleMenuItemEdit(ActionEvent event){

        int index = 1;
        if (index == -1) return; //nothing choosen

        WebsiteButton websiteButton = handleEditWebsite(model.getWebsiteButtons().get(index));
        if (websiteButton == null) return;
        //else
        model.getWebsiteButtons().remove(index);
        model.getWebsiteButtons().add(index, websiteButton);
    }

    private void handleMenuItemDelete(ActionEvent event) {
        //remove an URL-Link
        int index = 1;
        if (index == -1) return; //nothing choosen

        Optional result = showAlert("Want to delete Website: " + "PlatzHalter für WebsiteButtonName" + "?");
        if (!result.isPresent() || !result.get().equals(ButtonType.OK)) return;
        //else
        model.getWebsiteButtons().remove(index);
    }


    private void handleMenuItemAbout(ActionEvent event) {
        //about the App
    }

    private void handleLayoutKeyPressed(KeyEvent event) throws InterruptedException {
        if(openPerShortcut(event)) return;  //return if nothing opened
        if (event.isControlDown()) return;  //return if control is pressed
        System.exit(0);              //else Exit System
    }
    //+++++++++++++++++++++++++++++++++++++
    //other methods
    //+++++++++++++++++++++++++++++++++++++

    private boolean openPerShortcut(KeyEvent event) {
        boolean first = true;
        for (int i = 0; i < model.getWebsiteButtons().size(); i++) {

            //else keycode equals pressed key!
            ApplicationFassade adapter = new ApplicationFassade();
            adapter.showWebsite(model.getWebsiteButtons().get(i).getUrl());
            if (!first) continue;

            //else wait till Browser is open -> sleep for 1.5 sek.
            try {
                Thread.sleep(1500);
                first = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return first;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++
    //methods
    //++++++++++++++++++++++++++++++++++++++++++++++

    private WebsiteButton handleEditWebsite(WebsiteButton websiteButton){

        EditWebsite editWebsite = new EditWebsite();
        Dialog dialog = createEditDialog(websiteButton, editWebsite);

        if (websiteButton == null)
            websiteButton = new WebsiteButton("Website", "www.google.de");

        Optional result = dialog.showAndWait();
        websiteButton.setText(editWebsite.getTextFieldURLName().getText());
        websiteButton.setUrl(editWebsite.getTextFieldURL().getText());

        if (!result.isPresent()) return null;
        if (!result.get().equals(ButtonType.OK)) return null;
        if (websiteButton.getUrl() == null) {
            showAlert("Failure! Website URL is null!");
            return null;
        }

        if (websiteButton.getUrl().equals(""))
            websiteButton.setUrl("Website");

        return websiteButton;
    }

    private Dialog createEditDialog(WebsiteButton websiteButton, EditWebsite editWebsite) {
        Dialog dialog = new Dialog();
        dialog.setTitle("Edit Website Settings");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        dialog.getDialogPane().setContent(editWebsite.getRoot());
        if (websiteButton == null) return dialog;
        //else
        editWebsite.getTextFieldURLName().setText(websiteButton.getText());
        editWebsite.getTextFieldURL().setText(websiteButton.getUrl());
        return dialog;
    }

    private Optional showAlert(String FailureMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(FailureMessage);
        return alert.showAndWait();
    }
}
