package com.gfk.controller;

import com.gfk.services.SchemaService;
import com.gfk.services.SpecificationService;
import com.gfk.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

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

    @RequestMapping(value = "/validate/{specName}", method = RequestMethod.POST)
    public void validateSpecification(final @PathVariable String specName, final @RequestParam("json") String json) {
        // TODO
    }

    @RequestMapping(value = "/generate/{specName}", method = RequestMethod.POST)
    public void generateSpecification(final @PathVariable String specName, final @RequestParam("json") String json) {
        // TODO
    }
}
