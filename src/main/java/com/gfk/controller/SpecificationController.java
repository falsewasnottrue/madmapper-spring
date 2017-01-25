package com.gfk.controller;

import com.gfk.services.SchemaService;
import com.gfk.services.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class SpecificationController {

    @Autowired
    private SchemaService schemaService;

    @Autowired
    private SpecificationService specificationService;

    @RequestMapping(value = "/specification")
    public String specification(final Model model) {
        // TODO load specification from file
        // TODO add to model
        model.addAttribute("schema", schemaService.getSchema());

        return "specification";
    }

    @RequestMapping(value = "/specification", method = RequestMethod.POST)
    public void saveSpecification(final @RequestParam("json") String json) {
        try {
            specificationService.save(json);
        } catch (IOException ioException) {
            // do something...
        }
    }

}
