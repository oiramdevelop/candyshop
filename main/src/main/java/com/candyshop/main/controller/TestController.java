package com.candyshop.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        System.out.println("✅ TEST CONTROLLER EJECUTADO");
        return "test";
    }
}