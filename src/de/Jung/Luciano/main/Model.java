package de.Jung.Luciano.main;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private ObservableList<WebsiteLink> WebsiteLinks = FXCollections.observableArrayList();
    private final String fileName = "C:\\Users\\Luciano\\Dropbox\\Privat\\WebsiteLinks\\savedWebsiteLinks.txt";

    public Model() {
        //Listener
        WebsiteLinks.addListener((ListChangeListener.Change<? extends WebsiteLink> change) -> {
            while(change.next()){
                if(change.wasUpdated()){
                    System.out.println("Update detected");
                }
                else{
                    for (WebsiteLink remitem : change.getRemoved()) {
                        System.out.println("Website removed");
                    }
                    for (WebsiteLink additem : change.getAddedSubList()) {
                        System.out.println("Website Added");
                    }
                }
            }
        });
        System.out.println(loadData().get(0));
    }

    //++++++++++++++++++++++++++++++++
    //load and save
    //++++++++++++++++++++++++++++++++

    public void saveData(List<WebsiteLink> dataBlock) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (WebsiteLink websiteLink : dataBlock) {
            addData(websiteLink);
        }
    }

    public void addData(WebsiteLink data) {
        try {
            FileWriter fileWriter = new FileWriter (fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append(
                    data.getUrlName() +
                            "," + data.getUrl() +
                            "," + data.getKeyCode());
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> loadData(){
        List<String> dataList = new ArrayList<>();
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            dataList.add(bufferedReader.readLine());

        }catch (IOException e){
            e.printStackTrace();
        }
        return dataList;
    }

    //+++++++++++++++++++++++++
    //getter and setter
    //++++++++++++++++++++++++++++++

    public ObservableList<WebsiteLink> getWebsiteLinks() {
        return WebsiteLinks;
    }
}
