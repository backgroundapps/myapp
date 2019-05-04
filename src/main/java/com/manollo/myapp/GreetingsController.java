package com.manollo.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * GreetingsController
 */
@Controller
public class GreetingsController {
    @GetMapping("/")
    public String index() {
        return "welcome";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}