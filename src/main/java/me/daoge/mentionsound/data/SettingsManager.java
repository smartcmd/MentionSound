package me.daoge.mentionsound.data;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SettingsManager {
    private final Path dataFolder;
    private final Map<String, PlayerSettings> cache = new ConcurrentHashMap<>();
    private final Yaml yaml;

    public SettingsManager(Path dataFolder) {
        this.dataFolder = dataFolder.resolve("settings");

        var options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        var representer = new Representer(options);
        representer.getPropertyUtils().setSkipMissingProperties(true);

        this.yaml = new Yaml(representer, options);

        try {
            Files.createDirectories(this.dataFolder);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create settings directory", e);
        }
    }

    public PlayerSettings getSettings(String playerName) {
        return cache.computeIfAbsent(playerName.toLowerCase(), this::loadSettings);
    }

    private PlayerSettings loadSettings(String playerName) {
        Path file = dataFolder.resolve(playerName + ".yml");
        if (Files.exists(file)) {
            try (var reader = Files.newBufferedReader(file)) {
                var settings = yaml.loadAs(reader, PlayerSettings.class);
                if (settings != null) {
                    return settings;
                }
            } catch (IOException e) {
                // Fall through to return default
            }
        }
        return new PlayerSettings();
    }

    public void saveSettings(String playerName, PlayerSettings settings) {
        cache.put(playerName.toLowerCase(), settings);
        Path file = dataFolder.resolve(playerName.toLowerCase() + ".yml");
        try (var writer = Files.newBufferedWriter(file)) {
            yaml.dump(settings, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save settings for " + playerName, e);
        }
    }

    public void saveAll() {
        cache.forEach(this::saveSettings);
    }
}
