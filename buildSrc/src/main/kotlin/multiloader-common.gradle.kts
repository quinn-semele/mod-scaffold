import dev.compasses.multiloader.Constants
import gradle.kotlin.dsl.accessors._a87ff6e8de49377acc07e836f6f5893d.modImplementation

plugins {
    id("multiloader-shared")
    id("net.neoforged.moddev")
}

neoForge {
    neoFormVersion = Constants.NEOFORM_VERSION

    parchment {
        minecraftVersion = Constants.PARCHMENT_MINECRAFT
        mappingsVersion = Constants.PARCHMENT_RELEASE
    }
}

dependencies {
    compileOnly(group = "org.spongepowered", name = "mixin", version = Constants.MIXIN_VERSION)
    annotationProcessor(compileOnly(group = "io.github.llamalad7", name = "mixinextras-common", version = Constants.MIXIN_EXTRAS_VERSION))

    compileOnly(group = "net.fabricmc", name = "fabric-language-kotlin", version = Constants.FABRIC_KOTLIN_VERSION) {
        exclude(group = "net.fabricmc", module = "fabric-loader")
    }
}

configurations {
    create("commonJava") { isCanBeResolved = false; isCanBeConsumed = true }
    create("commonKotlin") { isCanBeResolved = false; isCanBeConsumed = true }
    create("commonResources") { isCanBeResolved = false; isCanBeConsumed = true }
}

afterEvaluate {
    with(sourceSets.main.get()) {
        artifacts {
            java.sourceDirectories.forEach { add("commonJava", it) }
            kotlin.sourceDirectories.forEach { add("commonKotlin", it) }
            resources.sourceDirectories.forEach { add("commonResources", it) }
        }
    }
}
