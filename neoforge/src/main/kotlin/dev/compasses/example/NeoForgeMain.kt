package dev.compasses.example

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod

@Mod(Constants.MOD_ID)
class NeoForgeMain(eventBus: IEventBus, container: ModContainer) {
    init {
        Constants.LOG.info("Hello NeoForge world!")
        CommonMain.init(NeoForgePlatformHelper)
    }
}