package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.*;

import static org.example.Main.logr;


public class Config {
    InputStream inputStream;


    public Properties getPropValues() {

        Properties prop = new Properties();
        String propFileName = "suffix-config.properties";
        try {
            logr.log(Level.INFO, "Started loading resources from config resources file");
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
                logr.log(Level.INFO, "loading resources from config resources file has finished");

            } else {
                throw new FileNotFoundException();
            }
        } catch (IOException ex) {
            logr.log(Level.SEVERE, " Property file " + propFileName + " not found in the classpath", ex);
            ex.printStackTrace();
        }
        return prop;
    }
}