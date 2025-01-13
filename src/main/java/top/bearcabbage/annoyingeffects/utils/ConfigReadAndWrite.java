package top.bearcabbage.annoyingeffects.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static com.mojang.text2speech.Narrator.LOGGER;

public class ConfigReadAndWrite {
    private final Path filePath;
    private JsonObject jsonObject;
    private final Gson gson;

    public ConfigReadAndWrite(Path filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if (Files.notExists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
                try (FileWriter writer = new FileWriter(filePath.toFile())) {
                    writer.write("{}");
                }
            }

        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        load();
    }

    public void load() {
        try (FileReader reader = new FileReader(filePath.toFile())) {
            this.jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            this.jsonObject = new JsonObject();
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(jsonObject, writer);
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    public <T> void set(String key, T value) {
        jsonObject.add(key, gson.toJsonTree(value));
    }

    public <T> T get(String key, Class<T> clazz) {
        return gson.fromJson(jsonObject.get(key), clazz);
    }

    public <T> T get(String key, Class<T> clazz, T defaultValue) {
        T value = gson.fromJson(jsonObject.get(key), clazz);
        if (value == null) {
            set(key, defaultValue);
            save();
        }
        return value != null ? value : defaultValue;
    }

    public Map<String, Integer> getStatusParameters(String key, Map<String, Integer> defaultValue) {
        JsonObject value = jsonObject.getAsJsonObject(key);
        if (value == null) {
            set(key, defaultValue);
            save();
            return defaultValue;
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("duration", value.get("duration").getAsInt());
        result.put("interval", value.get("interval").getAsInt());
        result.put("weak_duration", value.get("weak_duration").getAsInt());
        return result;
    }
    
}
