package api.business.usermanagement.services.auth;

import api.business.common.services.IEndpoint;
import api.business.common.services.ServiceUrl;
import io.restassured.http.Method;

/**
 * Endpoints for Auth User Service
 * <p>
 *
 * @author Denis.Martynov
 * Created on 12.07.21
 */
public enum AuthUserEndpoint implements IEndpoint<AuthUserService>, ServiceUrl {
    POST_AUTH("/auth", Method.POST);

    private final String url;
    private final Method method;

    AuthUserEndpoint(String url, Method method) {
        this.url = url;
        this.method = method;
    }

    public String getFullUrl() {
        return userManagementUrl(url);
    }

    public Method getMethod() {
        return method;
    }

    public Class<AuthUserService> getServiceClass() {
        return AuthUserService.class;
    }
}
