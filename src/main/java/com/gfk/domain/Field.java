package com.gfk.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
    private String name;
    private Object type;
    @JsonProperty("default")
    private String defaulValue;

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
