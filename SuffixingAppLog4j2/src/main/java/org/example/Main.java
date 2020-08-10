package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Main {
    protected static final Logger logr = LogManager.getLogger();

    public static void main(String[] args) {
        logr.info( "application has  started");

        Config config = new Config();
        Properties properties = config.getPropValues();
        String suffix = properties.getProperty("suffix");
        String filePaths = properties.getProperty("filePaths");
        List<String> filePathArray = new ArrayList<>(Arrays.asList(filePaths.split(",")));

        logr.info("reading and analyzing content of config file");
        for (String currentPath : filePathArray) {
            String filePath = currentPath;
            File oldFile = new File(filePath);
            if (oldFile.exists()) {
                logr.info(oldFile.getName() + " exists" );
                File newFileName = new File(filePath.substring(0, filePath.lastIndexOf('.')) + properties.getProperty("suffix") + filePath.substring(filePath.lastIndexOf('.'), filePath.length()));
                boolean success = oldFile.renameTo(newFileName);
                logr.info("new name " + newFileName.getName() + " was generated from " + oldFile.getName() +  " because of the suffix - " + suffix);

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

    }
}
