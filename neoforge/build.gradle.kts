import dev.compasses.multiloader.Constants

plugins {
    id("multiloader-loader")
    id("net.neoforged.moddev")
}

neoForge {
    version = Constants.NEOFORGE_VERSION

    // Automatically enable neoforge AccessTransformers if the file exists
    val at = project(":common").file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.add(at.absolutePath)
    }

    parchment {
        minecraftVersion = Constants.PARCHMENT_MINECRAFT
        mappingsVersion = Constants.PARCHMENT_RELEASE
    }

    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", Constants.MOD_ID)
            ideName = "NeoForge ${name.capitalize()} (${project.path})"
        }

        create("client") { client() }
        create("data") { data() }
        create("server") { server() }
    }

    mods {
        create(Constants.MOD_ID) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.main {
    resources.srcDirs("src/generated/resources")
}