package api.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:db/persistence.properties"})
public interface PersistenceProperties extends Config {

    @Key("db.server")
    String dbServer();

    @Key("db.user")
    String dbUser();

    @Key("db.password")
    String dbPassword();

    @Key("userManagement.unit.name")
    String userManagementUnitName();

    @Key("userManagement.db.name")
    String userManagementDbName();

}
