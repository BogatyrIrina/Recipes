package me.bogatyr.recipes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.bogatyr.recipes.dto.IngredientDTO;
import me.bogatyr.recipes.dto.RecipeDTO;
import me.bogatyr.recipes.model.Ingredient;
import me.bogatyr.recipes.model.Recipe;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    final private static String STORE_FILE_NAME = "recipes";
    final private FilesService filesService;
    final private IngredientService ingredientService;
    private int idCounter = 0;
    private Map<Integer, Recipe> recipes;

    public RecipeService(FilesService filesService, IngredientService ingredientService) {
        this.filesService = filesService;
        this.ingredientService = ingredientService;
        Map<Integer,Recipe> storedMap = filesService.readFromFile(STORE_FILE_NAME,
                new TypeReference<>() {
                });
        if (storedMap != null){
            this.recipes = storedMap;
        }
        else {
            this.recipes = new HashMap<>();
        }
    }
    @PostConstruct
    public void setUp(){

    }

    public RecipeDTO addRecipe(Recipe recipe){
        int id = idCounter++;
        recipes.put(id, recipe);
        for (Ingredient ingredient:recipe.getIngredients()){
            this.ingredientService.addIngredient(ingredient);
        }
        this.filesService.saveToFile(STORE_FILE_NAME, this.recipes);
        return RecipeDTO.from(id,recipe);
    }

    public List<RecipeDTO> getRecipesByIngredientId(int ingredientId){
        IngredientDTO ingredient = this.ingredientService.getIngredient(ingredientId);
        return this.recipes.entrySet()
                .stream()
                .filter(e -> e.getValue().getIngredients().stream().anyMatch(i ->i.getTitle().equals(ingredient.getTitle())))
                .map(e->RecipeDTO.from(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public List<RecipeDTO> getRecipesByIngredientsIds(List<Integer> ingredientsIds){
        List<String> ingredientsNames = ingredientsIds.stream()
                .map(i -> this.ingredientService.getIngredient(i))
                .filter(Objects::nonNull)
                .map(i->i.getTitle())
                .collect(Collectors.toList());

        //Check null
        return this.recipes.entrySet()
                .stream()
                .filter(e -> {
                    Set<String> recipeIngredientsNames = e.getValue()
                            .getIngredients()
                            .stream()
                            .map(i->i.getTitle())
                            .collect(Collectors.toSet());
                    return recipeIngredientsNames.containsAll(ingredientsNames);
                })
                .map(e->RecipeDTO.from(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
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
        this.filesService.saveToFile(STORE_FILE_NAME, this.recipes);
        return RecipeDTO.from(id, recipe);
    }

    public RecipeDTO deleteById(int id) {
        Recipe existingRecipe = recipes.remove(id);
        if (existingRecipe == null){
            throw new RecipeNotFoundException();
        }
        this.filesService.saveToFile(STORE_FILE_NAME, this.recipes);
        return RecipeDTO.from(id, existingRecipe);
    }

    public Resource getRecipesFile() {
        return filesService.getResource(STORE_FILE_NAME);
    }

    public void importRecipes(Resource resource){
        filesService.saveResource(STORE_FILE_NAME, resource);
        this.recipes = filesService.readFromFile(STORE_FILE_NAME,
                new TypeReference<>() {
                });
    }

}
