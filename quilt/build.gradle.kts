import dev.compasses.multiloader.Constants

plugins {
    id("multiloader-threadlike")
}

multiloader {
    dependencies {
        create("qsl") {
            required()

            requiresRepo("QuiltMC Maven", "https://maven.quiltmc.org/repository/release/", setOf(
                "org.quiltmc",
                "org.quiltmc.quilted-fabric-api"
            ))

            artifacts {
                modImplementation(group = "org.quiltmc", name = "quilt-loader", version = Constants.QUILT_LOADER_VERSION)
                modImplementation(group = "org.quiltmc.quilted-fabric-api", name = "quilted-fabric-api", version = Constants.QUILT_API_VERSION)
            }
        }
    }
}
