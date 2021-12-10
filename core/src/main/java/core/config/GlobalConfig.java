package core.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties"})
public interface GlobalConfig extends Config {

    @Key("thread.count")
    Integer threadCount();
}
