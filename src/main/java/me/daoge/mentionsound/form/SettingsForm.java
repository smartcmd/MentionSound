package me.daoge.mentionsound.form;

import me.daoge.mentionsound.MentionSound;
import org.allaymc.api.form.Forms;
import org.allaymc.api.message.I18n;
import org.allaymc.api.player.Player;
import org.allaymc.api.utils.TextFormat;

public class SettingsForm {

    public static void show(Player player) {
        var settingsManager = MentionSound.getInstance().getSettingsManager();
        var settings = settingsManager.getSettings(player.getOriginName());
        var i18n = I18n.get();

        Forms.custom()
                .title(TextFormat.GOLD + i18n.tr("mentionsound:form.settings.title"))

                // Index 0: Toggle for @name mentions
                .toggle(i18n.tr("mentionsound:form.settings.name_mention_toggle", player.getOriginName()),
                        settings.isNameMentionEnabled())

                // Index 1: Sound name input for name mentions
                .input(i18n.tr("mentionsound:form.settings.name_mention_sound"),
                        i18n.tr("mentionsound:form.settings.name_mention_sound_placeholder"),
                        settings.getNameMentionSound())

                // Index 2: Toggle for @here mentions
                .toggle(i18n.tr("mentionsound:form.settings.here_mention_toggle"),
                        settings.isHereMentionEnabled())

                // Index 3: Sound name input for @here mentions
                .input(i18n.tr("mentionsound:form.settings.here_mention_sound"),
                        i18n.tr("mentionsound:form.settings.here_mention_sound_placeholder"),
                        settings.getHereMentionSound())

                // Index 4: Pitch slider (0.5 to 2.0)
                .slider(i18n.tr("mentionsound:form.settings.pitch"), 0.5f, 2.0f, 1, settings.getPitch())

                .onResponse(responses -> {
                    // Parse responses in order
                    boolean nameMentionEnabled = Boolean.parseBoolean(responses.get(0));
                    String nameMentionSound = responses.get(1);
                    boolean hereMentionEnabled = Boolean.parseBoolean(responses.get(2));
                    String hereMentionSound = responses.get(3);
                    float pitch = Float.parseFloat(responses.get(4));

                    // Validate inputs
                    if (nameMentionSound == null || nameMentionSound.isBlank()) {
                        nameMentionSound = "note.pling";
                    }
                    if (hereMentionSound == null || hereMentionSound.isBlank()) {
                        hereMentionSound = "note.pling";
                    }

                    // Update settings
                    settings.setNameMentionEnabled(nameMentionEnabled);
                    settings.setNameMentionSound(nameMentionSound);
                    settings.setHereMentionEnabled(hereMentionEnabled);
                    settings.setHereMentionSound(hereMentionSound);
                    settings.setPitch(pitch);

                    // Save settings
                    settingsManager.saveSettings(player.getOriginName(), settings);

                    player.sendTip(TextFormat.GREEN + i18n.tr("mentionsound:form.settings.saved"));
                })
                .onClose(reason -> {
                    player.sendTip(TextFormat.YELLOW + i18n.tr("mentionsound:form.settings.closed"));
                })
                .sendTo(player);
    }
}
