package com.gfk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpecificationController {

    @RequestMapping("greeting")
    public String greeting(final Model model) {
        return "greetings";
    }

}