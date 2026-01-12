# 🔔 MentionSound

> 📢 Get sound notifications when someone mentions you in chat!

An [AllayMC](https://github.com/AllayMC/Allay) plugin that plays a sound when a player is mentioned in chat via `@playerName` or `@here`.

## ✨ Features

- 🎵 **Name Mentions** - Play a sound when someone types `@yourName`
- 📣 **@here Mentions** - Notify all online players with `@here`
- 🔧 **Customizable Settings** - Each player can configure their own preferences
- 🎚️ **Adjustable Pitch** - Fine-tune the sound pitch (0.5 - 2.0)
- 💾 **Persistent Settings** - Settings are saved per-player in YAML files
- 🌐 **Multi-language Support** - English (en_US) and Chinese (zh_CN)
- 👥 **Names with Spaces** - Supports `@"player name"` and `@player name` formats

## 📦 Installation

1. Download the latest `MentionSound-x.x.x-shaded.jar` from releases
2. Place it in your Allay server's `plugins` folder
3. Restart the server

## 🎮 Commands

| Command                   | Description                   |
|---------------------------|-------------------------------|
| `/mentionsound`           | Show help information         |
| `/mentionsound settings`  | Open the settings form UI     |
| `/mentionsound soundlist` | Display available sound names |

**Alias:** `/mentsound`

## ⚙️ Settings

Players can customize the following settings via the in-game form:

| Setting                  | Description                       | Default      |
|--------------------------|-----------------------------------|--------------|
| 🔔 Enable @name mentions | Toggle name mention notifications | ✅ On         |
| 🎵 Name mention sound    | Sound to play for name mentions   | `note.pling` |
| 📣 Enable @here mentions | Toggle @here notifications        | ✅ On         |
| 🎵 @here mention sound   | Sound to play for @here           | `note.pling` |
| 🎚️ Sound pitch          | Pitch multiplier (0.5 - 2.0)      | 1.0          |

## 🎵 Common Sounds

```
note.pling          note.bell           note.chime
note.harp           note.xylophone      random.levelup
random.orb          random.pop          random.click
random.toast        mob.villager.yes    mob.villager.no
ui.toast.challenge_complete
```

You can use any valid Minecraft Bedrock Edition sound name!

## 🔨 Building from Source

### Prerequisites
- ☕ Java 21 or higher
- 🐘 Gradle

### Build

```bash
./gradlew shadowJar
```

The compiled JAR will be in `build/libs/MentionSound-x.x.x-shaded.jar`

### Test with Local Server

```bash
./gradlew runServer
```

## 📋 Requirements

- 🎮 AllayMC Server (API 0.21.0+)
- ☕ Java 21+

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Credits

- Original PocketMine-MP version by [DavyCraft648](https://github.com/DavyCraft648/MentionSound)
- AllayMC port by [daoge_cmd](https://github.com/smartcmd)
