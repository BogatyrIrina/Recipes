package me.bogatyr.recipes.controllers;

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

@Controller
public class ImportExportController {
    private final RecipeService recipeService;

    public ImportExportController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/files/export/recipes")
    public ResponseEntity<Resource> downloadRecipes(){
        Resource recipes = recipeService.getRecipesFile();
        ContentDisposition disposition = ContentDisposition.attachment().
                name("recipes.json").
                build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(disposition);
        return ResponseEntity.ok().headers(headers).body(recipes);
    }
    @PostMapping(value = "/files/import/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importRecipes(@RequestParam MultipartFile file){
        this.recipeService.importRecipes(file.getResource());
        return ResponseEntity.noContent().build();
    }


}
