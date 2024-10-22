import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiloader-shared")
}

configurations {
    create("commonJava") { isCanBeResolved = true }
    create("commonKotlin") { isCanBeResolved = true }
    create("commonResources") { isCanBeResolved = true }
}

dependencies {
    compileOnly(project(":common"))

    "commonJava"(project(path=":common", configuration="commonJava"))
    "commonKotlin"(project(path=":common", configuration="commonKotlin"))
    "commonResources"(project(path=":common", configuration="commonResources"))
}

tasks {
    "compileJava"(JavaCompile::class) {
        dependsOn(configurations.getByName("commonJava"))
        source(configurations.getByName("commonJava"))
    }

    "compileKotlin"(KotlinCompile::class) {
        dependsOn(configurations.getByName("commonKotlin"))
        source(configurations.getByName("commonKotlin"))
    }

    processResources {
        dependsOn(configurations.getByName("commonResources"))
        from(configurations.getByName("commonResources"))
    }
}