package com.gfk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfk.domain.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationService {

    @Autowired
    private SchemaService schemaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CSVAdapter csvAdapter = new CSVAdapter();
    private final JsonAdapter jsonAdapter = new JsonAdapter();

    public List<String> generate(final String json) throws IOException {

        final Map<String, Object> specification = objectMapper.readValue(json, Map.class);

        final Schema schema = schemaService.getSchema();


        final List<String> csvData = new ArrayList<>();
        for (Map.Entry<String, Object> entry : specification.entrySet()) {
            final String key = entry.getKey();
            final Map<String, Object> values = (Map<String, Object>)entry.getValue();

            csvData.add(csvAdapter.jsonToCsv(key, values, schema));
        }

        return csvData;
    }

    public String importCsv(final List<String> csv) throws IOException {
        final Map<String, Object> data = new HashMap<>();

        // drop first line == headers
        for (final String csvLine : csv.subList(1, csv.size())) {
            final Map<String, Object> m = jsonAdapter.csvToJson(csvLine);
            final String key = m.get("target").toString();
            data.put(key, m);
        }

        final String result = objectMapper.writeValueAsString(data);
        return result;
    }
}
