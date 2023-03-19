package me.bogatyr.recipes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceIngredient {
    @Value("${path.to.ingredient.file}")
    private String ingredientFilePath;

    @Value("${name.of.ingredient.file}")
    private String ingredientFileName;

    public boolean saveToFileIngredient(String json){
        try {
            cleanDataFileIngredient();
            Files.writeString(Path.of(ingredientFilePath, ingredientFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public String readFromFileIngredient(){
        try {
            return Files.readString(Path.of(ingredientFilePath, ingredientFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private boolean cleanDataFileIngredient(){
        try {
            Path path = Path.of(ingredientFilePath, ingredientFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
