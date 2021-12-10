package api.usermanagement.auth;

import api.business.common.data.AppName;
import api.business.common.errors.ErrorResponse;
import api.business.usermanagement.db.enums.AccountRole;
import api.business.usermanagement.db.models.UserAccount;
import api.business.usermanagement.dto.TokenDto;
import api.business.usermanagement.dto.request.LoginRequest;
import api.business.usermanagement.errors.UserManagementErrorCodeInfo;
import api.business.usermanagement.mappers.UserAccountMapper;
import api.common.ApiCommonBaseTest;
import core.assertions.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * User Management API tests for /auth end-point
 * <p>
 *
 * @author Denis.Martynov
 * Created on 28.04.21
 */
@DisplayName("Post Login Test")
public class PostLoginTest extends ApiCommonBaseTest {

    @ParameterizedTest(name = "POST login: user with {0} role")
    @Tag("usermanagement")
    @DisplayName("POST login: user with role")
    @EnumSource(AccountRole.class)
    void testLoginUserWithRole(AccountRole accountRole) {
        UserAccount userAccount = getDefaultUserAccountByRole(accountRole);

        LoginRequest loginRequest = UserAccountMapper.toLoginRequest(userAccount);

        TokenDto tokenDto = authUserService.login(loginRequest);

        SoftAssertions.hardAssertNotNull(tokenDto, "TokenDto not received!");
        SoftAssertions.assertFalse(tokenDto.getAccessToken().isEmpty(), "Access token is not empty!");
        SoftAssertions.assertFalse(tokenDto.getRefreshToken().isEmpty(), "Refresh token is not empty!");
    }

    @ParameterizedTest(name = "POST login: user with {0} role and without deviceId")
    @Tag("usermanagement")
    @DisplayName("POST login: without deviceId")
    @EnumSource(AccountRole.class)
    void testLoginUserWithoutDeviceId(AccountRole accountRole) {
        UserAccount userAccount = getDefaultUserAccountByRole(accountRole);
        LoginRequest loginRequest = UserAccountMapper.toLoginRequest(userAccount);
        loginRequest.setDeviceId(null);

        ErrorResponse expectedError = ErrorResponse.builder()
                .errorCodeInfo(UserManagementErrorCodeInfo.DEFAULT_BAD_REQUEST)
                .applicationName(AppName.USER_MANAGEMENT)
                .build();

        ErrorResponse actualError = authUserService.whenLogin(loginRequest)
                .then()
                .extract()
                .as(ErrorResponse.class);

        SoftAssertions.assertEquals(expectedError, actualError, "Receiving ErrorResponse is not correct!");
    }
}
