package me.daoge.mentionsound.listener;

import me.daoge.mentionsound.MentionSound;
import me.daoge.mentionsound.data.PlayerSettings;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.player.PlayerChatEvent;
import org.allaymc.api.player.Player;
import org.allaymc.api.server.Server;
import org.allaymc.api.world.sound.CustomSound;

public class ChatListener {

    @EventHandler
    private void onPlayerChat(PlayerChatEvent event) {
        String message = event.getMessage();

        // Quick check: if no @ symbol, skip processing
        if (!message.contains("@")) {
            return;
        }

        var settingsManager = MentionSound.getInstance().getSettingsManager();
        var playerManager = Server.getInstance().getPlayerManager();

        // Check for @here mention
        if (message.contains("@here")) {
            playerManager.forEachPlayer(player -> {
                PlayerSettings settings = settingsManager.getSettings(player.getOriginName());
                if (settings.isHereMentionEnabled()) {
                    playSound(player, settings.getHereMentionSound(), settings.getPitch());
                }
            });
        }

        // Check for @playerName mentions
        playerManager.forEachPlayer(player -> {
            String playerName = player.getOriginName();
            // Check if message contains @playerName (case-insensitive)
            if (containsMention(message, playerName)) {
                PlayerSettings settings = settingsManager.getSettings(playerName);
                if (settings.isNameMentionEnabled()) {
                    playSound(player, settings.getNameMentionSound(), settings.getPitch());
                }
            }
        });
    }

    private boolean containsMention(String message, String playerName) {
        String lowerMessage = message.toLowerCase();
        String lowerPlayerName = playerName.toLowerCase();

        // Check for quoted form: @"player name"
        String quotedMention = "@\"" + lowerPlayerName + "\"";
        if (lowerMessage.contains(quotedMention)) {
            return true;
        }

        // Check for unquoted form: @player name or @playername
        String mention = "@" + lowerPlayerName;
        int index = lowerMessage.indexOf(mention);
        if (index == -1) {
            return false;
        }

        // For names without spaces, check word boundary
        if (!playerName.contains(" ")) {
            int endIndex = index + mention.length();
            if (endIndex < message.length()) {
                char nextChar = message.charAt(endIndex);
                if (Character.isLetterOrDigit(nextChar) || nextChar == '_') {
                    return false;
                }
            }
            return true;
        }

        // For names with spaces, the match is already complete if found
        return true;
    }

    private void playSound(Player player, String soundName, float pitch) {
        var entity = player.getControlledEntity();
        if (entity == null) {
            return;
        }
        var location = entity.getLocation();
        var sound = new CustomSound(soundName, 1.0f, pitch);
        // relative=true means sound volume is based on distance; false means full volume
        player.viewSound(sound, location, false);
    }
}
