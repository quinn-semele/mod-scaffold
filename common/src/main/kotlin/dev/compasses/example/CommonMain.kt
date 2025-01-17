package dev.compasses.example

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Items

object CommonMain {
    lateinit var PLATFORM_HELPER: IPlatformHelper

    fun init(helper: IPlatformHelper) {
        PLATFORM_HELPER = helper

        Constants.LOG.info(
            "Hello from Common init on {}! we are currently in a {} environment!",
            PLATFORM_HELPER.platformName,
            PLATFORM_HELPER.environmentName
        )
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND))

        if (PLATFORM_HELPER.isModLoaded(Constants.MOD_ID)) {
            Constants.LOG.info("Hello to ${Constants.MOD_ID}")
        }
    }
}