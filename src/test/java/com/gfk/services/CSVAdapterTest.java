package com.gfk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfk.domain.Field;
import com.gfk.domain.Schema;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CSVAdapterTest {

    final CSVAdapter adapter = new CSVAdapter();
    final Schema schema = new Schema();

    @Before
    public void init() {
        Field field1 = new Field();
        field1.setName("dep_valid_from");
        field1.setType("string");
        Field field2 = new Field();
        field2.setName("gxl_agehh_hhleader_1");
        field2.setType("string");
        Field field3 = new Field();
        field3.setName("mst_age_metric");
        field3.setType(Arrays.asList("null", "int"));

        schema.setFields(new Field[] { field1, field2, field3 });
    }

    @Test
    public void testDirectJsonToCSV() throws Exception {
        final Map<String, Object> data = new LinkedHashMap<>();
        data.put("type", "direct");
        data.put("origin", "household");
        data.put("required", "true");
        data.put("source", "gueltig_ab");

        final String csv = adapter.jsonToCsv("dep_valid_from", data, schema);

        assertEquals("gueltig_ab,HH,y,string,dep_valid_from", csv);
    }

    @Test
    public void testDefaultJsonToCSV() throws Exception {
        final Map<String, Object> data = new LinkedHashMap<>();
        data.put("type", "default");
        data.put("origin", "person");
        data.put("value", "-2");

        final String csv = adapter.jsonToCsv("gxl_agehh_hhleader_1", data, schema);

        assertEquals("gxl_agehh_hhleader_1,P,n,string,gxl_agehh_hhleader_1,,-2", csv);
    }

    @Test
    public void testMappingJsonToCSV() throws Exception {
        final Map<String, Object> data = new LinkedHashMap<>();
        data.put("type", "mapping");
        data.put("origin", "person");
        data.put("required", "true");
        data.put("source", "age0_18_25_35_45_55_65_max");

        final Map<String, Object> mapping1 = new LinkedHashMap<>();
        mapping1.put("id", "1");
        mapping1.put("key", "18-24");
        mapping1.put("value", "21");
        final Map<String, Object> mapping2 = new LinkedHashMap<>();
        mapping2.put("id", "1");
        mapping2.put("key", "25-34");
        mapping2.put("value", "30");
        final Map<String, Object> mapping3 = new LinkedHashMap<>();
        mapping3.put("id", "1");
        mapping3.put("key", "45-54");
        mapping3.put("value", "50");

        final List<Object> mappings = new ArrayList<>();
        mappings.add(mapping1);
        mappings.add(mapping2);
        mappings.add(mapping3);
        data.put("mapping", mappings);

        final String csv = adapter.jsonToCsv("mst_age_metric", data, schema);

        assertEquals("age0_18_25_35_45_55_65_max,P,y,integer,mst_age_metric,18-24,21,,25-34,30,45-54,50", csv);
    }
}
