package me.bogatyr.recipes.dto;

import java.time.LocalDate;

public class InfoDTO {
    private final String author;
    private final String name;
    private final LocalDate creationDate;
    private final String description;

    public InfoDTO(String author, String name, LocalDate creationDate, String description) {
        this.author = author;
        this.name = name;
        this.creationDate = creationDate;
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }
}
