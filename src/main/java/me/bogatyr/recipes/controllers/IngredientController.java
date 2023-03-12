package me.bogatyr.recipes.controllers;

import me.bogatyr.recipes.dto.IngredientDTO;
import me.bogatyr.recipes.model.Ingredient;
import me.bogatyr.recipes.service.IngredientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    private IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{id}")
    public IngredientDTO getIngredient(@PathVariable("id") int id) {
        return ingredientService.getIngredient(id);
    }

    @PostMapping
    public IngredientDTO addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    @PutMapping("/{id}")
    public IngredientDTO editIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(id, ingredient);
    }

    @DeleteMapping("/{id}")
    public IngredientDTO deleteIngredient(@PathVariable("id") int id) {
        return ingredientService.deleteById(id);
    }
}
