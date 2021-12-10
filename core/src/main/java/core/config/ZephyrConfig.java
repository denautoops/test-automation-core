package core.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:zephyr.properties"})
public interface ZephyrConfig extends Config {

    @Key("jira.url")
    String jiraUrl();

    @Key("jira.user.encodedCredentials")
    String jiraEncodedCredentials();

    @Key("zephyr.project")
    String projectName();

}
