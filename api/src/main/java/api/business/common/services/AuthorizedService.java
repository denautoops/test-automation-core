package api.business.common.services;

import api.business.usermanagement.dto.TokenDto;
import api.testexecution.context.ApiContextManager;
import io.restassured.specification.RequestSpecification;

/**
 * Authorized Service
 * <p>
 *
 * @author Denis.Martynov
 * Created on 27.05.21
 */
public abstract class AuthorizedService implements IService {

    private final TokenDto token;

    public AuthorizedService(TokenDto token) {
        this.token = token;
    }

    protected RequestSpecification getAuthorizedRequest() {
        return ApiContextManager.getContext()
                .getRequestSpecification()
                .auth()
                .oauth2(token.getAccessToken());
    }
}
