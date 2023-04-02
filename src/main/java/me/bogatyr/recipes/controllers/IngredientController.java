package me.bogatyr.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.bogatyr.recipes.dto.IngredientDTO;
import me.bogatyr.recipes.model.Ingredient;
import me.bogatyr.recipes.service.IngredientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "CRUD-операции и другие эндпоинты по работе с ингредиентами")
public class IngredientController {
    private final IngredientService ingredientService;

    private IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Получение ингредиентов по id",
            description = "Получить ингредиент можно по id")
    public IngredientDTO getIngredient(@PathVariable("id") int id) {
        return ingredientService.getIngredient(id);
    }

    @PostMapping
    @Operation(summary = "Добавление ингредиентов по id",
            description = "Добавить ингредиент можно по id")
    public IngredientDTO addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование ингредиентов по id",
            description = "Отредактировать ингредиент можно по id")
    public IngredientDTO editIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(id, ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиентов по id",
    description = "Удалить ингредиент можно по id")
    public IngredientDTO deleteIngredient(@PathVariable("id") int id) {
        return ingredientService.deleteById(id);
    }
}
