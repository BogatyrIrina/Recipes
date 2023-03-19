package me.bogatyr.recipes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesService {
    private final Path filesDir;
    private final ObjectMapper objectMapper;

    public FilesService(ObjectMapper objectMapper, @Value("${path.to.data.file}") Path filesDir) {
        this.objectMapper = objectMapper;
        this.filesDir = filesDir;
    }

    public <T> void saveToFile (String fileName, T objectToSave) {
        try {
            String json = objectMapper.writeValueAsString(objectToSave);
            Files.createDirectories(filesDir);
            Path filePath = filesDir.resolve(fileName + ".json");
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
            Files.writeString(filePath, json);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public <T> T readFromFile(String fileName, TypeReference<T> typeReference) {
        Path filePath = filesDir.resolve(fileName+ ".json");
        if (!Files.exists(filePath)){
            return null;
        }
        try {
            String jsonString = Files.readString(filePath);
            T obj = objectMapper.readValue(jsonString, typeReference);
            return obj;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
