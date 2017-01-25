package com.gfk.domain;

public class Schema {
    private String type;
    private String name;
    private String namespace;

    private Field[] fields;

    public void setType(String type) {
        this.type = type;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    public void setFields(Field[] fields) {
        this.fields = fields;
    }

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
