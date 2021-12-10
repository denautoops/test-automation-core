package api.testexecution;

import api.business.common.services.AuthorizedService;
import api.business.common.services.IService;
import api.business.usermanagement.dto.TokenDto;
import api.config.ApiConfiguration;
import api.config.ApiEnvironment;
import api.testexecution.extensions.ApiTestRunnerExtension;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Base Test
 * <p>
 *
 * @author Denis.Martynov
 * Created on 1.04.21
 */
@ExtendWith(ApiTestRunnerExtension.class)
public abstract class ApiBaseTest {

    protected static final ApiEnvironment ENV = ApiConfiguration.getEnvironment();

    @BeforeEach
    public void preCondition() {
        customBeforeMethod();
    }

    @AfterEach
    public void postCondition() {
    }

    protected void customBeforeMethod() {}

    protected <T extends AuthorizedService> T invokeService(Class<T> clazz, TokenDto token) {
        T service;

        try {
            service = ConstructorUtils.invokeConstructor(clazz, token);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not create '%s' service with token!", clazz.getSimpleName()));
        }

        return service;
    }

    protected <T extends IService> T invokeService(Class<T> clazz) {
        T service;

        try {
            service = ConstructorUtils.invokeConstructor(clazz);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not create '%s' unauthorized service!", clazz.getSimpleName()));
        }

        return service;
    }

}
