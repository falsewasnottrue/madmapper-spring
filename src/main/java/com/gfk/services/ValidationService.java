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
            if (field.isRequired() && specification.get(field.getName()) == null) {
                result.put(field.getName(), "This field is required");
            }
        }

        return result;
    }
}
