package me.bogatyr.recipes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String first() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String info() {
        return "имя ученика";
    }
}

