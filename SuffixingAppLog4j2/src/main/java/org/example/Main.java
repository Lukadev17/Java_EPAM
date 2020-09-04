package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Main {
    protected static final Logger logr = LogManager.getLogger();
    private static String oldFiles = "";
    private static String newFiles = "";
    public  static Properties properties;

    public static void main(String[] args) throws IOException {
        logr.info( "application has  started");

        Config config = new Config();
        properties = config.getPropValues();

        String filePaths = properties.getProperty("filePaths");
        List<String> filePathArray = new ArrayList<>(Arrays.asList(filePaths.split(",")));

        logr.info("reading and analyzing content of config file");
        List<MyFile> data =  getData(filePathArray);
        JDOMCreator creator = new JDOMCreator();
        Document doc = creator.createXMLDocument(data);
        //test if works
        List<Element> list = doc.getRootElement().getChildren(); //it should be deleted.
        System.out.println("Retrieved " + list.size());////it should be deleted.

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xmlString = outputter.outputString(doc);
        System.out.println(xmlString);

        FileWriter fileWriter = new FileWriter(new File("./output/suffixingApp.xml"));
        outputter.output(doc, fileWriter);

        GSONCreator gsonCreator = new GSONCreator();
        gsonCreator.writeJSON(data);

        if (oldFiles.equals("") == false && newFiles.equals("") == false) {
            logr.info(oldFiles);
            logr.info(newFiles);
        }

        logr.info("Application finished");

    }

    public static List<MyFile> getData(List<String> filePathArray) {
        List<MyFile> Data = new ArrayList<>();
        for (String currentPath : filePathArray) {
            String filePath = currentPath;
            File oldFile = new File(filePath);
            if (oldFile.exists()) {
                MyFile file = new MyFile();
                logr.info(oldFile.getName() + " exists" );
                oldFiles = oldFiles + " " + oldFile.getName();
                File newFileName = new File(filePath.substring(0, filePath.lastIndexOf('.')) + properties.getProperty("suffix") + filePath.substring(filePath.lastIndexOf('.'), filePath.length()));
                boolean success = oldFile.renameTo(newFileName);
                logr.info("new name " + newFileName.getName() + " was generated from " + oldFile.getName() +  " because of the suffix - EPAM");
                newFiles =newFiles + " " + newFileName.getName();

                file.setOldName(oldFile.getName());
                file.setNewName(newFileName.getName());
                Data.add(file);

                if (success) {
                    logr.info(oldFile.getName() + " renamed to " + newFileName.getName());
                    System.out.println(oldFile.getName() + " -> " + newFileName.getName());
                } else {
                    logr.error(oldFile.getName() + " didn't renamed successfully");
                    System.out.println("file didnt renamed successfully");
                }
            } else {
                logr.error(oldFile.getName() + " file didn't exist, file renaming process failed");
                System.out.println(oldFile.getName() + " did not exist");
            }
        }
        return Data;
    }

}
