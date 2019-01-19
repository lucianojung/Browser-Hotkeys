package de.Jung.Luciano.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {

    private ObservableList<WebsiteLink> websiteLinks;
    private StringProperty fileName;
    private SimpleDataHandler dataHandler;
    private File file;

    //++++++++++++++++++++++++++++++++
    //contructor
    //++++++++++++++++++++++++++++++++

    public Model() {
        websiteLinks = FXCollections.observableArrayList();
        fileName = new SimpleStringProperty("C:\\Users\\Public\\Documents\\savedWebsiteLinks.txt");
        file = new File(fileName.getValue());
        dataHandler = new SimpleDataHandler(fileName.getValue());

        //Listener
        websiteLinks.addListener((ListChangeListener.Change<? extends WebsiteLink> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    System.out.println("Website removed");
                    saveData(websiteLinks, true);
                } else if (change.wasAdded()) {
                    System.out.println("Website Added");
                    saveData(websiteLinks, true);
                } else if (change.wasUpdated()) {
                    System.out.println("Update detected");
                    saveData(websiteLinks, true);
                }
            }
        });
    }


    //++++++++++++++++++++++++++++++++
    //load and save
    //++++++++++++++++++++++++++++++++


    public void saveData(List<WebsiteLink> dataBlock, boolean override) {
        List<List<String>> dataList = new ArrayList<>();

        for (WebsiteLink websiteLink : dataBlock) {
            List<String> innerDataList = new ArrayList<>();
            innerDataList.add(websiteLink.getUrlName());
            innerDataList.add(websiteLink.getUrl());
            innerDataList.add(websiteLink.getKeyCode().toString());
            dataList.add(innerDataList);
        }

        dataHandler.save(dataList, override);
    }

    public List<List<String>> loadData() {
        if(!file.isFile()) return null;
        return dataHandler.load();
    }

    //+++++++++++++++++++++++++
    //getter and setter
    //++++++++++++++++++++++++++++++

    public ObservableList<WebsiteLink> getWebsiteLinks() {
        return websiteLinks;
    }

}
