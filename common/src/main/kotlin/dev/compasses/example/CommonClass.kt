package dev.compasses.example

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Items

object CommonClass {
    lateinit var PLATFORM_HELPER: IPlatformHelper

    fun init(helper: IPlatformHelper) {
        PLATFORM_HELPER = helper

        Constants.LOG.info(
            "Hello from Common init on {}! we are currently in a {} environment!",
            PLATFORM_HELPER.getPlatformName(),
            PLATFORM_HELPER.getEnvironmentName()
        )
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND))

        if (PLATFORM_HELPER.isModLoaded(Constants.MOD_ID)) {
            Constants.LOG.info("Hello to ${Constants.MOD_ID}")
        }
    }
}