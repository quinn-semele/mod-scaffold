package dev.compasses.example

import net.fabricmc.loader.api.FabricLoader

object FabricPlatformHelper : IPlatformHelper {
    override fun getPlatformName(): String = "Fabric"

    override fun isModLoaded(modId: String): Boolean {
        return FabricLoader.getInstance().isModLoaded(modId)
    }

    override fun isDevelopmentEnvironment(): Boolean {
        return FabricLoader.getInstance().isDevelopmentEnvironment
    }
}