package de.Jung.Luciano.Model;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXMessageDialog;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimpleDataHandler {

    /*
     * class for simple save and load objects in a List
     * Writing via FileWriter and BufferedWriter
     * Reading via BufferedReader
     *
     * take a List<List<String>> when save
     * -> so you have the inner List for an Object with its param
     * -> and you can give various Object Param
     * gives back a List<List<String>> when load
     *
     * define the Path with <fileName>
     * */

    private String fileName;

    //++++++++++++++++++++++++++++++++
    //constructors
    //++++++++++++++++++++++++++++++++

    public SimpleDataHandler(String fileName) {
        this.fileName = fileName;
    }

    public SimpleDataHandler() {
        this("save.txt");
    }


    //++++++++++++++++++++++++++++++++
    //save and load
    //++++++++++++++++++++++++++++++++

    protected void save(List<List<String>> dataList, boolean override) {
        try {
            //override if true
            if (override){
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                bufferedWriter.write("");
                bufferedWriter.close();
            }
            //create fileWriter and BufferedWriter
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            /*append the data for each String in the Data Komplex
            * the inner List is for one Object-Data seperated with ,
            * the outer list is for various of object-data seperated with a new Line
            */
            for (List<String> innerDataList : dataList) {
                for (String stringData : innerDataList) {
                    bufferedWriter.append(stringData + ",");
                }
                bufferedWriter.newLine();
            }
            //close bufferedWriter and return
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException n){
            n.printStackTrace();
        }
    }


    protected List<List<String>> load() {
        List<List<String>> dataList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for (int i = 0; i < Files.lines(Paths.get(fileName)).count(); i++) {
                String[] strings = bufferedReader.readLine().split(",");
                List<String> innerDataList = new ArrayList<>();
                for ( String stringData : strings) {
                    innerDataList.add(stringData);
                }
                dataList.add(innerDataList);
            }
        } catch (IOException e) {
            System.out.println("No File existing yet!");
        }
        return dataList;
    }

    //+++++++++++++++++++++++++
    //getter and setter
    //+++++++++++++++++++++++++


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
