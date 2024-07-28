import dev.compasses.multiloader.Constants

plugins {
    `java-library`
}

base.archivesName = "${Constants.modId}-${project.name}-${Constants.minecraftVersion}"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(Constants.javaVersion.ordinal + 1)
}

repositories {
    mavenCentral()

    exclusiveContent {
        forRepository {
            maven {
                name = "Sponge"
                url = uri("https://repo.spongepowered.org/repository/maven-public/")
            }
        }
        filter { includeGroupAndSubgroups("org.spongepowered") }
    }

    exclusiveContent {
        forRepositories(
            maven {
                name = "ParchmentMC"
                url = uri("https://maven.parchmentmc.org/")
            },
            maven {
                name = "NeoForge"
                url = uri("https://maven.neoforged.net/releases/")
            }
        )
        filter { includeGroup("org.parchmentmc.data") }
    }
}

tasks.jar {
    manifest {
        attributes(mapOf(
            "Specification-Title" to Constants.modName,
            "Specification-Vendor" to Constants.authors.firstEntry().key,
            "Specification-Version" to archiveVersion,
            "Implementation-Title" to project.name,
            "Implementation-Version" to archiveVersion,
            "Implementation-Vendor" to Constants.authors.firstEntry().key,
            "Built-On-Minecraft" to Constants.minecraftVersion
        ))
    }
}

tasks.processResources {
    val replacements = mutableMapOf(
        "version" to version,
        "group" to Constants.group,
        "mod_name" to Constants.modName,
        "mod_id" to Constants.modId,
        "license" to Constants.license,
        "description" to Constants.description,

        "nf_authors" to Constants.authors.keys.joinToString(","),
        "fl_authors" to Constants.authors.keys.joinToString(",\n\t\t") { "\"$it\"" },

        "credits" to Constants.credits.map { "${it.key} - ${it.value}" }.joinToString(",\n"),

        "java_version" to java.toolchain.languageVersion.get().asInt(),
        "minecraft_version" to Constants.minecraftVersion,
        "fl_minecraft_constraint" to Constants.flMinecraftConstraint,
        "nf_minecraft_constraint" to Constants.nfMinecraftConstraint,

        "fabric_loader_version" to Constants.fabricLoaderVersion,
        "fabric_api_version" to Constants.fabricApiVersion,

        "neoforge_version" to Constants.neoForgeVersion,
        "fml_version_constraint" to Constants.fmlVersionConstraint,
    )

    inputs.properties(replacements)
    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
        expand(replacements)
    }
}