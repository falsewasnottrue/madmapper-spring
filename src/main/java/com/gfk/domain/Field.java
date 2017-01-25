package com.gfk.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Field {
    private String name;
    private Object type;
    @JsonProperty("default")
    private String defaultValue;

    public void setName(String name) {
        this.name = name;
    }
    public void setType(Object type) {
        this.type = type;
    }
    public void setDefaultValue(String defaulValue) {
        this.defaultValue = defaulValue;
    }

    public String getTargetType() {
        final Map<String, String> types = new HashMap<String, String>() {{
            put("int", "integer");
            put("string", "string");
            put("double", "double");
        }};

        if (type instanceof String) {
            return types.getOrDefault(type, "string");
        } else if (type instanceof List && ((List)type).size() == 2) {
            return types.getOrDefault(((List)type).get(1), "string");
        } else if (type instanceof Map) {
            return types.getOrDefault(((Map)type).get("values"), "string");
        }
        return "string";
    }

    public String getName() {
        return name;
    }
    public Object getType() {
        return type;
    }
    public String getDefaultValue() {
        return defaultValue;
    }
}
