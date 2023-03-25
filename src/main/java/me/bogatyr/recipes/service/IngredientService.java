package me.bogatyr.recipes.service;

import com.fasterxml.jackson.core.type.TypeReference;
import me.bogatyr.recipes.dto.IngredientDTO;
import me.bogatyr.recipes.dto.RecipeDTO;
import me.bogatyr.recipes.model.Ingredient;
import me.bogatyr.recipes.model.Recipe;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IngredientService {
    final private static String STORE_FILE_NAME = "ingredients";
    private static int idCounter = 0;
    final private FilesService filesService;
    private Map<Integer, Ingredient> ingredients;

    public IngredientService(FilesService filesService) {
        this.filesService = filesService;
        Map<Integer, Ingredient> storedMap = filesService.readFromFile(STORE_FILE_NAME,
                new TypeReference<>() {
                });
        this.ingredients = Objects.requireNonNullElseGet(storedMap, HashMap::new);
    }


    public IngredientDTO addIngredient(Ingredient ingredient){
        int id = idCounter++;
        ingredients.put(id, ingredient);
        this.filesService.saveToFile(STORE_FILE_NAME, ingredients);
        return IngredientDTO.from(id, ingredient);
    }
    public IngredientDTO getIngredient(int id){
        Ingredient ingredient = ingredients.get(id);
        if (ingredient != null){
            return IngredientDTO.from(id, ingredient);
        }
        return null;
    }

    public List<IngredientDTO> getAllIngredients() {
        List<IngredientDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, Ingredient> entry : ingredients.entrySet()){
            result.add(IngredientDTO.from(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public IngredientDTO updateIngredient(int id, Ingredient ingredient) {
        Ingredient existingIngredient = ingredients.get(id);
        if (existingIngredient == null){
            throw new IngredientNotFoundException();
        }
        ingredients.put(id, ingredient);
        this.filesService.saveToFile(STORE_FILE_NAME, ingredients);
        return IngredientDTO.from(id, ingredient);
    }

    public IngredientDTO deleteById(int id) {
        Ingredient existingIngredient = ingredients.remove(id);
        if (existingIngredient == null){
            throw new IngredientNotFoundException();
        }
        this.filesService.saveToFile(STORE_FILE_NAME, ingredients);
        return IngredientDTO.from(id, existingIngredient);
    }

    public void importIngredients(Resource resource) {
        filesService.saveResource(STORE_FILE_NAME, resource);
        this.ingredients = filesService.readFromFile(STORE_FILE_NAME,
                new TypeReference<>() {
                });
    }
}
