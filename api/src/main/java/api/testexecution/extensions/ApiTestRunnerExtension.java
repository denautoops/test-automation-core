package api.testexecution.extensions;

import api.testexecution.context.ApiContext;
import core.assertions.SoftAssertions;
import core.context.SessionContextManager;
import core.logger.Logger;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Default Test Runner Extension
 * <p>
 *
 * @author Denis.Martynov
 * Created on 29.03.21
 */
public class ApiTestRunnerExtension implements BeforeEachCallback, AfterEachCallback {

    protected static final Logger LOGGER = Logger.getInstance();

    @Override
    public void beforeEach(ExtensionContext context) {
        LOGGER.testStartInfo(context.getDisplayName());

        ApiContext apiContext = new ApiContext();
        SessionContextManager.createSessionContext(context.getRequiredTestMethod(), apiContext);
        SoftAssertions.initStore();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        boolean isTestFailed = context.getExecutionException().isPresent();
        SessionContextManager.releaseContext(Thread.currentThread(), context.getRequiredTestMethod(), isTestFailed);
        SoftAssertions.hardAssertErrorStorage();
        SoftAssertions.clearStorage();

        LOGGER.testEndInfo(context.getDisplayName());
    }
}
