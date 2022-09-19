package com.cbee.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private final static String PROPERTY_FILE_PATH = "configs//qb_configs.properties";


    public ConfigFileReader() {
        try {
            try (FileReader fr = new FileReader(PROPERTY_FILE_PATH);
                 BufferedReader reader = new BufferedReader(fr)) {
                properties = new Properties();
                properties.load(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("qb_configs.properties not found at " + PROPERTY_FILE_PATH);
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

    public String getBaseUrl(){
        String baseUrl = properties.getProperty("baseUrl");
        if(baseUrl!= null)
            return baseUrl;
        else
            throw new RuntimeException("baseUrl is not specified in the qb_configs.properties file.");
    }
    public String getClientKey(){
        String clientKey = properties.getProperty("clientKey");
        if(clientKey!= null)
            return clientKey;
        else
            throw new RuntimeException("clientKey is not specified in the qb_configs.properties file.");
    }
    public String getClientSecret(){
        String clientSecret = properties.getProperty("clientSecret");
        if(clientSecret!= null)
            return clientSecret;
        else
            throw new RuntimeException("clientSecret is not specified in the qb_configs.properties file.");
    }

    public String getApiKey(){
        String siteApiKey = properties.getProperty("siteApiKey");
        if(siteApiKey!= null)
            return siteApiKey;
        else
            throw new RuntimeException("siteApiKey is not specified in the qb_configs.properties file.");
    }

    public String getConfigValueByKey(String key){
        String configValue = properties.getProperty(key);
        if(configValue!= null)
            return configValue;
        else
            throw new RuntimeException(key + " is not specified in the qb_configs.properties file.");
    }
}
