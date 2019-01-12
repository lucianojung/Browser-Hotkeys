package de.Jung.Luciano.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private ObservableList<WebsiteLink> websiteLinks;
    private StringProperty folderNameProperty, fileNameProperty;
    private SimpleDataHandler dataHandler;

    //++++++++++++++++++++++++++++++++
    //contructor
    //++++++++++++++++++++++++++++++++

    public Model() {
        websiteLinks = FXCollections.observableArrayList();
        folderNameProperty = new SimpleStringProperty("C:\\Users");
        fileNameProperty = new SimpleStringProperty(folderNameProperty.get() + "\\savedWebsiteLinks.txt");
        dataHandler = new SimpleDataHandler(fileNameProperty.getValue());

        //Listener
        websiteLinks.addListener((ListChangeListener.Change<? extends WebsiteLink> change) -> {
            while(change.next()){
                if(change.wasRemoved()){
                    System.out.println("Website removed");
                    saveData(websiteLinks, true);
                }
                else if (change.wasAdded()){
                    System.out.println("Website Added");
                    saveData(websiteLinks, true);
                }
                else if (change.wasUpdated()){
                    System.out.println("Update detected");
                    saveData(websiteLinks, true);
                }
            }
        });
        folderNameProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("call Listener");
            if (oldValue.equals(newValue)) return;
            //else
            fileNameProperty.setValue(folderNameProperty.get() + "\\savedWebsiteLinks.txt");
            dataHandler.setFileName(fileNameProperty.getValue());
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

    public List<List<String>> loadData(){
        return dataHandler.load();
    }

    //+++++++++++++++++++++++++
    //getter and setter
    //++++++++++++++++++++++++++++++

    public ObservableList<WebsiteLink> getWebsiteLinks() {
        return websiteLinks;
    }

    public String getFolderNameProperty() {
        return folderNameProperty.get();
    }

    public StringProperty folderNamePropertyProperty() {
        return folderNameProperty;
    }
}
