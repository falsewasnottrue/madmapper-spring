package com.gfk.domain;

public class Schema {
    private String type;
    private String name;
    private String namespace;

    private Field[] fields;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public Field[] getFields() {
        return fields;
    }
}
