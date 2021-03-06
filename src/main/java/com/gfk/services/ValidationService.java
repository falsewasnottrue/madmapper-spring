package com.gfk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfk.domain.Field;
import com.gfk.domain.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValidationService {

    @Autowired
    private SchemaService schemaService;

    public Map<String, String> validate(final String json) throws IOException {
        final Schema schema = schemaService.getSchema();
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Object> specification = objectMapper.readValue(json, Map.class);
        final Map<String, String> result = new HashMap<>();

        for (final Field field : schema.getFields()) {
            final List<String> messages = new ArrayList<>();
            // required fields
            if (field.isRequired() && specification.get(field.getName()) == null) {
                messages.add("This field is required");
            }

            if (specification.get(field.getName()) != null) {
                final Map<String, Object> spec = (Map<String, Object>) specification.get(field.getName());

                // data type for default and mapping
                if ("integer".equals(field.getTargetType())) {
                    if ("default".equals(spec.get("type")) && !isInt(spec.get("value"))) {
                        messages.add("The value must be an integer");
                    } else if ("mapping".equals(spec.get("type"))) {
                        final List<Object> mappings = (List<Object>) spec.get("mapping");
                        for (final Object mapping : mappings) {
                            final Map<String, Object> m = (Map<String, Object>) mapping;
                            if (!isInt(m.get("value"))) {
                                messages.add("The value " + m.get("key") + " must be mapped to an integer");
                            }
                        }
                    }
                }

                if ("double".equals(field.getTargetType())) {
                    if ("default".equals(spec.get("type")) && !isDouble(spec.get("value"))) {
                        messages.add("The value must be a double");
                    } else if ("mapping".equals(spec.get("type"))) {
                        final List<Object> mappings = (List<Object>) spec.get("mapping");
                        for (final Object mapping : mappings) {
                            final Map<String, Object> m = (Map<String, Object>) mapping;
                            if (!isDouble(m.get("value"))) {
                                messages.add("The value " + m.get("key") + " must be mapped to a double");
                            }
                        }
                    }
                }

                if ("direct".equals(spec.get("type")) || "mapping".equals(spec.get("type"))) {
                    if (spec.get("source") == null || "".equals(spec.get("source"))) {
                        messages.add("Source must be specified");
                    }
                }
            }

            if (messages.size() > 0) {
                result.put(field.getName(), String.join("<br/>", messages));
            }
        }

        return result;
    }

    private Boolean isInt(final Object value) {
        if (value == null) {
            return false;
        }
        try {
            Integer.parseInt(value.toString());
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    private Boolean isDouble(final Object value) {
        if (value == null) {
            return false;
        }
        try {
            Double.parseDouble(value.toString());
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
