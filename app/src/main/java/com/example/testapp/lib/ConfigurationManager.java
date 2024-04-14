package com.example.testapp.lib;

import com.example.testapp.middleware.Auth;
import com.example.testapp.model.TravelPlan;

public class ConfigurationManager {
    private final static String TAG = "ConfigurationManager";
    private static ConfigurationManager instance = null;
    private String id;
    private CallbackFunction enableNavFunction = null;
    private CallbackFunction disableNavFunction = null;
    private TravelPlan selectedTravelPlan = null;

    public static ConfigurationManager getInstance() {
        if (instance == null) instance = new ConfigurationManager();
        return instance;
    }

    public void setSelectedTravelPlan(TravelPlan selectedTravelPlan) {
        this.selectedTravelPlan = selectedTravelPlan;
    }

    public boolean isOwnerOfTravelPlan() {
        if (selectedTravelPlan == null) return false;
        return Auth.getInstance().getUsername().equals(selectedTravelPlan.getCreator());
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

    public void setId(String travelPlanId) {
        this.id = travelPlanId;
        if (travelPlanId == null) {
            if (disableNavFunction != null) disableNavFunction.doFunction();
            else if (enableNavFunction != null) enableNavFunction.doFunction();
        }
    }

    public void clear() {
        instance = new ConfigurationManager();
    }
}
