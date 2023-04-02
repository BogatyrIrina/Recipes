package me.bogatyr.recipes.controllers;

import me.bogatyr.recipes.service.IngredientService;
import me.bogatyr.recipes.service.RecipeService;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
    @GetMapping("/files/export/recipesFromMemory")
    public void downloadRecipes(HttpServletResponse response) throws IOException {
        ContentDisposition disposition = ContentDisposition.attachment()
                .name("recipes.txt")
                .build();
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, disposition.toString());
        response.setContentType("text/plain");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        recipeService.exportFileFromMemory(response.getWriter());
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
