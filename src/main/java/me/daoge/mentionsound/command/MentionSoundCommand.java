package me.daoge.mentionsound.command;

import me.daoge.mentionsound.form.SettingsForm;
import org.allaymc.api.command.Command;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.message.I18n;
import org.allaymc.api.permission.OpPermissionCalculator;

import java.util.List;

public class MentionSoundCommand extends Command {

    private static final List<String> COMMON_SOUNDS = List.of(
            "note.pling",
            "note.bell",
            "note.chime",
            "note.harp",
            "note.xylophone",
            "random.levelup",
            "random.orb",
            "random.pop",
            "random.click",
            "random.toast",
            "mob.villager.yes",
            "mob.villager.no",
            "ui.toast.challenge_complete"
    );

    public MentionSoundCommand() {
        // Use translation key for description
        super("mentionsound", "mentionsound:command.description", "mentionsound.command");
        aliases.add("mentsound");
        // Allow non-ops to use this command
        OpPermissionCalculator.NON_OP_PERMISSIONS.addAll(this.permissions);
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                // /mentionsound settings - Open settings form
                .key("settings")
                .exec((context, player) -> {
                    var controller = player.getController();
                    if (controller == null) {
                        context.addError("mentionsound:command.error.no_controller");
                        return context.fail();
                    }
                    SettingsForm.show(controller);
                    return context.success();
                }, SenderType.PLAYER)
                .root()
                // /mentionsound soundlist - Show available sounds
                .key("soundlist")
                .exec((context, player) -> {
                    var i18n = I18n.get();
                    context.addOutput(i18n.tr("mentionsound:command.soundlist.title"));
                    for (String sound : COMMON_SOUNDS) {
                        context.addOutput("  - " + sound);
                    }
                    context.addOutput(i18n.tr("mentionsound:command.soundlist.note"));
                    return context.success();
                }, SenderType.PLAYER)
                .root()
                // /mentionsound - Show help
                .exec(context -> {
                    var i18n = I18n.get();
                    context.addOutput(i18n.tr("mentionsound:command.help.title"));
                    context.addOutput(i18n.tr("mentionsound:command.help.usage"));
                    context.addOutput(i18n.tr("mentionsound:command.help.settings"));
                    context.addOutput(i18n.tr("mentionsound:command.help.soundlist"));
                    return context.success();
                });
    }
}
