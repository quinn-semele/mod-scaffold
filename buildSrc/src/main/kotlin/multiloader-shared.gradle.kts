import dev.compasses.multiloader.Constants

plugins {
    `java-library`
}

group = Constants.GROUP
version = Constants.MOD_VERSION

base.archivesName = "${Constants.MOD_ID}-${project.name}-${Constants.MINECRAFT_VERSION}"

java {
    toolchain.languageVersion = Constants.JAVA_VERSION
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
            "Specification-Title" to Constants.MOD_NAME,
            "Specification-Vendor" to Constants.AUTHORS.firstEntry().key,
            "Specification-Version" to archiveVersion,
            "Implementation-Title" to project.name,
            "Implementation-Version" to archiveVersion,
            "Implementation-Vendor" to Constants.AUTHORS.firstEntry().key,
            "Built-On-Minecraft" to Constants.MINECRAFT_VERSION
        ))
    }
}

tasks.processResources {
    val replacements = mutableMapOf(
        "version" to version,
        "group" to Constants.GROUP,
        "mod_name" to Constants.MOD_NAME,
        "mod_id" to Constants.MOD_ID,
        "license" to Constants.LICENSE,
        "description" to Constants.DESCRIPTION,

        "nf_authors" to Constants.AUTHORS.keys.joinToString(","),
        "fl_authors" to Constants.AUTHORS.keys.joinToString("\", \""),

        "credits" to Constants.CREDITS.map { "${it.key} - ${it.value}" }.joinToString(",\n"),

        "java_version" to Constants.JAVA_VERSION.asInt(),
        "minecraft_version" to Constants.MINECRAFT_VERSION,
        "fl_minecraft_constraint" to Constants.FL_MINECRAFT_CONSTRAINT,
        "nf_minecraft_constraint" to Constants.NF_MINECRAFT_CONSTRAINT,

        "fabric_loader_version" to Constants.FABRIC_LOADER_VERSION,
        "fabric_api_version" to Constants.FABRIC_API_VERSION,

        "neoforge_version" to Constants.NEOFORGE_VERSION,
        "fml_version_constraint" to Constants.FML_CONSTRAINT,
    )

    inputs.properties(replacements)
    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
        expand(replacements)
    }
}