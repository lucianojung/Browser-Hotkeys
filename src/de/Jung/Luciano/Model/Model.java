package de.Jung.Luciano.Model;

import de.Jung.Luciano.Data_Handler.SimpleDataHandler;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
        saveData(new ArrayList<>(), false);
        List<WebsiteButton> data = loadData();
        if (data.size() == 0) return;
        //else
        System.out.println("File have Data in it! Loading...");
        websiteButtons = data;

    }


    //+++++++++++++++++++++++++++++++
    //Save and Load Data-Methods    +
    //+++++++++++++++++++++++++++++++


    public void saveData(List<WebsiteButton> dataList, boolean override) {
        dataHandler.save(new ArrayList<>(Arrays.asList(dataList)), override);
    }

    public ObservableList<WebsiteButton> loadData() {

        if(!new File(fileName).isFile()) return null;
        List<WebsiteButton> websiteButtonList = new ArrayList<>();

        List<Object> rawData = dataHandler.load();
        for (Object websiteButton : rawData){
            String[] strings = websiteButton.toString().split(",");
            if (strings.length != 2) continue;
            websiteButtonList.add(new WebsiteButton(strings[0], strings[1]));
        }
        return FXCollections.observableArrayList(websiteButtonList);
    }

    //+++++++++++++++++++++++++++++++
    //getter and setter             +
    //+++++++++++++++++++++++++++++++

    public Stage getStage() {
        return stage;
    }

    public List<WebsiteButton> getWebsiteButtons() {
        return websiteButtons;
    }

    public void addWebsiteButton(WebsiteButton websiteButton){
        websiteButtons.add(websiteButton);
        //add Funktionality: actualise Data and/or Screen
    }

    public void removeWebsiteButton(WebsiteButton websiteButton){
        websiteButtons.remove(websiteButton);
        //add Funktionality: actualise Data and/or Screen
    }
}
