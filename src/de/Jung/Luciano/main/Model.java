package de.Jung.Luciano.main;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private ObservableList<WebsiteLink> WebsiteLinks = FXCollections.observableArrayList();
    private final String fileName = "C:\\Users\\Luciano\\Dropbox\\Privat\\WebsiteLinks\\savedWebsiteLinks.txt";

    //++++++++++++++++++++++++++++++++
    //load and save
    //++++++++++++++++++++++++++++++++

    public List<WebsiteLink> StringListToWebsiteLinkList(List<String> dataList){
        List<WebsiteLink> websiteLinkList = new ArrayList<>();
        for (String websiteString : dataList) {
            String[] strings = websiteString.split(",");
            WebsiteLink websiteLink = new WebsiteLink(strings[0], strings[1], KeyCode.getKeyCode(strings[2]));
            websiteLinkList.add(websiteLink);
        }
        return websiteLinkList;
    }

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
            for (int i = 0; i < Files.lines(Paths.get(fileName)).count(); i++) {
                dataList.add(bufferedReader.readLine());
            }
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
