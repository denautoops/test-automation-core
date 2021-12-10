package api.business.usermanagement.services.auth;

import api.business.common.services.IService;
import api.business.usermanagement.dto.TokenDto;
import api.business.usermanagement.dto.request.LoginRequest;
import api.testexecution.context.ApiContextManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

/**
 * User Management API Auth service
 * <p>
 *
 * @author Denis.Martynov
 * Created on 8.04.21
 */
public class AuthUserService implements IService {

    public AuthUserService() {
    }

    public Response whenLogin(LoginRequest loginRequest) {
        String fullURL = AuthUserEndpoint.POST_AUTH.getFullUrl();

        return ApiContextManager.getContext()
                .getRequestSpecification()
                .when()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post(fullURL);
    }

    public TokenDto login(LoginRequest loginRequest) {
        return whenLogin(loginRequest)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(TokenDto.class);
    }
}
