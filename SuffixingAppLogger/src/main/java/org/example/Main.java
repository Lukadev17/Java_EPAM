package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;

public class Main {
    protected final static Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main( String[] args ) {
        LogManager.getLogManager().reset();

        logr.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        logr.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("myLogger.log");
            fh.setLevel(Level.ALL);
            logr.addHandler(fh);
        } catch (IOException e) {
            logr.log(Level.SEVERE, "File logger not working ", e);
        }

        logr.log(Level.INFO, "aplication has  started");

        Config config = new Config();
        Properties properties = config.getPropValues();
        String suffix = properties.getProperty("suffix");
        logr.info("reading and analyzing content of config file ");
        for (String key : properties.stringPropertyNames()) {
            if (key.equals("suffix")) {
                continue;
            }
            String filePath = properties.getProperty(key);
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
