package de.Jung.Luciano.Controller;

import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.View.EditWebsite;
import de.Jung.Luciano.Model.WebsiteLink;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;


public class Controller {
    //model
    private Model model;

    //from View
    @FXML
    private TableColumn tableColumnURLName, tableColumnURL, tableColumnURLShortcut;

    @FXML
    private TableView tableView;

    //++++++++++++++++++++++++++++++++++++
    //constructor
    //+++++++++++++++++++++++++++++++++++

    public Controller() {
        this.model = new Model();
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Event Handler
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @FXML
    private void handleMenuItemClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleMenuItemAdd(ActionEvent event) {
        //Add new Website
        WebsiteLink websiteLink = handleEditWebsite(null);
        if (websiteLink == null) return;
        //else
        model.getWebsiteLinks().add(websiteLink);
    }

    @FXML
    private void handleMenuItemEdit(ActionEvent event){
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (index == -1) return; //nothing choosen

        WebsiteLink websiteLink = handleEditWebsite(model.getWebsiteLinks().get(index));
        if (websiteLink == null) return;
        //else
        model.getWebsiteLinks().remove(index);
        model.getWebsiteLinks().add(index, websiteLink);
        tableView.refresh();
    }

    @FXML
    private void handleMenuItemRemove(ActionEvent event) {
        //remove an URL-Link
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (index == -1) return; //nothing choosen

        Optional result = showAlert("Want to delete Website: " + tableView.getSelectionModel().getSelectedItems().get(0).toString() + "?");
        if (!result.isPresent() || !result.get().equals(ButtonType.OK)) return;
        //else
        model.getWebsiteLinks().remove(index);
    }


    @FXML
    private void handleMenuItemAbout(ActionEvent event) {
        //about the App
    }

    @FXML
    private void handleLayoutKeyPressed(KeyEvent event) throws InterruptedException {
        if(openPerShortcut(event)) return;  //return if nothing opened
        if (event.isControlDown()) return;  //return if control is pressed
        System.exit(0);              //else Exit System
    }

    //+++++++++++++++++++++++++++++++++++++
    //after FXML initialize Nodes
    //+++++++++++++++++++++++++++++++++++++
    @FXML
    void initialize() {
        tableView.setItems(model.getWebsiteLinks());
        tableColumnURLName.setCellValueFactory(
                new PropertyValueFactory<WebsiteLink, String>("urlName")
        );
        tableColumnURL.setCellValueFactory(
                new PropertyValueFactory<WebsiteLink, String>("url")
        );
        tableColumnURLShortcut.setCellValueFactory(
                new PropertyValueFactory<WebsiteLink, KeyCode>("keyCode")
        );

        //load Data
        List<List<String>> dataList = model.loadData();
        if (dataList == null) return;
        for (List<String> innerDataList : dataList) {
            WebsiteLink websiteLink = new WebsiteLink(innerDataList.get(0), innerDataList.get(1), KeyCode.getKeyCode(innerDataList.get(2)));
            model.getWebsiteLinks().add(websiteLink);
        }
    }

    //+++++++++++++++++++++++++++++++++++++
    //other methods
    //+++++++++++++++++++++++++++++++++++++

    private boolean openPerShortcut(KeyEvent event) {
        boolean first = true;
        for (int i = 0; i < model.getWebsiteLinks().size(); i++) {
            if (event.getCode() != KeyCode.A && event.getCode() != model.getWebsiteLinks().get(i).getKeyCode()) continue;

            //else keycode equals pressed key!
            openWebpage(model.getWebsiteLinks().get(i).getUrl());
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

    public boolean openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
            return true;
        } catch (IOException e) { e.printStackTrace();
        } catch (URISyntaxException e) { e.printStackTrace();
        }
        return false;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++
    //methods
    //++++++++++++++++++++++++++++++++++++++++++++++

    private WebsiteLink handleEditWebsite(WebsiteLink websiteLink){

        EditWebsite editWebsite = new EditWebsite();
        Dialog dialog = createEditDialog(websiteLink, editWebsite);

        if (websiteLink == null)
            websiteLink = new WebsiteLink(null, null, null);

        Optional result = dialog.showAndWait();
        websiteLink.setUrlName(editWebsite.getTextFieldURLName().getText());
        websiteLink.setUrl(editWebsite.getTextFieldURL().getText());
        websiteLink.setKeyCode(KeyCode.getKeyCode(editWebsite.getTextFieldKeyCode().getText()));

        if (!result.isPresent()) return null;
        if (!result.get().equals(ButtonType.OK)) return null;
        if (websiteLink.getUrl() == null) {
            showAlert("Failure! Website URL is null!");
            return null;
        }
        //if (websiteLink.getUrl().equals("")) return showAlert("Failure! Website URL is empty!");             //returns always null
        if (!openWebpage(websiteLink.getUrl())) {
            showAlert("Failure! Website URL is not valid!");
            return null;
        }

        if (websiteLink.getUrlName().equals("")) websiteLink.setUrlName("Website");
        if (websiteLink.getKeyCode() == null) websiteLink.setKeyCode(KeyCode.getKeyCode("A"));
        //else

        return websiteLink;
    }

    private Dialog createEditDialog(WebsiteLink websiteLink, EditWebsite editWebsite) {
        Dialog dialog = new Dialog();
        dialog.setTitle("Edit Website Settings");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        dialog.getDialogPane().setContent(editWebsite.getRoot());
        if (websiteLink == null) return dialog;
        //else
        editWebsite.getTextFieldURLName().setText(websiteLink.getUrlName());
        editWebsite.getTextFieldURL().setText(websiteLink.getUrl());
        editWebsite.getTextFieldKeyCode().setText(websiteLink.getKeyCode().toString());
        return dialog;
    }

    private Optional showAlert(String FailureMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(FailureMessage);
        return alert.showAndWait();
    }
}
