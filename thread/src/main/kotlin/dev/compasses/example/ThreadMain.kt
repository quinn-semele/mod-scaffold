package dev.compasses.example

import net.fabricmc.api.ModInitializer

class ThreadMain : ModInitializer {
    override fun onInitialize() {
        Constants.LOG.info("Hello Fabric world!")
        CommonMain.init(ThreadPlatformHelper)
    }
}