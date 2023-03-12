package me.bogatyr.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.bogatyr.recipes.dto.RecipeDTO;
import me.bogatyr.recipes.model.Recipe;
import me.bogatyr.recipes.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты по работе с рецептами")

public class RecipeController {
    private final RecipeService recipeService;

    private RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @Operation(summary = "Получение всех рецептов",
            description = "Можно получить список всех рецептов")
    public List<RecipeDTO> getRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение рецептов по id",
            description = "Получить рецепт можно по id")
    public RecipeDTO getRecipe(@PathVariable("id") int id) {
        return recipeService.getRecipe(id);
    }

    @PostMapping
    @Operation(summary = "Добавление рецептов",
            description = "Добавить рецепт можно по id")
    public RecipeDTO addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецептов по id",
            description = "Отредактировать рецепт можно по id")
    public RecipeDTO editRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe) {
        return recipeService.updateRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецептов по id",
            description = "Удалить рецепт можно по id")
    public RecipeDTO deleteRecipe(@PathVariable("id") int id) {
        return recipeService.deleteById(id);
    }
}
