import dev.compasses.multiloader.Constants

plugins {
    id("fabric-loom") version Constants.LOOM_VERSION apply false
    id("net.neoforged.moddev") version Constants.MOD_DEV_GRADLE_VERSION apply false
}