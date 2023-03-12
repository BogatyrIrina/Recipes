package me.bogatyr.recipes.service;

import me.bogatyr.recipes.dto.IngredientDTO;
import me.bogatyr.recipes.dto.RecipeDTO;
import me.bogatyr.recipes.model.Ingredient;
import me.bogatyr.recipes.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientService {
    private int idCounter = 0;
    private final Map<Integer, Ingredient> ingredients = new HashMap<>();
    public IngredientDTO addIngredient(Ingredient ingredient){
        int id = idCounter++;
        ingredients.put(id, ingredient);
        return IngredientDTO.from(id, ingredient);
    }
    public IngredientDTO getIngredient(int id){
        Ingredient ingredient = ingredients.get(id);
        if (ingredient != null){
            return IngredientDTO.from(id, ingredient);
        }
        return null;
    }

    public IngredientDTO updateIngredient(int id, Ingredient ingredient) {
        Ingredient existingIngredient = ingredients.get(id);
        if (existingIngredient == null){
            throw new IngredientNotFoundException();
        }
        ingredients.put(id, ingredient);
        return IngredientDTO.from(id, ingredient);
    }

    public IngredientDTO deleteById(int id) {
        Ingredient existingIngredient = ingredients.remove(id);
        if (existingIngredient == null){
            throw new IngredientNotFoundException();
        }
        return IngredientDTO.from(id, existingIngredient);
    }
}
