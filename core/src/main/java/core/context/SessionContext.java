package core.context;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Default Session Context
 * <p>
 *
 * @author Denis.Martynov
 * Created on 2.04.21
 */
public class SessionContext<T extends ApplicationSessionContext> {

    private Thread thread;
    private Method method;

    private T applicationSessionContext;

    public SessionContext(Thread thread, Method method, T applicationSessionContext) {
        this.thread = thread;
        this.method = method;
        this.applicationSessionContext = applicationSessionContext;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public T getApplicationSessionContext() {
        return applicationSessionContext;
    }

    public void initApplicationContext() {
        applicationSessionContext.init(method);
    }

    public void release(boolean isTestFailed) {
        if (Objects.nonNull(applicationSessionContext)) {
            applicationSessionContext.release(method, isTestFailed);
            applicationSessionContext = null;
        }

        thread = null;
        method = null;
    }
}
