package de.Jung.Luciano.Controller;

import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.View.TableEditView;
import de.Jung.Luciano.Model.WebsiteLink;
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
import java.util.*;


public class Controller {
    //model
    private Model model;

    //from View
    @FXML
    TableColumn tableColumnURLName, tableColumnURL, tableColumnURLShortcut;

    @FXML
    TableView tableView;

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

            if (result.get().equals(ButtonType.CANCEL))
                return;
        } while (!checkNewWebsite(editView) || !result.isPresent() || !result.get().equals(ButtonType.OK));

        //Add new Website
        WebsiteLink websiteLink = new WebsiteLink(editView.getTextFieldURLName().getText(), editView.getTextFieldURL().getText(), editView.getKeyCode());
        model.getWebsiteLinks().add(websiteLink);
        model.saveData(new ArrayList<WebsiteLink>(Arrays.asList(websiteLink)), false);
    }

    @FXML
    private void handleMenuItemEdit(ActionEvent event){
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (index == -1) return; //nothing choosen

        //add URL
        Dialog dialog = new Dialog();
        dialog.setTitle("Add a new Link");
        dialog.setHeaderText("");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        TableEditView editView = new TableEditView();

        WebsiteLink websiteLink = model.getWebsiteLinks().get(tableView.getSelectionModel().getSelectedIndex());
        editView.getTextFieldURLName().setText(websiteLink.getUrlName());
        editView.getTextFieldURL().setText(websiteLink.getUrl());
        editView.getTextFieldKeyCode().setText(websiteLink.getKeyCode().toString());
        editView.setKeyCode(websiteLink.getKeyCode());

        Optional result;
        do{
            dialog.getDialogPane().setContent(editView.getRoot());
            result = dialog.showAndWait();

            if (result.get().equals(ButtonType.CANCEL))
                return;
        } while (!checkNewWebsite(editView) || !result.isPresent() || !result.get().equals(ButtonType.OK));

        //Add new Website
        websiteLink = new WebsiteLink(editView.getTextFieldURLName().getText(), editView.getTextFieldURL().getText(), editView.getKeyCode());
        model.getWebsiteLinks().set(index, websiteLink);
        model.saveData(model.getWebsiteLinks(), true);
    }

    @FXML
    private void handleMenuItemRemove(ActionEvent event) {
        //remove an URL-Link
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (index == -1) return; //nothing choosen

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Want to delete Website: " + tableView.getSelectionModel().getSelectedItems().get(0).toString() + "?");

        Optional result = alert.showAndWait();
        if (!result.isPresent() || !result.get().equals(ButtonType.OK)) return;
        //else
        removeWebsite(index);
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
        for (List<String> innerDataList : dataList) {
            WebsiteLink websiteLink = new WebsiteLink(innerDataList.get(0), innerDataList.get(1), KeyCode.getKeyCode(innerDataList.get(2)));
            model.getWebsiteLinks().add(websiteLink);
        }
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
        return (openWebpage(url));
    }

    private void removeWebsite(int index){
        WebsiteLink websiteLink = model.getWebsiteLinks().get(index);
        String deleteString = websiteLink.getUrlName() + "," + websiteLink.getUrl() + "," + websiteLink.getKeyCode();
        List<List<String>> dataList = model.loadData();

        Iterator<List<String>> iter = dataList.iterator();

        while (iter.hasNext()) {
            List<String> innerDataList = iter.next();

            if (!innerDataList.get(0).equals(websiteLink.getUrlName())) continue;
            if (!innerDataList.get(1).equals(websiteLink.getUrl())) continue;
            if (!innerDataList.get(2).equals(websiteLink.getKeyCode().toString())) continue;
            //else
            System.out.println("Remove Website");
            iter.remove();
        }
        model.saveData(dataList);
        model.getWebsiteLinks().remove(index);
    }
}
