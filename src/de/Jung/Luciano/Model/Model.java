package de.Jung.Luciano.Model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private ObservableList<WebsiteLink> websiteLinks;
    private String fileName;
    private SimpleDataHandler dataHandler;

    //++++++++++++++++++++++++++++++++
    //contructor
    //++++++++++++++++++++++++++++++++

    public Model() {
        websiteLinks = FXCollections.observableArrayList();
        fileName = "C:\\Users\\Luciano\\Dropbox\\Privat\\websiteLinks\\savedWebsiteLinks.txt";
        dataHandler = new SimpleDataHandler(fileName);

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
        return websiteLinks;
    }
}
