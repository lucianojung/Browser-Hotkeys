package de.Jung.Luciano.View;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Overview {

    //Layout
    BorderPane root;
    FlowPane flowPane;
    //MenuBar
    MenuBar menuBar;
    Menu menuAction;
    Menu menuEdit;
    ContextMenu contextMenuEdit;
    MenuItem menuItemSave;
    MenuItem menuItemSaveAs;
    MenuItem menuItemOpenFile;
    MenuItem menuItemExit;
    MenuItem menuItemAdd;
    MenuItem menuItemEdit;
    MenuItem menuItemRemove;

    public Overview() {
        //set Layouts
        root = new BorderPane();
        flowPane = new FlowPane();

        //MenuBar
        menuBar = new MenuBar();
        menuAction = new Menu("File");
        menuEdit = new Menu("Edit");
        contextMenuEdit = new ContextMenu();
        menuItemOpenFile = new MenuItem("Open File");
        menuItemSave = new MenuItem("Save");
        menuItemSaveAs = new MenuItem("Save As");
        menuItemExit = new MenuItem("Exit");
        menuItemAdd = new MenuItem("Add");
        menuItemEdit = new MenuItem("Edit");
        menuItemRemove = new MenuItem("Remove");

        menuAction.getItems().addAll(menuItemOpenFile, menuItemSave, menuItemSaveAs, menuItemExit);
        menuEdit.getItems().addAll(menuItemAdd, menuItemEdit, menuItemRemove);
        contextMenuEdit.getItems().addAll(menuItemAdd, menuItemEdit, menuItemRemove);
        menuBar.getMenus().addAll(menuAction, menuEdit);
        root.setTop(menuBar);

        //flowPane
        flowPane.setOnContextMenuRequested(event -> contextMenuEdit.show(flowPane, event.getScreenX(), event.getScreenY()));
        root.setCenter(flowPane);
    }

    //+++++++++++++++++++++++++++++++
    //other methods                 +
    //+++++++++++++++++++++++++++++++

    public void show(Stage stage) {
        stage.setTitle("Website Shortcuts");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    //+++++++++++++++++++++++++++++++
    //getter and setter             +
    //+++++++++++++++++++++++++++++++


    public BorderPane getRoot() {
        return root;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public MenuItem getMenuItemSave() {
        return menuItemSave;
    }

    public MenuItem getMenuItemSaveAs() {
        return menuItemSaveAs;
    }

    public MenuItem getMenuItemOpenFile() {
        return menuItemOpenFile;
    }

    public MenuItem getMenuItemExit() {
        return menuItemExit;
    }

    public MenuItem getMenuItemAdd() {
        return menuItemAdd;
    }

    public MenuItem getMenuItemEdit() {
        return menuItemEdit;
    }

    public MenuItem getMenuItemRemove() {
        return menuItemRemove;
    }

}
