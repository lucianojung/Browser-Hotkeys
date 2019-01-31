package de.Jung.Luciano.Model;

import de.Jung.Luciano.Data_Handler.SimpleDataHandler;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.scene.control.Labeled;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Model {

    private Stage stage;
    private List<WebsiteButton> websiteButtons, shownWebsiteButtons;        //maybe seperated List for shown WebsiteButtons, because of Shortcut search
    private SimpleDataInterface dataHandler;
    private String fileName = new File("save.txt").toString();
    private String inputText = "";
    private boolean tempDataChanged;

    //+++++++++++++++++++++++++++++++
    //constructor                   +
    //+++++++++++++++++++++++++++++++

    public Model(Stage stage) {
        this.stage = stage;
        this.websiteButtons = new ArrayList<>();
        this.shownWebsiteButtons = new ArrayList<>();
        this.dataHandler = new SimpleDataHandler();                  //add fileName in Constructor to save it somewhere else

        /*
        * test if the File exists and has already Data
        * if size() of the data loaded is > 0 means it has!
        * if it has load the Data
        */
        dataHandler.save(new ArrayList<>(), false);
        this.loadData();
        if (websiteButtons.size() == 0) return;
        //else
        System.out.println("File have Data in it! Loading...");
        tempDataChanged = false;
    }

    //+++++++++++++++++++++++++++++++
    //save and load Data            +
    //+++++++++++++++++++++++++++++++


    public void saveData() {
        /*
        * for each WebsiteButton from dataList
        *   create one Object of WebsiteButton
        * save Data via SimpleDataInterface (uses toString()-Method, to save Object Data)
        */
        List<Object> objects = new ArrayList<>(websiteButtons);
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

        List<Object> rawData = dataHandler.load();
        for (Object websiteButton : rawData){
            String[] strings = websiteButton.toString().split(",");
            if (strings.length != 2) continue;
                addWebsiteButton(new WebsiteButton(strings[0], strings[1]));
        }
        System.out.println("Load Data, Found " + websiteButtons.size() + " WebsiteButtons!");
        this.websiteButtons.sort(Comparator.comparing(Labeled::getText));
        this.filterShownWebsiteButtons();
    }

    //+++++++++++++++++++++++++++++++
    //add and remove                +
    //+++++++++++++++++++++++++++++++

    public void addWebsiteButton(WebsiteButton websiteButton){
        websiteButtons.add(websiteButton);
        websiteButtons.sort(Comparator.comparing(Labeled::getText));
        filterShownWebsiteButtons();
        tempDataChanged = true;
    }

    private void addAllWebsiteButtons(List<WebsiteButton> list){
        for (WebsiteButton websiteButton : list){
            addWebsiteButton(websiteButton);
        }
    }

    public void removeWebsiteButton(WebsiteButton websiteButton){
        websiteButtons.remove(websiteButton);
        websiteButtons.sort(Comparator.comparing(Labeled::getText));
        filterShownWebsiteButtons();
        //add Functionality: actualise Data and/or Screen
    }

    //+++++++++++++++++++++++++++++++
    //other methods                 +
    //+++++++++++++++++++++++++++++++

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

    public List<WebsiteButton> getShownWebsiteButtons() {
        return shownWebsiteButtons;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        this.dataHandler = new SimpleDataHandler(fileName);
    }

    public boolean isTempDataChanged() {
        return tempDataChanged;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        filterShownWebsiteButtons();
    }

    public void appendInputText(String keyCode) {
        setInputText(inputText + keyCode);
    }

    public String getInputText() {
        return inputText;
    }
}
