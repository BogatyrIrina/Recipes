package me.bogatyr.recipes.controllers;

import me.bogatyr.recipes.dto.IngredientDTO;
import me.bogatyr.recipes.model.Ingredient;
import me.bogatyr.recipes.service.IngredientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    public final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{id}")
    public IngredientDTO getIngredient(@PathVariable("id") int id){
        return ingredientService.getIngredient(id);
    }
    @PostMapping
    public IngredientDTO addIngredient(@RequestBody Ingredient ingredient){
        return ingredientService.addIngredient(ingredient);
    }
}
