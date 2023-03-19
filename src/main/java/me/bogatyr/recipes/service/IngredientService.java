package me.bogatyr.recipes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.bogatyr.recipes.dto.IngredientDTO;
import me.bogatyr.recipes.model.Ingredient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientService {
    final private FilesServiceIngredient filesServiceIngredient;
    private int idCounter = 0;
    private final Map<Integer, Ingredient> ingredients = new HashMap<>();

    public IngredientService(FilesServiceIngredient filesServiceIngredient) {
        this.filesServiceIngredient = filesServiceIngredient;
    }


    public IngredientDTO addIngredient(Ingredient ingredient){
        int id = idCounter++;
        ingredients.put(id, ingredient);
        saveToFile();
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
        saveToFile();
        return IngredientDTO.from(id, ingredient);
    }

    public IngredientDTO deleteById(int id) {
        Ingredient existingIngredient = ingredients.remove(id);
        if (existingIngredient == null){
            throw new IngredientNotFoundException();
        }
        saveToFile();
        return IngredientDTO.from(id, existingIngredient);
    }
    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesServiceIngredient.saveToFileIngredient(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile(){
        String json = filesServiceIngredient.readFromFileIngredient();
        try {
            new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @PostConstruct
    private void init(){
        readFromFile();
    }
}
