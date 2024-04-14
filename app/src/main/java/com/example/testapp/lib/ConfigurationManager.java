package com.example.testapp.lib;

public class ConfigurationManager {
    private final static String TAG = "ConfigurationManager";
    private static ConfigurationManager instance = null;
    private boolean isOwnerOfTravelPlan = false;
    private String id;
    private CallbackFunction enableNavFunction = null;
    private CallbackFunction disableNavFunction = null;

    public static ConfigurationManager getInstance() {
        if (instance == null) instance = new ConfigurationManager();
        return instance;
    }

    public boolean isOwnerOfTravelPlan() {
        return isOwnerOfTravelPlan;
    }

    public void setOwnerOfTravelPlan(boolean ownerOfTravelPlan) {
        isOwnerOfTravelPlan = ownerOfTravelPlan;
    }

    public void setEnableNavFunction(CallbackFunction enableNavFunction) {
        this.enableNavFunction = enableNavFunction;
    }

    public void setDisableNavFunction(CallbackFunction disableNavFunction) {
        this.disableNavFunction = disableNavFunction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        if (id == null) {
            if (disableNavFunction != null) disableNavFunction.doFunction();
            else if (enableNavFunction != null) enableNavFunction.doFunction();
        }
    }

    public void clear() {
        instance = new ConfigurationManager();
    }
}
