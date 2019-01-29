package de.Jung.Luciano.Model;

import de.Jung.Luciano.Data_Handler.SimpleDataHandler;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Model {

    private Stage stage;
    private List<WebsiteButton> websiteButtons;
    private SimpleDataInterface dataHandler;
    private String fileName = "C:\\Users\\Public\\Documents\\savedWebsiteLinks.txt";

    //+++++++++++++++++++++++++++++++
    //constructor                   +
    //+++++++++++++++++++++++++++++++

    public Model(Stage stage) {
        this.stage = stage;
        this.websiteButtons = new ArrayList<>();
        this.dataHandler = new SimpleDataHandler();                  //add fileName in Constructor to save it somewhere else

        /*
        * test if the File exists and has already Data
        * if size() of the data loaded is > 0 means it has!
        * if it has load the Data
        */
        dataHandler.save(new ArrayList<>(), false);
        List<WebsiteButton> data = loadData();
        if (data.size() == 0) return;
        //else
        System.out.println("File have Data in it! Loading...");
        addAllWebsiteButtons(data);
    }

    //+++++++++++++++++++++++++++++++
    //Save and Load Data-Methods    +
    //+++++++++++++++++++++++++++++++


    public void saveData(boolean override) {
        /*
        * for each WebsiteButton from dataList
        *   create one Object of WebsiteButton
        * save Data via SimpleDataInterface (uses toString()-Method, to save Object Data)
        */
        List<Object> objects = new ArrayList<>();
        for (WebsiteButton websiteButton : websiteButtons){
            objects.add(websiteButton);
        }
        System.out.println("Saving Data: " + objects.size() + " Objects");
        dataHandler.save(objects, override);
    }

    public List<WebsiteButton> loadData() {
        /*
        * if (fileName is no File) return
        * else:
        *   create List<WebsiteButton>
        *   load Data via SimpleDataInterface in another List<Object>
        *   look if Object is a String with two Strings seperated with a ","
        *       if yes: add a new WebsiteButton to first List (String[0] = Name, String[1] = Url)
        *   return first List
        */
        if(!new File(fileName).isFile()) return null;
        List<WebsiteButton> websiteButtonList = new ArrayList<>();

        List<Object> rawData = dataHandler.load();
        for (Object websiteButton : rawData){
            String[] strings = websiteButton.toString().split(",");
            if (strings.length != 2) continue;
            websiteButtonList.add(new WebsiteButton(strings[0], strings[1]));
        }
        System.out.println("Load Data, Found " + websiteButtonList.size() + " WebsiteButtons!");
        websiteButtons.sort(Comparator.comparing(Labeled::getText));
        return websiteButtonList;
    }

    //+++++++++++++++++++++++++++++++
    //getter and setter             +
    //+++++++++++++++++++++++++++++++

    public Stage getStage() {
        return stage;
    }

    public List<WebsiteButton> getWebsiteButtons() {
        websiteButtons = loadData();
        return websiteButtons;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        this.dataHandler = new SimpleDataHandler(fileName);
    }

    //+++++++++++++++++++++++++++++++
    //add and remove                +
    //+++++++++++++++++++++++++++++++

    public void addWebsiteButton(WebsiteButton websiteButton){
        websiteButtons.add(websiteButton);
        websiteButtons.sort(Comparator.comparing(Labeled::getText));
        //add Funktionality: actualise Data and/or Screen
        saveData(true);
    }

    public void addAllWebsiteButtons(List<WebsiteButton> list){
        for (WebsiteButton websiteButton : list){
            addWebsiteButton(websiteButton);
        }
    }

    public void removeWebsiteButton(WebsiteButton websiteButton){
        websiteButtons.remove(websiteButton);
        websiteButtons.sort(Comparator.comparing(Labeled::getText));
        //add Funktionality: actualise Data and/or Screen
        saveData(true);
    }
}
