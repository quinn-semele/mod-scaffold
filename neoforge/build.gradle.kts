import dev.compasses.multiloader.Constants

plugins {
    id("multiloader-neoforge")
}

multiloader {
    mods {
        if (plugins.hasPlugin("org.jetbrains.kotlin.jvm")) {
            create("kotlin-for-forge") {
                required()

                artifacts {
                    implementation(group = "thedarkcolour", name = "kotlinforforge-neoforge", version = Constants.NEOFORGE_KOTLIN_VERSION)
                }
            }
        }
    }
}
