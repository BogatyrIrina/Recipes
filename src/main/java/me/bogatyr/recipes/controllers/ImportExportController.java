package me.bogatyr.recipes.controllers;

import me.bogatyr.recipes.service.IngredientService;
import me.bogatyr.recipes.service.RecipeService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ImportExportController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public ImportExportController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }


    @GetMapping("/files/export/recipes")
    public ResponseEntity<Resource> downloadRecipes() throws IOException {
        Resource recipes = recipeService.getRecipesFile();
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(recipes.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.json\"")
                .body(recipes);
    }
    @PostMapping(value = "/files/import/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importRecipes(@RequestParam MultipartFile file){
        this.recipeService.importRecipes(file.getResource());
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/files/import/ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importIngredients(@RequestParam MultipartFile file){
        this.ingredientService.importIngredients(file.getResource());
        return ResponseEntity.noContent().build();
    }
}
