package me.bogatyr.recipes.model;

public class Ingredient {
    private String title;
    private int number;
    private String measure;

    public Ingredient(String title, int number, String measure) {
        this.title = title;
        this.number = number;
        this.measure = measure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
