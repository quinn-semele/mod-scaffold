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

// This feels like a hack but I can't really think of a way to do this properly.
evaluationDependsOnChildren()

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
            appendLine("A detailed changelog can be found [here](${Constants.COMPARE_URL}${compareTag}...${commitHash}).")
        } else {
            appendLine("A detailed changelog could not be made for this release, sorry.")
        }
    }
}

val curseforgeOptions = Constants.curseforgeProperties?.let { props ->
    publishMods.curseforgeOptions {
        accessToken = providers.environmentVariable(props.uploadToken)
        projectId = props.projectId
        projectSlug = props.projectSlug
        minecraftVersions = listOf(Constants.MINECRAFT_VERSION)
        clientRequired = props.clientSideRequired
        serverRequired = props.serverSideRequired
        javaVersions = props.supportedJavaVersions
    }
}

val modrinthOptions = Constants.modrinthProperties?.let { props ->
    publishMods.modrinthOptions {
        accessToken = providers.environmentVariable(props.uploadToken)
        projectId = props.projectId
        minecraftVersions = listOf(Constants.MINECRAFT_VERSION)
    }
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
        (Constants.curseforgeProperties != null && !curseforgeOptions!!.get().accessToken.isPresent) ||
                 (Constants.modrinthProperties != null && !modrinthOptions!!.get().accessToken.isPresent)
    }
}

val publishTasks = projectsToPublish.map { (name, loader) ->
    name to buildList {
        Constants.curseforgeProperties?.run {
            add(publishMods.curseforge("CurseForge$name") {
                from(curseforgeOptions!!)
                displayName = "$name ${loader.version}"
                version = "${Constants.MOD_VERSION}+${name.lowercase()}"
                modLoaders.add(name.lowercase())

                file = if (loader.extensions.findByName("loom") != null) {
                    loader.tasks.getByName("remapJar", AbstractArchiveTask::class).archiveFile
                } else {
                    loader.tasks.getByName("jar", AbstractArchiveTask::class).archiveFile
                }
            })
        }

        Constants.modrinthProperties?.run {
            add(publishMods.modrinth("Modrinth$name") {
                from(modrinthOptions!!)
                displayName = "$name ${loader.version}"
                version = "${Constants.MOD_VERSION}+${name.lowercase()}"
                modLoaders.add(name.lowercase())

                file = if (loader.extensions.findByName("loom") != null) {
                    loader.tasks.getByName("remapJar", AbstractArchiveTask::class).archiveFile
                } else {
                    loader.tasks.getByName("jar", AbstractArchiveTask::class).archiveFile
                }
            })
        }
    }
}.toMap()
