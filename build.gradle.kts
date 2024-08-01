import dev.compasses.multiloader.Constants
import me.modmuss50.mpp.ReleaseType
import org.codehaus.groovy.runtime.ProcessGroovyMethods

plugins {
    id("me.modmuss50.mod-publish-plugin") version "0.6.3"
}

// To update run the :wrapper task, update values here as required from the gradle site below.
// https://gradle.org/release-checksums/
tasks.wrapper {
    gradleVersion = "8.9"
    distributionSha256Sum = "d725d707bfabd4dfdc958c624003b3c80accc03f7037b5122c4b1d0ef15cecab"
    distributionType = Wrapper.DistributionType.BIN
}

val projectsToPublish = mapOf(
    "NeoForge" to findProject(":neoforge"),
    "Fabric" to findProject(":fabric"),
    "Quilt" to findProject(":quilt")
).filter { it.value != null }.mapValues { (_, loader) -> loader!! }

val modChangelog = providers.provider {
    val compareTag = ProcessGroovyMethods.getText(ProcessGroovyMethods.execute("git describe --tags --abbrev=0")).trim()
    val commitHash = ProcessGroovyMethods.getText(ProcessGroovyMethods.execute("git rev-parse HEAD")).trim()

    buildString {
        appendLine(file("changelog.md").readText(Charsets.UTF_8).trimEnd())
        appendLine()
        if (compareTag.isNotBlank()) {
            appendLine("A detailed changelog can be found [here](${Constants.compareUrl}${compareTag}...${commitHash}).")
        } else {
            appendLine("A detailed changelog could not be made for this release, sorry.")
        }
    }
}

val curseforgeOptions = publishMods.curseforgeOptions {
    accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
    projectId = Constants.CURSEFORGE_PROJECT_ID
    projectSlug = Constants.CURSEFORGE_PROJECT_SLUG
    minecraftVersions = listOf(Constants.MINECRAFT_VERSION)
    clientRequired = Constants.CLIENTSIDE_REQUIRED
    serverRequired = Constants.SERVERSIDE_REQUIRED
    javaVersions = Constants.JAVA_VERSIONS_SUPPORTED
}

val modrinthOptions = publishMods.modrinthOptions {
    accessToken = providers.environmentVariable("MODRINTH_TOKEN")
    projectId = Constants.MODRINTH_PROJECT_ID
    minecraftVersions = listOf(Constants.MINECRAFT_VERSION)
}

publishMods {
    changelog = modChangelog
    type = if ("alpha" in Constants.MOD_VERSION) {
        ReleaseType.ALPHA
    } else if ("beta" in Constants.MOD_VERSION) {
        ReleaseType.BETA
    } else {
        ReleaseType.STABLE
    }

    dryRun = providers.provider {
        (Constants.CURSEFORGE_PROJECT_ID != "000000" && !curseforgeOptions.get().accessToken.isPresent) ||
                 (Constants.MODRINTH_PROJECT_ID != "00000000" && !modrinthOptions.get().accessToken.isPresent)
    }
}

val publishTasks = projectsToPublish.map { (name, loader) ->
    Pair(name, buildList {
        if (Constants.CURSEFORGE_PROJECT_ID != "000000") {
            add(publishMods.curseforge("CurseForge$name") {
                from(curseforgeOptions)
                modLoaders.add(name.lowercase())

                file = if (loader.extensions.findByName("loom") != null) {
                    loader.tasks.getByName("remapJar", Jar::class).archiveFile
                } else {
                    loader.tasks.getByName("jar", Jar::class).archiveFile
                }
            })
        }

        if (Constants.MODRINTH_PROJECT_ID != "00000000") {
            add(publishMods.modrinth("Modrinth$name") {
                from(modrinthOptions)
                modLoaders.add(name.lowercase())

                file = if (loader.extensions.findByName("loom") != null) {
                    loader.tasks.getByName("remapJar", Jar::class).archiveFile
                } else {
                    loader.tasks.getByName("jar", Jar::class).archiveFile
                }
            })
        }
    })
}.toMap()