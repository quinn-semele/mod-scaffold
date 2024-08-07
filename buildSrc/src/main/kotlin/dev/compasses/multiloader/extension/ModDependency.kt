package dev.compasses.multiloader.extension

import org.gradle.api.Named
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Property

abstract class ModDependency : Named {
    private var isFrozen: Boolean = false
    private val repositories = mutableListOf<Triple<String, String, Set<String>>>()
    private lateinit var artifacts: DependencyHandler.() -> Unit

    abstract val modrinthName: Property<String>
    abstract val curseforgeName: Property<String>
    abstract val type: Property<DependencyType>

    init {
        modrinthName.convention(name)
        curseforgeName.convention(name)
        type.convention(DependencyType.OPTIONAL)
    }

    fun freezeProperties() {
        isFrozen = true

        modrinthName.finalizeValue()
        curseforgeName.finalizeValue()
        type.finalizeValue()
    }

    fun requiresRepo(name: String, url: String, groups: Set<String> = setOf()) {
        if (isFrozen) throw IllegalStateException("Cannot require more repositories when frozen.")
        repositories.add(Triple(name, url, groups))
    }

    fun artifacts(function: DependencyHandler.() -> Unit) {
        if (isFrozen) throw IllegalStateException("Cannot define artifacts when frozen.")
        artifacts = function
    }

    fun getRepositories() = repositories
    fun getArtifacts() = artifacts
}