package de.Jung.Luciano.Data_Handler;

import de.Jung.Luciano.Model.SimpleDataInterface;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimpleDataHandler implements SimpleDataInterface {

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
        save(new ArrayList<>(), false);
        if (load().size() == 0) return;
        //else show the User, that the File already Exists before and have text in it
        System.out.println("File is not Empty");
    }

    public SimpleDataHandler() {
        this("save.txt");
    }


    //++++++++++++++++++++++++++++++++
    //save and load
    //++++++++++++++++++++++++++++++++

    @Override
    public void save(List<Object> dataList, boolean override) {
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
            for (Object objects : dataList) {
                bufferedWriter.append(objects.toString() + ",");
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


    @Override
    public List<Object> load() {
        List<Object> dataList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for (int i = 0; i < Files.lines(Paths.get(fileName)).count(); i++) {
                dataList.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    //+++++++++++++++++++++++++
    //getter and setter
    //+++++++++++++++++++++++++


    protected String getFileName() {
        return fileName;
    }

    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
