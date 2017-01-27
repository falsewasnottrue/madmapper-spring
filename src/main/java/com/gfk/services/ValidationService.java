package com.gfk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfk.domain.Field;
import com.gfk.domain.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
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

            // required fields
            if (field.isRequired() && specification.get(field.getName()) == null) {
                result.put(field.getName(), "This field is required");
            }

            // integer for default mapping
            if ("integer".equals(field.getTargetType()) && specification.get(field.getName()) != null) {
                final Map<String, Object> spec = (Map<String, Object>)specification.get(field.getName());
                if ("default".equals(spec.get("type")) && !isInt(spec.get("value"))) {
                    result.put(field.getName(), "The value must be an integer");
                }
            }

            // TODO source must be set
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
}
