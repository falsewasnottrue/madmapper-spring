package com.gfk.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
    private String name;
    private Object type;
    @JsonProperty("default")
    private String defaulValue;

    public void setName(String name) {
        this.name = name;
    }
    public void setType(Object type) {
        this.type = type;
    }
    public void setDefaulValue(String defaulValue) {
        this.defaulValue = defaulValue;
    }


    public String getName() {
        return name;
    }
    public Object getType() {
        return type;
    }
    public String getDefaulValue() {
        return defaulValue;
    }
}
