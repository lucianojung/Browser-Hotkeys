package de.Jung.Luciano.View;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Overview {

    /*
    * Main View of the Application
    * Has a BorderPane as root
    *   -> Top: MenuBar
    *   -> Center: FlowPane
    *       -> AllButtons and addButton
    *
    * created by Controller
    * uses StyleSheet: style.css
    *
    * has getter for Nodes
    * has a show()-Method to show Stage with root
    * has a refresh-Method to refresh the WebsiteButtons in the flowPane
    *
    * EventListener:
    *   - flowPane.setOnContextMenuRequested() -> for RightClick Menu
    *   - addButton.setOnAction() -> fires menuItemAdd
    */

    //Layout
    private BorderPane root;
    private FlowPane flowPane;
    //Menus
    private ContextMenu contextMenuEdit;
    private MenuItem menuItemSave;
    private MenuItem menuItemSaveAs;
    private MenuItem menuItemOpenFile;
    private MenuItem menuItemExit;
    private MenuItem menuItemAdd;
    private MenuItem menuItemEdit;
    private MenuItem menuItemRemove;
    //others
    private Button addButton;

    public Overview() {
        //set Layouts
        root = new BorderPane();
        flowPane = new FlowPane();

        //MenuBar
        //Menus
        MenuBar menuBar = new MenuBar();
        Menu menuAction = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        contextMenuEdit = new ContextMenu();
        menuItemOpenFile = new MenuItem("Open File");
        menuItemSave = new MenuItem("Save");
        menuItemSaveAs = new MenuItem("Save As");
        menuItemExit = new MenuItem("Exit");
        menuItemAdd = new MenuItem("Add");
        menuItemEdit = new MenuItem("Edit");
        menuItemRemove = new MenuItem("Remove");

        menuAction.getItems().addAll(menuItemOpenFile, menuItemSave, menuItemSaveAs, menuItemExit);
        menuEdit.getItems().addAll(menuItemAdd);
        contextMenuEdit.getItems().addAll(menuItemEdit, menuItemRemove);
        menuBar.getMenus().addAll(menuAction, menuEdit);
        root.setTop(menuBar);

        addButton = new Button("+");
        addButton.getStyleClass().add("add-button");
        flowPane.getChildren().add(addButton);

        //flowPane
        flowPane.setPadding(new Insets(5));
        flowPane.setHgap(5);
        flowPane.setVgap(5);
        root.setCenter(flowPane);
        root.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());

    }

    //+++++++++++++++++++++++++++++++
    //other methods                 +
    //+++++++++++++++++++++++++++++++

    public void show(Stage stage) {
        stage.setTitle("Browser Hotkeys");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void refreshView(ObservableList<Node> websiteButtons){
        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(websiteButtons);
        flowPane.getChildren().add(addButton);
    }

    //+++++++++++++++++++++++++++++++
    //getter and setter             +
    //+++++++++++++++++++++++++++++++

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

    public ContextMenu getContextMenuEdit() {
        return contextMenuEdit;
    }

    public Button getAddButton() {
        return addButton;
    }
}
