package core.zephyr.services;

import core.config.CoreConfiguration;
import core.config.ZephyrConfig;
import core.zephyr.client.RestAssuredManager;
import core.zephyr.requests.CreateExecutionRequest;
import core.zephyr.requests.CreateFolderRequest;
import core.zephyr.requests.UpdateExecutionRequest;
import core.zephyr.responses.CycleFolderResponse;
import core.zephyr.responses.IssueExecutionResponse;
import core.zephyr.responses.IssueExecutionsResponse;
import core.zephyr.responses.ZapiResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service providing methods for working with Zephyr API
 * <p>
 *
 * @author Denis.Martynov
 * Created on 13.05.21
 */
public class ZephyrService {

    private static final ZephyrConfig ZEPHYR_CONFIG = CoreConfiguration.getZephyrConfig();

    private static final String ZAPI_URI = "/zapi/latest";

    private static final String GET_EXECUTIONS_BY_ISSUE = "/execution?issueId=%s";
    private static final String UPDATE_EXECUTION_STATUS = "/execution/%s/execute";
    private static final String CREATE_FOLDER = "/folder/create";
    private static final String CREATE_EXECUTION = "/execution";
    private static final String DELETE_EXECUTION = "/execution/%s";
    private static final String GET_CYCLE_FOLDERS = "/cycle/%s/folders?projectId=%s&versionId=%s";

    private final RestAssuredManager restAssuredManager;

    public ZephyrService() {
        this.restAssuredManager = new RestAssuredManager(ZEPHYR_CONFIG.jiraEncodedCredentials());
    }

    private StringBuilder buildBaseUrl() {
        return new StringBuilder().append(ZEPHYR_CONFIG.jiraUrl())
                .append(ZAPI_URI);
    }

    public void updateExecutionDetails(final Long executionId, final UpdateExecutionRequest updateExecutionRequest) {
        String fullUrl = buildBaseUrl().append(String.format(UPDATE_EXECUTION_STATUS, executionId))
                .toString();

        restAssuredManager.getRequestSpecification()
                .body(updateExecutionRequest)
                .put(fullUrl)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public void deleteExecution(final Long executionId) {
        String fullUrl = buildBaseUrl().append(String.format(DELETE_EXECUTION, executionId))
                .toString();

        restAssuredManager.getRequestSpecification()
                .delete(fullUrl)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public Long createExecution(final CreateExecutionRequest createExecutionRequest) {
        String fullUrl = buildBaseUrl().append(CREATE_EXECUTION)
                .toString();

        Response response = restAssuredManager.getRequestSpecification()
                .body(createExecutionRequest)
                .post(fullUrl);

        Map<String, String> responseMap = response.then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .get();

        String executionId = responseMap.keySet()
                .stream()
                .findFirst()
                .orElse(null);

        if (executionId == null) {
            throw new RuntimeException(String.format(
                    "Execution with '%s' issueId does not created!", createExecutionRequest.getIssueId()));
        }
        return Long.parseLong(executionId);
    }

    public ZapiResponse createCycleFolder(final CreateFolderRequest createFolderRequest) {
        String fullUrl = buildBaseUrl().append(CREATE_FOLDER)
                .toString();

        return restAssuredManager.getRequestSpecification()
                .body(createFolderRequest)
                .post(fullUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ZapiResponse.class);
    }

    public List<CycleFolderResponse> getCycleFolders(final Long projectId, final Long versionId, final Long cycleId) {
        String fullUrl = buildBaseUrl().append(String.format(GET_CYCLE_FOLDERS, cycleId, projectId, versionId))
                .toString();

        CycleFolderResponse[] cycleFolders = restAssuredManager.getRequestSpecification()
                .get(fullUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(CycleFolderResponse[].class);
        return Arrays.asList(cycleFolders);
    }

    public List<IssueExecutionResponse> getIssueExecutions(final Long issueId, final String versionName, final String cycleName) {
        String fullUrl = buildBaseUrl().append(String.format(GET_EXECUTIONS_BY_ISSUE, issueId))
                .toString();

        IssueExecutionsResponse issueExecutionsResponse = restAssuredManager.getRequestSpecification()
                .get(fullUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(IssueExecutionsResponse.class);

        return issueExecutionsResponse.getExecutions()
                .stream()
                .filter(e -> e.getProjectKey().equals(ZEPHYR_CONFIG.projectName())
                        && e.getVersionName().equals(versionName)
                        && e.getCycleName().equals(cycleName))
                .collect(Collectors.toList());
    }
}
