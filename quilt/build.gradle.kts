import dev.compasses.multiloader.extension.DependencyType

plugins {
    id("multiloader-threadlike")
}

multiloader {
    dependencies {
        create("qsl") {
            type = DependencyType.REQUIRED

            requiresRepo("QuiltMC Maven", "https://maven.quiltmc.org/repository/release/", setOf(
                "org.quiltmc",
                "org.quiltmc.quilted-fabric-api"
            )
            )

            artifacts {
                modImplementation(group = "org.quiltmc", name = "quilt-loader", version = "0.25.0")
                modImplementation(group = "org.quiltmc.quilted-fabric-api", name = "quilted-fabric-api", version = "11.0.0-alpha.3+0.100.7-1.21")
            }
        }
    }
}