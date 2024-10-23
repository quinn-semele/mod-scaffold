import dev.compasses.multiloader.Constants

plugins {
    id("multiloader-threadlike")
}

multiloader {
    mods {
        create("fabric-api") {
            required()

            artifacts {
                modImplementation(group = "net.fabricmc", name = "fabric-loader", version = Constants.FABRIC_LOADER_VERSION)
                modImplementation(group = "net.fabricmc.fabric-api", name = "fabric-api", version = Constants.FABRIC_API_VERSION)
            }
        }

        if (plugins.hasPlugin("org.jetbrains.kotlin.jvm")) {
            create("fabric-language-kotlin") {
                required()

                artifacts {
                    modImplementation(group = "net.fabricmc", name = "fabric-language-kotlin", version = Constants.FABRIC_KOTLIN_VERSION) {
                        exclude(group = "net.fabricmc", module = "fabric-loader")
                    }
                }
            }
        }
    }
}
