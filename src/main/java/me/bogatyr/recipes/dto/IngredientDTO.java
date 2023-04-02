package me.bogatyr.recipes.dto;

import me.bogatyr.recipes.model.Ingredient;

public class IngredientDTO {
    private final int id;
    private final String title;
    private final int number;
    private final String measure;

    public IngredientDTO(int id, String title, int number, String measure) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.measure = measure;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getNumber() {
        return number;
    }

    public String getMeasure() {
        return measure;
    }
    public static IngredientDTO from(int id, Ingredient ingredient){
        return new IngredientDTO(id, ingredient.getTitle(), ingredient.getNumber(), ingredient.getMeasure());
    }
}
