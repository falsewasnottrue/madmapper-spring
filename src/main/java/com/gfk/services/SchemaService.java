package com.gfk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfk.domain.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Scanner;

@Service
public class SchemaService {

    // TODO expose as model files
    private Schema schema;

    @Value(value = "classpath:enriched_master_data_v1.avsc")
    private Resource schemaResource;

    @PostConstruct
    public void init() throws IOException {
        final ObjectMapper jsonMapper = new ObjectMapper();
        final Scanner s = new java.util.Scanner(schemaResource.getInputStream()).useDelimiter("\\A");
        final String schemaContent = s.hasNext() ? s.next() : "";

        schema = jsonMapper.readValue(schemaContent, Schema.class);
    }

    public Schema getSchema() {
        return schema;
    }
}
