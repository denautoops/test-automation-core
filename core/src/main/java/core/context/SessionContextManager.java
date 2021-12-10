package core.context;

import core.config.CoreConfiguration;
import core.config.GlobalConfig;
import core.logger.Logger;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Session Context Manager
 * <p>
 *
 * @author Denis.Martynov
 * Created on 2.04.21
 */
public class SessionContextManager {

    private static final Logger LOGGER = Logger.getInstance(SessionContextManager.class);
    private static final GlobalConfig GLOBAL_CONFIG = CoreConfiguration.getGlobalConfig();

    private static final LinkedList<SessionContext<?>> sessionContexts = new LinkedList<>();

    private static SessionContext<?> getActiveContext() {
        synchronized (sessionContexts) {
            long threadId = Thread.currentThread().getId();
            SessionContext<?> activeContext = sessionContexts.stream()
                    .filter(sessionContext ->
                            Objects.nonNull(sessionContext.getThread())
                                    && sessionContext.getThread().getId() == threadId)
                    .findAny()
                    .orElse(null);

            if (Objects.isNull(activeContext)) {
                LOGGER.warn(String.format("Context for '%s' thread not found!", Thread.currentThread().getId()));
                return null;
            }

            return activeContext;
        }
    }

    public static synchronized SessionContext<?> getContext() {
        return getActiveContext();
    }

    public static synchronized ApplicationSessionContext getApplicationContext() {
        return getContext().getApplicationSessionContext();
    }

    public static synchronized <T extends ApplicationSessionContext> void createSessionContext(
            Method testMethod,
            T applicationSessionContext
    ) {
        synchronized (sessionContexts) {
            /*if (sessionContexts.size() == GLOBAL_CONFIG.threadCount()) {
                throw new RuntimeException("This should never happened. All contexts in use!");
            }*/

            SessionContext<T> context = new SessionContext<>(Thread.currentThread(), testMethod, applicationSessionContext);
            context.initApplicationContext();
            sessionContexts.add(context);
        }
    }

    public static synchronized void releaseContext(final Thread thread, final Method testMethod, boolean isTestFailed) {
        if (Objects.isNull(testMethod)){
            return;
        }

        synchronized (sessionContexts) {
            SessionContext<?> foundedContext = sessionContexts.stream()
                    .filter(context -> context.getThread().getId() == thread.getId())
                    .findAny()
                    .orElse(null);
            if (Objects.nonNull(foundedContext)) {
                foundedContext.release(isTestFailed);
            }
            sessionContexts.remove(foundedContext);
        }
    }

}
