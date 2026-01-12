plugins {
    id("java-library")
    id("org.allaymc.gradle.plugin") version "0.2.1"
}

group = "me.daoge.mentionsound"
description = "Get sound notification when someone mentions you | AllayMC plugin"
version = "0.1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allay {
    api = "0.21.0"

    plugin {
        entrance = ".MentionSound"
        authors += "daoge_cmd"
        website = "https://github.com/smartcmd/MentionSound"
    }
}

dependencies {
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.34")
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.34")
    implementation("org.yaml:snakeyaml:2.2")
}
