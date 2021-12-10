package api.config;

import org.aeonbits.owner.ConfigCache;

public class ApiConfiguration {

    private ApiConfiguration() {
    }

    public static ApiEnvironment getEnvironment() {
        return ConfigCache.getOrCreate(ApiEnvironment.class, System.getenv());
    }

    public static PersistenceProperties getPersistenceProperties() {
        return ConfigCache.getOrCreate(PersistenceProperties.class, System.getenv());
    }

}