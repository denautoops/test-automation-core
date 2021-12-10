package core.config;

import org.aeonbits.owner.ConfigCache;

public final class CoreConfiguration {

    private CoreConfiguration() {
    }

    public static ZephyrConfig getZephyrConfig() {
        return ConfigCache.getOrCreate(ZephyrConfig.class, System.getenv());
    }

    public static EncryptionConfig getEncodeConfig() {
        return ConfigCache.getOrCreate(EncryptionConfig.class, System.getenv());
    }

    public static GlobalConfig getGlobalConfig() {
        return ConfigCache.getOrCreate(GlobalConfig.class, System.getenv());
    }
}