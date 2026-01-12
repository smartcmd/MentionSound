package me.daoge.mentionsound;

import lombok.Getter;
import me.daoge.mentionsound.command.MentionSoundCommand;
import me.daoge.mentionsound.data.SettingsManager;
import me.daoge.mentionsound.listener.ChatListener;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.registry.Registries;
import org.allaymc.api.server.Server;

public class MentionSound extends Plugin {

    @Getter
    private static MentionSound instance;

    @Getter
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize settings manager with plugin data folder
        settingsManager = new SettingsManager(pluginContainer.dataFolder());

        // Register event listener
        Server.getInstance().getEventBus().registerListener(new ChatListener());

        // Register command
        Registries.COMMANDS.register(new MentionSoundCommand());

        getPluginLogger().info("MentionSound enabled!");
    }

    @Override
    public void onDisable() {
        if (settingsManager != null) {
            settingsManager.saveAll();
        }
        getPluginLogger().info("MentionSound disabled!");
    }
}
