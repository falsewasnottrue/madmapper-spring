package com.gfk.services;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonAdapterTest {

    final JsonAdapter adapter = new JsonAdapter();

    @Test
    public void testDirectCSVToJson() throws Exception {
        final String csv = "panelistid;ID;y;string;pnr;regex: ^[0-9a-zA-Z]+$;;regex taken from http://www.askingbox.com/tip/php-permit-only-certain-letters-numbers-and-characters-in-a-string;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

        final Map<String, Object> data = adapter.csvToJson(csv);
        assertEquals("pnr", data.get("target"));
        assertEquals("direct", data.get("type"));
        assertEquals(null, data.get("origin"));
        assertEquals(true, data.get("required"));
        assertEquals("panelistid", data.get("source"));
    }

    @Test
    public void testDefaultCSVToJson() throws Exception {
        //final String csv = "gxl_agehh_hhleader_1;P;n;string;gxl_agehh_hhleader_1;;-2;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";
        final String csv = "GXL_Smartphone_User;;y;integer;gxl_smartphone_user;1;;;2;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

        final Map<String, Object> data = adapter.csvToJson(csv);
        assertEquals("gxl_agehh_hhleader_1", data.get("target"));
        assertEquals("default", data.get("type"));
        assertEquals("person", data.get("origin"));
        assertEquals(false, data.get("required"));
        assertEquals("gxl_agehh_hhleader_1", data.get("source"));
        assertEquals("-2", data.get("value"));
    }

    @Test
    public void testMappingCSVToJson() throws Exception {
        final String csv = "age0_18_25_35_45_55_65_max;P;y;integer;mst_age_metric;18-24;21;;25-34;30;45-54;50;35-44;40;55-64;60;<18;17;>=65;70;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";


        final Map<String, Object> data = adapter.csvToJson(csv);
        assertEquals("mst_age_metric", data.get("target"));
        assertEquals("mapping", data.get("type"));
        assertEquals("person", data.get("origin"));
        assertEquals(true, data.get("required"));
        assertEquals("age0_18_25_35_45_55_65_max", data.get("source"));
        final List<Object> mappings = new ArrayList<>();

        final Map<String, Object> m1 = new HashMap<>();
        m1.put("id", "1");
        m1.put("key", "<18");
        m1.put("value", "17");
        mappings.add(m1);

        final Map<String, Object> m2 = new HashMap<>();
        m2.put("id", "2");
        m2.put("key", "18-24");
        m2.put("value", "21");
        mappings.add(m2);

        final Map<String, Object> m3 = new HashMap<>();
        m3.put("id", "3");
        m3.put("key", "25-34");
        m3.put("value", "30");
        mappings.add(m3);

        final Map<String, Object> m4 = new HashMap<>();
        m4.put("id", "4");
        m4.put("key", "35-44");
        m4.put("value", "40");
        mappings.add(m4);

        final Map<String, Object> m5 = new HashMap<>();
        m5.put("id", "5");
        m5.put("key", "45-54");
        m5.put("value", "50");
        mappings.add(m5);

        final Map<String, Object> m6 = new HashMap<>();
        m6.put("id", "6");
        m6.put("key", "55-64");
        m6.put("value", "60");
        mappings.add(m6);

        final Map<String, Object> m7 = new HashMap<>();
        m7.put("id", "7");
        m7.put("key", ">=65");
        m7.put("value", "70");
        mappings.add(m7);

        assertEquals(mappings, data.get("mapping"));
    }
}
