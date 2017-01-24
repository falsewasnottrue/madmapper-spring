package com.gfk.controller;

import com.gfk.services.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpecificationController {

    @Autowired
    private SchemaService schemaService;

    @RequestMapping("greeting")
    public String greeting(final Model model) {
        // TODO load specification from file
        // TODO add to model
        model.addAttribute("schema", schemaService.getSchema());

        return "specification";
    }

}
