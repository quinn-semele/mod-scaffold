import dev.compasses.multiloader.Constants

plugins {
    id("multiloader-loader")
    id("org.quiltmc.loom")
}

evaluationDependsOn(":common")

dependencies {
    minecraft("com.mojang:minecraft:${Constants.MINECRAFT_VERSION}")

    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${Constants.PARCHMENT_MINECRAFT}:${Constants.PARCHMENT_RELEASE}@zip")
    })

    modCompileOnly(modRuntimeOnly("net.fabricmc:fabric-loader:${Constants.FABRIC_LOADER_VERSION}")!!)
    modCompileOnly(modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${Constants.FABRIC_API_VERSION}")!!)

    if (plugins.hasPlugin("org.jetbrains.kotlin.jvm")) {
        modImplementation(group = "net.fabricmc", name = "fabric-language-kotlin", version = Constants.FABRIC_KOTLIN_VERSION)
    }
}

fabricApi {
    configureDataGeneration {
        modId = Constants.MOD_ID
        outputDirectory = file("src/generated/resources")
    }
}

loom {
    val accessWidener = project(":common").file("src/main/resources/${Constants.MOD_ID}.accesswidener")
    if (accessWidener.exists()) {
        accessWidenerPath = accessWidener
    }

    @Suppress("UnstableApiUsage")
    mixin {
        defaultRefmapName = "${Constants.MOD_ID}.refmap.json"
        useLegacyMixinAp = false
    }

    runs {
        named("client") {
            client()

            configName = "Thread Client"
            isIdeConfigGenerated = true
        }

        named("server") {
            server()

            configName = "Thread Server"
            isIdeConfigGenerated = true
        }

        named("datagen") {
            configName = "Thread Data"
            isIdeConfigGenerated = true
        }
    }
}

configurations {
    create("threadJava") { isCanBeResolved = false; isCanBeConsumed = true }
    create("threadKotlin") { isCanBeResolved = false; isCanBeConsumed = true }
    create("threadResources") { isCanBeResolved = false; isCanBeConsumed = true }
}

afterEvaluate {
    with(sourceSets.main.get()) {
        artifacts {
            java.sourceDirectories.forEach { add("threadJava", it) }
            kotlin.sourceDirectories.forEach { add("threadKotlin", it) }
            resources.sourceDirectories.forEach { add("threadResources", it) }
        }
    }
}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${Constants.FABRIC_LOADER_VERSION}")
    }
}