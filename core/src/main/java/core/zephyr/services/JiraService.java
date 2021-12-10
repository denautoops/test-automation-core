package core.zephyr.services;

import core.config.CoreConfiguration;
import core.config.ZephyrConfig;
import core.zephyr.client.RestAssuredManager;
import core.zephyr.responses.IssueResponse;
import org.apache.http.HttpStatus;

/**
 * Service providing methods for working with Jira API
 * <p>
 *
 * @author Denis.Martynov
 * Created on 13.05.21
 */
public class JiraService {

    private static final ZephyrConfig ZEPHYR_CONFIG = CoreConfiguration.getZephyrConfig();

    private static final String GET_ISSUE_ENDPOINT = "/api/2/issue/%s";

    private final RestAssuredManager restAssuredManager;

    public JiraService() {
        this.restAssuredManager = new RestAssuredManager(ZEPHYR_CONFIG.jiraEncodedCredentials());
    }

    private StringBuilder buildBaseUrl() {
        return new StringBuilder().append(ZEPHYR_CONFIG.jiraUrl());
    }

    public Long getIssueIdByKey(final String key) {
        String fullUrl = buildBaseUrl().append(String.format(GET_ISSUE_ENDPOINT, key))
                .toString();

        IssueResponse issueResponse = restAssuredManager.getRequestSpecification()
                .get(fullUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(IssueResponse.class);

        return issueResponse.getId();
    }

}
