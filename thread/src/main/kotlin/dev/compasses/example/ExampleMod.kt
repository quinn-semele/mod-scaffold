package dev.compasses.example

import net.fabricmc.api.ModInitializer

class ExampleMod : ModInitializer {
    override fun onInitialize() {
        Constants.LOG.info("Hello Fabric world!")
        CommonClass.init(FabricPlatformHelper)
    }
}