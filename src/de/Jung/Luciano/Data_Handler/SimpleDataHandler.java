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
     * save(List<Object>)-Method
     * -> uses toString-Method from Object to store data
     * -> data stored in a simple *.txt File
     *
     * load()-Method
     * -> return a List<Object>
     * -> simply the Strings per Line
     *
     * define the Path with <fileName>
     * */

    private String fileName;

    //++++++++++++++++++++++++++++++++
    //constructors
    //++++++++++++++++++++++++++++++++

    public SimpleDataHandler(String fileName) {
        /*
        * stores fileName given (default: save.txt)
        * save and load data first time
        * -> doesnt override data
        * -> creates file if it hadn't exists yet
        */
        this.fileName = fileName;
        this.save(new ArrayList<>(), false);
        if (load().size() > 0)
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
            /*
            * override data first If true
            * -> creates a bufferedWriter
            * -> override with empty String
            * -> close bufferedWriter
            *
            * creates new BufferedWriter (append is true)
            * -> append object.toString() for each object from the List
            * -> make a new Line for each object
            * -> close bufferedWriter
            *
            * catches IOException and NullPointerException
            */
            FileWriter fileWriter;
            if (override){                                                                                              //overrides all data if true
                fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("");
                bufferedWriter.close();
            }

            fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Object objects : dataList) {
                bufferedWriter.append(objects.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<String> load() {
        /*
        * saves in a dataList each Line in one String
        * -> Uses BufferedReader with the fileName
        * -> returns the List
        */
        List<String> dataList = new ArrayList<>();
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
}
