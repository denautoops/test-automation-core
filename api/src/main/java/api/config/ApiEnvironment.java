package api.config;

import core.config.GlobalConfig;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:apiEnv.properties"})
public interface ApiEnvironment extends GlobalConfig {

    @Key("api.baseUrl")
    String apiBaseUrl();

    @Key("api.private.baseUri")
    String apiPrivateBaseUri();

    @Key("api.userManagement.mapping.uri")
    String apiUserManagementMappingUri();
}
