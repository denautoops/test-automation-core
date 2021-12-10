package api.business.common.services;

import io.restassured.http.Method;

/**
 * Endpoints interface
 * <p>
 *
 * @author Denis.Martynov
 * Created on 12.07.21
 */
public interface IEndpoint <T extends IService> extends ServiceUrl {
    String getFullUrl();

    Method getMethod();

    Class<T> getServiceClass();
}
