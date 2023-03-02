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
        return "имя ученика: Иван,\n" +
                "название проекта: Рецепт смузи,\n" +
                "дата создания проекта: 01.03.2023,\n" +
                "описание проекта: порезать ингредиенты кубиками. " +
                "Сначала перемалывают твердые ингредиенты — орехи, овсянку. " +
                "Потом — сочные фрукты и ягоды. Затем добавляют вязкие ингредиенты — например, банан. " +
                "Потом жидкости, лед.";
    }
}

