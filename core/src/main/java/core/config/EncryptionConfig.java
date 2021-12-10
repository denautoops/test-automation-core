package core.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties"})
public interface EncryptionConfig extends Config {

    @Key("dataSecret")
    String dataSecret();

    @Key("passwordSecret")
    String passwordSecret();

    @Key("passwordIteration")
    Integer passwordIteration();

    @Key("passwordKeyLength")
    Integer passwordKeyLength();

}
