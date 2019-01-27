package de.Jung.Luciano.Model;

import de.Jung.Luciano.Data_Handler.SimpleDataHandler;
import de.Jung.Luciano.Data_Handler.SimpleDataInterface;
import de.Jung.Luciano.WebsiteButton.WebsiteButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Model {

    private Stage stage;
    private ObservableList<WebsiteButton> websiteButtons;
    private String fileName;
    private SimpleDataInterface dataHandler;

    //++++++++++++++++++++++++++++++++
    //contructor
    //++++++++++++++++++++++++++++++++

    public Model(Stage stage) {
        this.stage = stage;
        websiteButtons = FXCollections.observableArrayList();
        fileName = "C:\\Users\\Public\\Documents\\savedWebsiteLinks.txt";
        //add fileName in Constructor to save it somewhere else
        dataHandler = new SimpleDataHandler();

        //test File
        saveData(new ArrayList<>(), false);
        if (loadData().size() != 0){
            //means that the File already Exists before and have text in it
            System.out.println("File is not Empty");
        }

        //load
        websiteButtons = loadData();

        //Listener
        /*websiteButtons.addListener((ListChangeListener.Change<? extends WebsiteButton> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    System.out.println("Website removed");
                    saveData(websiteButtons, true);
                } else if (change.wasAdded()) {
                    System.out.println("Website Added");
                    saveData(websiteButtons, true);
                } else if (change.wasUpdated()) {
                    System.out.println("Update detected");
                    saveData(websiteButtons, true);
                }
            }
        });*/
    }


    //++++++++++++++++++++++++++++++++
    //load and save
    //++++++++++++++++++++++++++++++++


    public void saveData(List<WebsiteButton> dataList, boolean override) {
        dataHandler.save(Arrays.asList(dataList.toArray()), override);
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

    //+++++++++++++++++++++++++
    //getter and setter
    //++++++++++++++++++++++++++++++

    public ObservableList<WebsiteButton> getWebsiteButtons() {
        return websiteButtons;
    }

    public Stage getStage() {
        return stage;
    }
}
