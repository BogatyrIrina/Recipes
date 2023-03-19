package me.bogatyr.recipes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.bogatyr.recipes.dto.RecipeDTO;
import me.bogatyr.recipes.model.Recipe;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {
    final private FilesService filesService;
    private int idCounter = 0;
    private final Map<Integer, Recipe> recipes = new HashMap<>();

    public RecipeService(FilesService filesService) {
        this.filesService = filesService;
    }

    public RecipeDTO addRecipe(Recipe recipe){
        int id = idCounter++;
        recipes.put(id, recipe);
        saveToFile();
        return RecipeDTO.from(id,recipe);
    }
    public RecipeDTO getRecipe(int id){
        Recipe recipe = recipes.get(id);
        if (recipe != null){
            return RecipeDTO.from(id, recipe);
        }
        return null;
    }


    public List<RecipeDTO> getAllRecipes() {
        List<RecipeDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, Recipe> entry : recipes.entrySet()){
            result.add(RecipeDTO.from(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public RecipeDTO updateRecipe(int id, Recipe recipe) {
        Recipe existingRecipe = recipes.get(id);
        if (existingRecipe == null){
            throw new RecipeNotFoundException();
        }
        recipes.put(id, recipe);
        saveToFile();
        return RecipeDTO.from(id, recipe);
    }

    public RecipeDTO deleteById(int id) {
        Recipe existingRecipe = recipes.remove(id);
        if (existingRecipe == null){
            throw new RecipeNotFoundException();
        }
        saveToFile();
        return RecipeDTO.from(id, existingRecipe);
    }
    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile(){
        String json = filesService.readFromFile();
        try {
            new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
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
