package com.konifar.material_icon_generator;

import com.google.gson.Gson;

/**
 * Created by family_lee on 2016/2/8.
 */
public class IconInfo{
    private boolean enabled;
    private String name;
    private String color;
    private String state;
    private String stateValue;

    public IconInfo(){
    }

    public IconInfo(boolean isEnabled,String name, String color,String state,String stateValue) {
        this.enabled=isEnabled;
        this.name = name;
        this.color = color;
        this.state=state;
        this.stateValue=stateValue;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isStateNone(){
        return "none".equalsIgnoreCase(state);
    }

    public String getStateValue() {
        return stateValue;
    }

    public void setStateValue(String stateValue) {
        this.stateValue = stateValue;
    }

    @Override
    public String toString() {
       Gson gson=new Gson();
        return gson.toJson(this,IconInfo.class);
    }
}
