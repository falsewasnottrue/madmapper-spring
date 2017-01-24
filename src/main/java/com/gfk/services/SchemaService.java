package com.gfk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

@Service
public class SchemaService {

    @Value("${schema.file}")
    private String schemaFile;

    // TODO expose as model files
    private String schema;

    @Value(value = "classpath:enriched_master_data_v1.avsc")
    private Resource schemaResource;

    @PostConstruct
    public void init() throws IOException {
        // final ObjectMapper jsonMapper = new ObjectMapper();
        // .

        java.util.Scanner s = new java.util.Scanner(schemaResource.getInputStream()).useDelimiter("\\A");
        schema = s.hasNext() ? s.next() : "";
      // jsonMapper.
    }

    public String getSchema() {
        return schema;
    }
}
