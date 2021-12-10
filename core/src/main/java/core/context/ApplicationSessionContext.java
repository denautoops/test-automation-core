package core.context;

import java.lang.reflect.Method;

public interface ApplicationSessionContext {

    void release(Method method, boolean isTestFailed);

    void init(Method method);

}
