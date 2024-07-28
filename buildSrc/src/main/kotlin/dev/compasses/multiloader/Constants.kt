package dev.compasses.multiloader

import org.gradle.jvm.toolchain.JavaLanguageVersion

object Constants {
    const val GROUP = "com.example.examplemod"
    const val MOD_ID = "examplemod"
    const val MOD_NAME = "Example Mod"
    const val MOD_VERSION = "1.0.0"
    const val LICENSE = "CC0-1.0"
    const val DESCRIPTION = ""

    val AUTHORS = linkedMapOf(
        "Quinn Semele" to "Project Owner"
    )

    val CREDITS = linkedMapOf<String, String>(

    )

    val JAVA_VERSION = JavaLanguageVersion.of(21)
    const val MANIFOLD_VERSION = "2024.1.29"

    const val MINECRAFT_VERSION = "1.21"
    const val FL_MINECRAFT_CONSTRAINT = ">=1.21- <1.22"
    const val NF_MINECRAFT_CONSTRAINT = "[1.21, 1.22)"

    // https://parchmentmc.org/docs/getting-started#choose-a-version/
    const val PARCHMENT_MINECRAFT = "1.21"
    const val PARCHMENT_RELEASE = "2024.07.07"

    // https://fabricmc.net/develop/
    const val FABRIC_API_VERSION = "0.100.1+1.21"
    const val FABRIC_LOADER_VERSION = "0.15.11"

    const val NEOFORM_VERSION = "1.21-20240613.152323" // // https://projects.neoforged.net/neoforged/neoform/
    const val NEOFORGE_VERSION = "21.0.139-beta" // https://projects.neoforged.net/neoforged/neoforge/
    const val FML_CONSTRAINT = "[4,)" // https://projects.neoforged.net/neoforged/fancymodloader/
}