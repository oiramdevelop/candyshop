package com.candyshop.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


import ch.qos.logback.core.model.Model;



@Controller
@RequestMapping("")
public class mainController {
    private Logger logger = LoggerFactory.getLogger(mainController.class);

    @GetMapping
    public String mainString ( Model model) {
        return ("main.html");
    }
    
}
