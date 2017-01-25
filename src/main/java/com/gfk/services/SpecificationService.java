package com.gfk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfk.domain.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationService {

    @Autowired
    private SchemaService schemaService;

    public void save(final String json) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Object> specification = objectMapper.readValue(json, Map.class);

        final Schema schema = schemaService.getSchema();
        final CSVAdapter csvAdapter = new CSVAdapter();

        final List<String> csvData = new ArrayList<>();
        for (Map.Entry<String, Object> entry : specification.entrySet()) {
            final String key = entry.getKey();
            final Map<String, Object> values = (Map<String, Object>)entry.getValue();

            csvData.add(csvAdapter.jsonToCsv(key, values, schema));
        }

        System.out.println(csvData);
        // TODO save to file
    }
}
