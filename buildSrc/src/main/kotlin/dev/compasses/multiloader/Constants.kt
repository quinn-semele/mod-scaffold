package dev.compasses.multiloader

import org.gradle.api.JavaVersion
import org.gradle.jvm.toolchain.JavaLanguageVersion

object Constants {
    const val GROUP = "com.example.examplemod"
    const val MOD_ID = "examplemod"
    const val MOD_NAME = "Example Mod"
    const val MOD_VERSION = "1.0.0"
    const val LICENSE = "CC0-1.0"
    const val DESCRIPTION = ""

    const val CURSEFORGE_PROJECT_ID = "000000"
    const val CURSEFORGE_PROJECT_SLUG = "examplemod"
    const val SERVERSIDE_REQUIRED = true
    const val CLIENTSIDE_REQUIRED = true
    val JAVA_VERSIONS_SUPPORTED = listOf(JavaVersion.VERSION_21, JavaVersion.VERSION_22)
    const val MODRINTH_PROJECT_ID = "00000000"
    const val compareUrl = "https://www.example.com/author/repo/compare/"

    val AUTHORS = linkedMapOf(
        "Quinn Semele" to "Project Owner",
        "Ellie Semele" to "Project Owner"
    )

    val CREDITS = linkedMapOf<String, String>(

    )

    val JAVA_VERSION = JavaLanguageVersion.of(21)

    const val MIXIN_VERSION = "0.8.5"
    const val MIXIN_EXTRAS_VERSION = "0.3.5"

    const val MINECRAFT_VERSION = "1.21"
    const val FL_MINECRAFT_CONSTRAINT = ">=1.21- <1.22"
    const val NF_MINECRAFT_CONSTRAINT = "[1.21, 1.22)"

    // https://parchmentmc.org/docs/getting-started#choose-a-version/
    const val PARCHMENT_MINECRAFT = "1.21"
    const val PARCHMENT_RELEASE = "2024.07.07"

    // https://fabricmc.net/develop/
    const val FABRIC_API_VERSION = "0.100.8+1.21"
    const val FABRIC_LOADER_VERSION = "0.15.11"

    const val NEOFORM_VERSION = "1.21-20240613.152323" // // https://projects.neoforged.net/neoforged/neoform/
    const val NEOFORGE_VERSION = "21.0.143" // https://projects.neoforged.net/neoforged/neoforge/
    const val FML_CONSTRAINT = "[4,)" // https://projects.neoforged.net/neoforged/fancymodloader/
}