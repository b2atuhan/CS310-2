package edu.sabanciuniv.week7day1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the Howudoin Application!";
    }
}
