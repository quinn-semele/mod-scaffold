import dev.compasses.multiloader.Constants

plugins {
    id("multiloader-neoforge")
}

multiloader {
    mods {
        create("kotlin-for-forge") {
            required()

            artifacts {
                implementation(group = "thedarkcolour", name = "kotlinforforge-neoforge", version = Constants.NEOFORGE_KOTLIN_VERSION)
            }
        }
    }
}
