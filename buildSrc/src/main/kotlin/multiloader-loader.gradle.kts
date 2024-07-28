plugins {
    id("multiloader-common")
}

configurations {
    create("commonJava") { isCanBeResolved = true }
    create("commonResources") { isCanBeResolved = true }
}

dependencies {
    compileOnly(project(":common", configuration="${project.name}Java"))

    "commonJava"(project(path=":common", configuration="${project.name}Java"))
    "commonResources"(project(path=":common", configuration="commonResources"))
}

tasks {
    "compileJava"(JavaCompile::class) {
        dependsOn(configurations.getByName("commonJava"))
        source(configurations.getByName("commonJava"))
    }

    processResources {
        dependsOn(configurations.getByName("commonResources"))
        from(configurations.getByName("commonResources"))
    }
}