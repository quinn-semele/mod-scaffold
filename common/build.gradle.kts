import dev.compasses.multiloader.Constants

plugins {
    id("multiloader-common")
    id("net.neoforged.moddev")
}

neoForge {
    neoFormVersion = Constants.NEOFORM_VERSION

    // Automatically enable AccessTransformers if the file exists
    val at = file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.add(at.absolutePath)
    }

    parchment {
        minecraftVersion = Constants.PARCHMENT_MINECRAFT
        mappingsVersion = Constants.PARCHMENT_RELEASE
    }
}

dependencies {
    compileOnly(group = "org.spongepowered", name = "mixin", version = "0.8.5")
    annotationProcessor(compileOnly(group = "io.github.llamalad7", name = "mixinextras-common", version = "0.3.5"))
}

sourceSets {
    create("fabric") {
//        java.setSrcDirs(listOf(sourceSets.main.get().java))
    }
    create("neoforge") {
//        java.setSrcDirs(listOf(sourceSets.main.get().java))
//        java.source(sourceSets.main.get().java)
    }
}

configurations {
    listOf("fabricJava", "neoforgeJava").forEach {
        create(it) {
            isCanBeResolved = false
            isCanBeConsumed = true
            extendsFrom(configurations.mainSourceElements.get())
        }
    }

    create("commonResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

tasks.withType<JavaCompile> {
    if (name == "compileNeoforgeJava") {
        options.compilerArgs.add("-ALOADER=NEOFORGE")
    } else if (name == "compileFabricJava") {
        options.compilerArgs.add("-ALOADER=FABRIC")
    } else {
        options.compilerArgs.add("-ALOADER=COMMON")
    }

    destinationDirectory = destinationDirectory.get().dir(name)
}

artifacts {
    add("fabricJava", sourceSets.named("fabric").map { it.java.sourceDirectories.singleFile })
    add("neoforgeJava", sourceSets.named("neoforge").map { it.java.sourceDirectories.singleFile })
    add("commonResources", sourceSets.main.map { it.resources.sourceDirectories.singleFile })
}
