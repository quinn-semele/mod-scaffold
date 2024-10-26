package dev.compasses.example

import net.fabricmc.loader.api.FabricLoader

object ThreadPlatformHelper : IPlatformHelper {
    override val platformName: String = "Fabric/Quilt"

    override fun isModLoaded(modId: String): Boolean {
        return FabricLoader.getInstance().isModLoaded(modId)
    }

    override fun isDevelopmentEnvironment(): Boolean {
        return FabricLoader.getInstance().isDevelopmentEnvironment
    }
}