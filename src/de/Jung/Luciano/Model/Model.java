package de.Jung.Luciano.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private ObservableList<WebsiteLink> WebsiteLinks;
    private String fileName;
    SimpleDataHandler dataHandler;

    //++++++++++++++++++++++++++++++++
    //contructor
    //++++++++++++++++++++++++++++++++

    public Model() {
        WebsiteLinks = FXCollections.observableArrayList();
        fileName = "C:\\Users\\Luciano\\Dropbox\\Privat\\WebsiteLinks\\savedWebsiteLinks.txt";
        dataHandler = new SimpleDataHandler(fileName);
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

    public void saveData(List<List<String>> dataList){
        dataHandler.save(dataList, false);
    }

    public List<List<String>> loadData(){
        return dataHandler.load();
    }

    //+++++++++++++++++++++++++
    //getter and setter
    //++++++++++++++++++++++++++++++

    public ObservableList<WebsiteLink> getWebsiteLinks() {
        return WebsiteLinks;
    }
}
