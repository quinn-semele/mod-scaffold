package dev.compasses.multiloader

import org.gradle.api.JavaVersion

object Constants {
    const val group = "com.example.examplemod"
    const val modId = "examplemod"
    const val modName = "Example Mod"
    const val modVersion = "1.0.0"
    const val license = "CC0-1.0"
    const val description = ""

    val authors = linkedMapOf(
        "Quinn Semele" to "Project Owner"
    )

    val credits = linkedMapOf<String, String>(

    )

    val javaVersion = JavaVersion.VERSION_21
    const val minecraftVersion = "1.21"
    const val flMinecraftConstraint = ">=1.21- <1.22"
    const val nfMinecraftConstraint = "[1.21, 1.22)"

    // https://fabricmc.net/develop/
    const val fabricApiVersion = "0.100.1+1.21"
    const val fabricLoaderVersion = "0.15.11"

    // https://projects.neoforged.net/neoforged/neoform/
    const val neoFormVersion = "1.21-20240613.152323"
    // https://projects.neoforged.net/neoforged/neoforge/
    const val neoForgeVersion = "21.0.139-beta"
    // https://projects.neoforged.net/neoforged/fancymodloader/
    const val fmlVersionConstraint = "[4,)"

    // https://parchmentmc.org/docs/getting-started#choose-a-version
    const val parchmentMinecraft = "1.21"
    const val parchmentRelease = "2024.07.07"
}