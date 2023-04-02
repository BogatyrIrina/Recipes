package me.bogatyr.recipes.controllers;

import me.bogatyr.recipes.dto.InfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping
public class FirstController {
    private static final InfoDTO INFO = new InfoDTO("Bogatyr Irina", "Recipes app",
            LocalDate.of(2023, 3, 5), "App for managing recipes");
    @GetMapping
    public String hello() {
        return "Application is started!";
    }

    @GetMapping("/info")
    public InfoDTO info() {
        return INFO;
    }
}

