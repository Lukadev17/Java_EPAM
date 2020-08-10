package org.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.*;

public class Main {
    protected final static Logger logr;
    static {
        InputStream stream = Main.class.getClassLoader().
                getResourceAsStream("config.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logr = Logger.getLogger(Main.class.getName());
    }

    public static void main( String[] args ) {

        logr.log(Level.INFO, "application has  started");

        Config config = new Config();
        Properties properties = config.getPropValues();
        String suffix = properties.getProperty("suffix");
        String filePaths = properties.getProperty("filePaths");
        List<String> filePathArray = new ArrayList<>(Arrays.asList(filePaths.split(",")));

        logr.log(Level.INFO, "reading and analyzing content of config file");
        for (String currentPath : filePathArray) {
            String filePath = currentPath;
            File oldFile = new File(filePath);

            if (oldFile.exists()) {
                logr.info(oldFile.getName() + " exists" );
                File newFileName = new File(filePath.substring(0, filePath.lastIndexOf('.')) + properties.getProperty("suffix") + filePath.substring(filePath.lastIndexOf('.'), filePath.length()));
                logr.info("new name " + newFileName.getName() + " was generated from " + oldFile.getName() +  " because of the suffix - " + suffix);
                boolean success = oldFile.renameTo(newFileName);

                if (success) {
                    logr.info(oldFile.getName() + " renamed to " + newFileName.getName());
                    System.out.println(oldFile.getName() + " -> " + newFileName.getName());
                } else {
                    logr.warning(oldFile.getName() + " didn't renamed successfully");
                    System.out.println("file didnt renamed successfully");
                }
            } else {
                logr.severe(oldFile.getName() + " file didn't exist, file renaming process failed");
                System.out.println(oldFile.getName() + " did not exist");
            }
        }

    }
}
