package de.Jung.Luciano.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;


public class Controller {
    //model
    private Model model;

    //from View
    @FXML
    TableColumn tableColumnURLName, tableColumnURL, tableColumnURLShortcut;

    @FXML
    TableView tableView;

    @FXML
    private void handleMenuItemClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleMenuItemAdd(ActionEvent event) {
        //add URL
        Dialog dialog = new Dialog();
        dialog.setTitle("Add a new Link");
        dialog.setHeaderText("");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        TableEditView editView = new TableEditView();

        Optional result;
        do{
            dialog.getDialogPane().setContent(editView.getRoot());
            result = dialog.showAndWait();
        } while (!checkNewWebsite(editView) && result.isPresent() && result.get().equals(ButtonType.OK));

        //Add new Website
        WebsiteLink websiteLink = new WebsiteLink(editView.getTextFieldURLName().getText(), editView.getTextFieldURL().getText(), editView.getKeyCode());
        model.getWebsiteLinks().add(websiteLink);
        model.addData(websiteLink);
    }

    @FXML
    private void handleMenuItemDelete(ActionEvent event) {
        //remove an URL-Link
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (index == -1) return; //nothing choosen

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Want to delete Website: " + tableView.getSelectionModel().getSelectedItems().get(0).toString() + "?");

        Optional result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK))
            model.getWebsiteLinks().remove(index);
    }

    @FXML
    private void handleMenuItemAbout(ActionEvent event) {
        //about the App
    }

    @FXML
    private void handleLayoutKeyPressed(KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.A) {
            for (WebsiteLink websiteLink : model.getWebsiteLinks()) {
                openWebpage(websiteLink.getUrl());
            }
        }
        /*
        * load websites
        * if there are no known website for the Shortcut return
        * if there is a shortcut look if ControlDown == true
        * else:
        * Exit System
        * All well done
        */
        if(getKeyShortcuts(event)) return;
        if (event.isControlDown()) return;

        //else
        System.exit(0);
    }
    //++++++++++++++++++++++++++++++++++++
    //constructor
    //+++++++++++++++++++++++++++++++++++

    public Controller() {
        this.model = new Model();
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
    }

    //+++++++++++++++++++++++++++++++++++++
    //other methods
    //+++++++++++++++++++++++++++++++++++++

    private boolean getKeyShortcuts(KeyEvent event) {
        boolean first = true;
        for (int i = 0; i < model.getWebsiteLinks().size(); i++) {
            if (event.getCode() != model.getWebsiteLinks().get(i).getKeyCode()) continue;

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

    private boolean checkNewWebsite(TableEditView editView) {String urlName = editView.getTextFieldURLName().getText();
        //check for URL
        String url = editView.getTextFieldURL().getText();
        if (url.equals("")) return false;
        if (!openWebpage(url)) return false;
        return true;
    }
}
