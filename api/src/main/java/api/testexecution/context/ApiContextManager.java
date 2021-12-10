package api.testexecution.context;

import core.context.SessionContext;
import core.context.SessionContextManager;

/**
 * <p>
 *
 * @author Denis.Martynov
 * Created on 28.05.21
 */
public final class ApiContextManager {

    private ApiContextManager() {
    }

    public static synchronized ApiContext getContext() {
        SessionContext<?> sessionContext = SessionContextManager.getContext();
        if (ApiContext.class.isAssignableFrom(sessionContext.getApplicationSessionContext().getClass())) {
            return (ApiContext) sessionContext.getApplicationSessionContext();
        } else {
            throw new RuntimeException("Available context is not ApiContext!");
        }
    }
}
