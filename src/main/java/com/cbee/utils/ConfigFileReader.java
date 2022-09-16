package com.cbee.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private final static String PROPERTY_FILE_PATH = "configs//qb_configs.properties";


    public ConfigFileReader() throws IOException {
        try {
            try (FileReader fr = new FileReader(PROPERTY_FILE_PATH);
                 BufferedReader reader = new BufferedReader(fr)) {
                properties = new Properties();
                properties.load(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException("qb_configs.properties not found at " + PROPERTY_FILE_PATH);
        }
    }

    public String getSiteUserName(){
        String siteUserName = properties.getProperty("siteUserName");
        if(siteUserName!= null)
            return siteUserName;
        else
            throw new RuntimeException("siteUserName not specified in the qb_configs.properties file.");
    }

    public String getSitePassword(){
        String sitePassword = properties.getProperty("sitePassword");
        if(sitePassword!= null)
            return sitePassword;
        else
            throw new RuntimeException("sitePassword not specified in the qb_configs.properties file.");
    }
}
