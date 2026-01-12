package me.daoge.mentionsound.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSettings {
    private boolean nameMentionEnabled = true;
    private String nameMentionSound = "note.pling";
    private boolean hereMentionEnabled = true;
    private String hereMentionSound = "note.pling";
    private float pitch = 1.0f;
}
