package de.Jung.Luciano.View;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

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
    MenuItem menuItemLoad;
    MenuItem menuItemExit;
    MenuItem menuItemAdd;
    MenuItem menuItemEdit;
    MenuItem menuItemDelete;

    public Overview() {
        //set Layouts
        root = new BorderPane();
        flowPane = new FlowPane();

        //MenuBar
        menuBar = new MenuBar();
        menuAction = new Menu("File");
        menuEdit = new Menu("Edit");
        contextMenuEdit = new ContextMenu();
        menuItemSave = new MenuItem("Save");
        menuItemLoad = new MenuItem("Load");
        menuItemExit = new MenuItem("Exit");
        menuItemAdd = new MenuItem("Add");
        menuItemEdit = new MenuItem("Edit");
        menuItemDelete = new MenuItem("Delete");

        menuAction.getItems().addAll(menuItemSave, menuItemLoad, menuItemExit);
        menuEdit.getItems().addAll(menuItemAdd, menuItemEdit, menuItemDelete);
        contextMenuEdit.getItems().addAll(menuItemAdd, menuItemEdit, menuItemDelete);
        menuBar.getMenus().addAll(menuAction, menuEdit);
        root.setTop(menuBar);

        flowPane.setOnContextMenuRequested(new EventHandler< ContextMenuEvent >(){

            @Override
            public void handle(ContextMenuEvent event) {
                contextMenuEdit.show(flowPane, event.getScreenX(), event.getScreenY());
            }
        });
        root.setCenter(flowPane);
    }

    //++++++++++++++++++++++++++++++++++++++++++++++
    //getter and setter
    //++++++++++++++++++++++++++++++++++++++++++++++


    public BorderPane getRoot() {
        return root;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public MenuItem getMenuItemSave() {
        return menuItemSave;
    }

    public MenuItem getMenuItemLoad() {
        return menuItemLoad;
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

    public MenuItem getMenuItemDelete() {
        return menuItemDelete;
    }
}
