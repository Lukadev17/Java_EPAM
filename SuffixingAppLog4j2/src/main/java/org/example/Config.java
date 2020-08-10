package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import static org.example.Main.logr;

public class Config {
    InputStream inputStream;

    public Properties getPropValues() {
        Properties prop = new Properties();
        String propFileName = "suffix-config.properties";
        logr.info( "Started loading resources from config resources file");
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
                logr.info("loading resources from config resources file has finished");
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (IOException ex) {
            logr.fatal( " Property file " + propFileName + " not found in the classpath", ex);
            ex.printStackTrace();
        }
        return prop;
    }
}