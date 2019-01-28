package de.Jung.Luciano.Controller;

import de.Jung.Luciano.Model.Model;
import de.Jung.Luciano.View.Overview;
import de.Jung.Luciano.View.WebsiteDialog;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.event.ActionEvent;

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
    }

    //+++++++++++++++++++++++++++++++
    //Event Handler Methods         +
    //+++++++++++++++++++++++++++++++

    private void handleOpenFile(ActionEvent event){}

    private void handleSave(ActionEvent event){
        model.saveData(model.getWebsiteButtons(), false);
    }

    private void handleSaveAs(ActionEvent event){}

    private void handleAddWebsite(ActionEvent event){
        WebsiteButton newWebsiteButton = dialog.showDialog(new WebsiteButton());
        if (newWebsiteButton == null) return;
        model.addWebsiteButton(newWebsiteButton);
    }

    private void handleEditWebsite(ActionEvent event){
        WebsiteButton websiteButton = event.getSource() instanceof WebsiteButton ? dialog.showDialog((WebsiteButton) event.getSource()) : null;
        if (websiteButton == null) return;

        model.removeWebsiteButton((WebsiteButton) event.getSource());
        model.addWebsiteButton(websiteButton);
    }

    private void handleRemoveWebsite(ActionEvent event){
        if (!(event.getSource() instanceof WebsiteButton)) return;
        if (!dialog.showRemoveDialog((WebsiteButton) event.getSource())) return;

        model.removeWebsiteButton((WebsiteButton) event.getSource());
    }
}
