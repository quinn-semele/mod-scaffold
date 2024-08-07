import dev.compasses.multiloader.Constants
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.loom
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.mappings
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.minecraft
import gradle.kotlin.dsl.accessors._e054d9723d982fdb55b1e388b8ab0cbf.compileOnly
import gradle.kotlin.dsl.accessors._e054d9723d982fdb55b1e388b8ab0cbf.processResources
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.project

plugins {
    id("multiloader-loader")
    id("fabric-loom")
}

configurations {
    create("threadJava") { isCanBeResolved = true }
    create("threadResources") { isCanBeResolved = true }
}

dependencies {
    minecraft("com.mojang:minecraft:${Constants.MINECRAFT_VERSION}")

    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${Constants.PARCHMENT_MINECRAFT}:${Constants.PARCHMENT_RELEASE}@zip")
    })

    compileOnly(project(":thread"))

    "threadJava"(project(path=":thread", configuration="threadJava"))
    "threadResources"(project(path=":thread", configuration="threadResources"))
}

tasks {
    "compileJava"(JavaCompile::class) {
        dependsOn(configurations.getByName("threadJava"))
        source(configurations.getByName("threadJava"))
    }

    processResources {
        dependsOn(configurations.getByName("threadResources"))
        from(configurations.getByName("threadResources"))
    }
}

loom {
    val accessWidener = project(":common").file("src/main/resources/${Constants.MOD_ID}.accesswidener")
    if (accessWidener.exists()) {
        accessWidenerPath = accessWidener
    }

    @Suppress("UnstableApiUsage")
    mixin.defaultRefmapName = "${Constants.MOD_ID}.refmap.json"
}