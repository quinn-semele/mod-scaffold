import dev.compasses.multiloader.Constants

plugins {
    id("multiloader-common")
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
    compileOnly(group = "org.spongepowered", name = "mixin", version = "0.8.5")
    annotationProcessor(compileOnly(group = "io.github.llamalad7", name = "mixinextras-common", version = "0.3.5"))
}

configurations {
    listOf("commonJava", "commonResources").forEach {
        create(it) {
            isCanBeResolved = false
            isCanBeConsumed = true
        }
    }
}

artifacts {
    add("commonJava", sourceSets.main.map { it.java.sourceDirectories.singleFile })
    add("commonResources", sourceSets.main.map { it.resources.sourceDirectories.singleFile })
}
