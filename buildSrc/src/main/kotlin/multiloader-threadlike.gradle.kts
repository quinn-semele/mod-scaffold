import dev.compasses.multiloader.Constants
import dev.compasses.multiloader.task.ProcessJsonTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiloader-loader")
    id("org.quiltmc.loom")
}

evaluationDependsOn(":thread")

configurations {
    create("threadJava") { isCanBeResolved = true }
    create("threadKotlin") { isCanBeResolved = true }
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
    "threadKotlin"(project(path=":thread", configuration="threadKotlin"))
    "threadResources"(project(path=":thread", configuration="threadResources"))
}

tasks {
    "compileJava"(JavaCompile::class) {
        dependsOn(configurations.getByName("threadJava"))
        source(configurations.getByName("threadJava"))
    }

    "compileKotlin"(KotlinCompile::class) {
        dependsOn(configurations.getByName("threadKotlin"))
        source(configurations.getByName("threadKotlin"))
    }

    processResources {
        dependsOn(configurations.getByName("threadResources"))
        from(configurations.getByName("threadResources")) {
            exclude("fabric.mod.json")
        }
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
}

tasks.remapJar.configure {
    archiveClassifier = "fat"
}

tasks.processResources {
    exclude("META-INF/accesstransformer.cfg")
}

tasks.register("processJson", ProcessJsonTask::class) {
    group = "multiloader"
    dependsOn(tasks.remapJar)
    input.set(tasks.remapJar.get().outputs.files.singleFile)
    archiveClassifier = ""

    processors.put("quilt.mod.json") {
        val contributors = getAsJsonObject("quilt_loader").getAsJsonObject("metadata").getAsJsonObject("contributors")

        for ((contributor, role) in Constants.CONTRIBUTORS) {
            contributors.addProperty(contributor, role)
        }
    }
}

tasks.build {
    dependsOn("processJson")
}
