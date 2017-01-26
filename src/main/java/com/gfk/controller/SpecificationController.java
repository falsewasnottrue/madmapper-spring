package com.gfk.controller;

import com.gfk.services.SchemaService;
import com.gfk.services.SpecificationService;
import com.gfk.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.List;

@Controller
public class SpecificationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SchemaService schemaService;

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/specs")
    public String listSpecifications(final Model model) {
        model.addAttribute("specifications", storeService.listSpecifications());
        return "specs";
    }

    @RequestMapping(value = "/specification/{specName}")
    public String specification(final @PathVariable String specName, final Model model) {
        model.addAttribute("specName", specName);
        model.addAttribute("schema", schemaService.getSchema());

        try {
            final String specification = storeService.load(specName);
            model.addAttribute("specification", specification);
        } catch (final IOException e) {
            logger.error("cannot load " + specName, e);
        }

        return "specification";
    }

    @RequestMapping(value = "/save/{specName}", method = RequestMethod.POST)
    public String saveSpecification(final @PathVariable String specName, final @RequestParam("json") String json) {
        try {
            storeService.save(specName, json);
        } catch (final IOException e) {
            logger.error("cannot save " + specName, e);
        }
        return "specs";
    }

    @RequestMapping(value = "/generate/{specName}")
    public String generateSpecification(final @PathVariable String specName, final Model model) {
        model.addAttribute("specName", specName);
        try {
            final String specification = storeService.load(specName);
            final List<String> csv = specificationService.generate(specification);

            model.addAttribute("csv", csv);
        } catch (final IOException e) {
            logger.error("cannot load " + specName, e);
        }

        return "csv";
    }

    @RequestMapping(value = "/validate/{specName}", method = RequestMethod.POST)
    public @ResponseBody String validateSpecification(final @PathVariable String specName, final @RequestParam("json") String json) {

        // TODO
        return "{\"dep_weights\": \"An error occured\"}";
    }


}
