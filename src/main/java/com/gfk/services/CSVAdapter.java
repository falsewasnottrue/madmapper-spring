package com.gfk.services;

import com.gfk.domain.Field;
import com.gfk.domain.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVAdapter {

    private final String SEP = ",";

    public String jsonToCsv(final String key, final Map<String, Object> values, final Schema schema) {
        final List<String> data = new ArrayList<>();

        if ("default".equals(values.get("type"))) {
            data.add(key);
        } else {
            data.add(values.get("source").toString());
        }
        data.add(getOrigin(values));
        data.add(getMandatory(values));
        data.add(getType(key, schema));
        data.add(key);

        if ("default".equals(values.get("type"))) {
            data.add(""); // empty key value for defaut
            data.add(values.get("value").toString());
        } else if ("mapping".equals(values.get("type"))) {
            if (values.get("mapping") instanceof List) {
                List<Object> mappings = (List<Object>) values.get("mapping");
                if (mappings.size() > 0) {
                    data.add(getMappingKey(mappings, 0));
                    data.add(getMappingValue(mappings, 0));
                }
                if (mappings.size() > 1) {
                    data.add(""); // empty note field
                    for (int i = 1; i < mappings.size(); i++) {
                        data.add(getMappingKey(mappings, i));
                        data.add(getMappingValue(mappings, i));
                    }
                }
            }
        }

        return String.join(SEP, data);
    }

    private String getOrigin(final Map<String, Object> values) {
        final Object value = values.get("origin");
        if ("household".equals(value)) {
            return "HH";
        } else if ("person".equals(value)) {
            return "P";
        }
        return "";
    }

    private String getMandatory(final Map<String, Object> values) {
        final Object value = values.get("required");
        return "true".equals(value) ? "y" : "n";
    }

    private String getType(final String key, final Schema schema) {
        for (Field field : schema.getFields()) {
            if (field.getName().equals(key)) {
                return field.getTargetType();
            }
        }
        return "";
    }

    private String getMappingKey(List<Object> mappings, int index) {
        if (mappings.size() > index) {
            if (mappings.get(index) instanceof Map) {
                final Map<String, Object> mapping = (Map<String, Object>) mappings.get(index);
                return mapping.get("key").toString();
            }
        }
        return "";
    }

    private String getMappingValue(List<Object> mappings, int index) {
        if (mappings.size() > index) {
            if (mappings.get(index) instanceof Map) {
                final Map<String, Object> mapping = (Map<String, Object>) mappings.get(index);
                return mapping.get("value").toString();
            }
        }
        return "";
    }
}
