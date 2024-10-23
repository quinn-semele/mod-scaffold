package dev.compasses.example;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

public class CommonClass {
    public static IPlatformHelper PLATFORM_HELPER;
    public static void init(IPlatformHelper helper) {
        PLATFORM_HELPER = helper;

        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", PLATFORM_HELPER.getPlatformName(), PLATFORM_HELPER.getEnvironmentName());
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        if (PLATFORM_HELPER.isModLoaded("examplemod")) {

            Constants.LOG.info("Hello to examplemod");
        }
    }
}