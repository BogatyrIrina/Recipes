package me.bogatyr.recipes.service;

import me.bogatyr.recipes.dto.RecipeDTO;
import me.bogatyr.recipes.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeService {
    private int idCounter = 0;
    private final Map<Integer, Recipe> recipes = new HashMap<>();
    public RecipeDTO addRecipe(Recipe recipe){
        int id = idCounter++;
        recipes.put(id, recipe);
        return RecipeDTO.from(id,recipe);
    }
    public RecipeDTO getRecipe(int id){
        Recipe recipe = recipes.get(id);
        if (recipe != null){
            return RecipeDTO.from(id, recipe);
        }
        return null;
    }

}
