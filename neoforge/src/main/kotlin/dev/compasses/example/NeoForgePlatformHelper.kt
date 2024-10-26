package dev.compasses.example

import net.neoforged.fml.ModList
import net.neoforged.fml.loading.FMLLoader

object NeoForgePlatformHelper : IPlatformHelper {
    override val platformName: String = "NeoForge"

    override fun isModLoaded(modId: String): Boolean {
        return ModList.get().isLoaded(modId)
    }

    override fun isDevelopmentEnvironment(): Boolean {
        return !FMLLoader.isProduction()
    }
}