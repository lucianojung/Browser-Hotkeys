package de.Jung.Luciano.Model;

import de.Jung.Luciano.Data_Handler.SimpleDataHandler;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class Model {

    /*
    * Objects of:
    * - Stage
    * - Lists of WebsiteButtons (Saved Ones and shown)
    * - DataInterface for save(List)- and load()-Method
    * - Strings:
    *   -> Name of actual File
    *   -> Name of File to open when Application start instead of default Location
    *   -> InputText of Keyboard Input
    * - boolean if data changed since last Save
    */
    private Stage stage;
    private List<WebsiteButton> websiteButtons;
    private ObservableList<Node> shownWebsiteButtons;
    private SimpleDataInterface dataHandler;
    private String fileName = "save.txt";
    private String alternativeFile = fileName;
    private String inputText = "";
    private boolean tempDataChanged;

    //+++++++++++++++++++++++++++++++
    //constructor                   +
    //+++++++++++++++++++++++++++++++

    public Model(Stage stage) {
        /*
        * saves stage
        * initialize Lists and dataHandler
        * loads Data
        * look if first Line is another File, if true
        * -> is it a valid File?
        * -> is it not the same File?
        *   -> if both true set new FileName and load again
        */
        this.stage = stage;
        this.websiteButtons = new ArrayList<>();
        this.shownWebsiteButtons = FXCollections.observableArrayList();
        this.dataHandler = new SimpleDataHandler();                                                                     //add fileName in Constructor to save it somewhere else

        this.loadData();

        if (!new File(this.alternativeFile).isFile()) return;
        if (this.fileName.equals(alternativeFile)) return;

        setFileName(alternativeFile);
        this.loadData();
    }

    //+++++++++++++++++++++++++++++++
    //save and load Data            +
    //+++++++++++++++++++++++++++++++


    public void saveData(String file) {
        /*
        * for each WebsiteButton from dataList
        *   create one Object of WebsiteButton
        * save Data via SimpleDataInterface (uses toString()-Method, to save Object Data)
        */
        List<Object> objects = new ArrayList<>(Collections.singletonList(file));
        objects.addAll(websiteButtons);
        System.out.println("Saving Data: " + objects.size() + " Objects");
        websiteButtons.sort(Comparator.comparing(Labeled::getText));
        dataHandler.save(objects, true);
        tempDataChanged = false;
    }

    public void loadData() {
        /*
        * if (fileName is no File) return
        * else:
        *   create List<WebsiteButton>
        *   load Data via SimpleDataInterface in another List<Object>
        *   look if Object is a String with two Strings seperated with a ","
        *       if yes: add a new WebsiteButton to first List (String[0] = Name, String[1] = Url)
        *   return first List
        */
        websiteButtons.clear();
        if(!new File(fileName).isFile()) return;

        List<String> rawData = dataHandler.load();

        for (Object websiteButton : rawData){
            String[] strings = websiteButton.toString().split(",");
            if (strings.length == 3) {
                addWebsiteButton(new WebsiteButton(strings[0], strings[1], strings[2]));
            } else if (strings.length == 2)
                addWebsiteButton(new WebsiteButton(strings[0], strings[1], ""));
            else if (strings.length == 1)
                alternativeFile = strings[0];

        }
        System.out.println("Load Data, Found " + websiteButtons.size() + " WebsiteButtons!");
        websiteButtonsChanged();
        tempDataChanged = false;
    }

    //+++++++++++++++++++++++++++++++
    //add and remove                +
    //+++++++++++++++++++++++++++++++

    public void addWebsiteButton(WebsiteButton websiteButton){
        /*
        * add websiteButton to List
        * handles websiteButtons changed
        */
        websiteButtons.add(websiteButton);
        websiteButtonsChanged();
    }

    public void removeWebsiteButton(WebsiteButton websiteButton){
        /*
        * removes websiteButton
        * handles websiteButtons changed
        */
        websiteButtons.remove(websiteButton);
        websiteButtonsChanged();
    }


    //+++++++++++++++++++++++++++++++
    //other methods                 +
    //+++++++++++++++++++++++++++++++

    private void websiteButtonsChanged() {
        /*
        * called if websiteButtons-List has changed
        * -> sort List
        * filter shown Elements
        * set changedData true
        */
        websiteButtons.sort(Comparator.comparing(Labeled::getText));
        filterShownWebsiteButtons();
        tempDataChanged = true;
    }

    private void filterShownWebsiteButtons(){
        shownWebsiteButtons.clear();
        for (WebsiteButton websiteButton : websiteButtons){
            String[] words = websiteButton.getText().split(" ");
            for (String word: words){
                if (!word.substring(0, inputText.length() <= word.length() ? inputText.length() : word.length()).toUpperCase().equals(inputText)) continue;
                shownWebsiteButtons.add(websiteButton);
                break;
            }
        }
    }

    //+++++++++++++++++++++++++++++++
    //getter and setter             +
    //+++++++++++++++++++++++++++++++

    public Stage getStage() {
        return stage;
    }

    public ObservableList<Node> getShownWebsiteButtons() {
        return shownWebsiteButtons;
    }

    public void setFileName(String fileName) {
        /*
        * if new fileName set
        * create new SimpleDataHandler with it
        */
        this.fileName = fileName;
        this.dataHandler = new SimpleDataHandler(fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isTempDataChanged() {
        return tempDataChanged;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        //filers the InputText when it changes
        filterShownWebsiteButtons();
    }

    public void appendInputText(String keyCode) {
        setInputText(inputText + keyCode);
    }

    public String getInputText() {
        return inputText;
    }
}
