package com.example.testapp.lib;

public class ConfigurationManager {
    private static ConfigurationManager instance = null;
    private String id;

    public static ConfigurationManager getInstance() {
        if (instance == null) instance = new ConfigurationManager();
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void clear() {
        instance = new ConfigurationManager();
    }
}
