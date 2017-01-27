package com.gfk.services;

import java.util.*;

public class JsonAdapter {

    private final String SEP = ";";

    public Map<String, Object> csvToJson(final String csvLine) {
        final Map<String, Object> result = new HashMap<>();
        final List<String> items = Arrays.asList(csvLine.split("\\s*;\\s*"));

        final Iterator<String> iterator = items.iterator();
        final String src_name = iterator.next();
        final String src_origin = iterator.next();
        final String mandatory =  iterator.next();
        iterator.next(); // ignore: trg_type
        final String trg_name =  iterator.next();
        final String src_value = iterator.next();
        final String trg_value = iterator.hasNext() ? iterator.next() : "";
        if (iterator.hasNext()) {
            iterator.next(); // ignore: note_value
        }

        result.put("type", "direct");
        result.put("target", trg_name);
        result.put("source", src_name);
        result.put("required", "y".equals(mandatory));
        if ("P".equals(src_origin)) {
            result.put("origin", "person");
        } else if ("HH".equals(src_origin)) {
            result.put("origin", "household");
        }

        if (src_value.length() == 0 && trg_value.length() > 0) {
            result.put("type", "default");
            result.put("value", trg_value);
        }

        if (iterator.hasNext()) {
            result.put("type", "mapping");
            int index = 1;
            final List<Object> mappings = new ArrayList<>();
            final Map<String, Object> m1 = new HashMap<>();
            m1.put("id", index++);
            m1.put("key", src_value);
            m1.put("value", trg_value);
            mappings.add(m1);

            while (iterator.hasNext()) {
                final String src_val = iterator.next();
                final String trg_val = iterator.hasNext() ? iterator.next() : "";

                final Map<String, Object> m = new HashMap<>();
                m.put("id", index++);
                m.put("key", src_val);
                m.put("value", trg_val);
                mappings.add(m);
            }
            result.put("mapping", mappings);
        }

        return result;
    }
}
