package com.gfk.domain;

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class FieldSpec {

    @Test
    public void shouldExtractString() throws Exception {
        Field field = new Field();
        field.setType("string");

        assertEquals("string", field.getTargetType());
    }

    @Test
    public void shouldExtractInteger() throws Exception {
        Field field = new Field();
        field.setType("int");

        assertEquals("integer", field.getTargetType());
    }

    @Test
    public void shouldExtractDouble() throws Exception {
        Field field = new Field();
        field.setType("double");

        assertEquals("double", field.getTargetType());
    }

    @Test
    public void shouldExtractTargetTypeList() throws Exception {
        Field field = new Field();
        field.setType(Arrays.asList("null", "int"));

        assertEquals("integer", field.getTargetType());
    }

    @Test
    public void shouldExtractTargetTypeMap() throws Exception {
        Field field = new Field();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("type", "map");
        map.put("values", "int");
        field.setType(map);

        assertEquals("integer", field.getTargetType());
    }

    @Test
    public void shouldFallbackToString() throws Exception {
        Field field = new Field();
        field.setType("object");

        assertEquals("string", field.getTargetType());
    }
}
