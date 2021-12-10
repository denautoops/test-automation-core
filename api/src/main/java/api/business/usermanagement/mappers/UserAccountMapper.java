package api.business.usermanagement.mappers;

import api.business.usermanagement.db.models.UserAccount;
import api.business.usermanagement.dto.request.LoginRequest;

import java.util.UUID;

/**
 * Mapper for {@link UserAccount}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 28.04.21
 */
public final class UserAccountMapper {

    private UserAccountMapper() {
    }

    public static LoginRequest toLoginRequest(final UserAccount userAccount) {
        String deviceId = UUID.randomUUID().toString();
        return LoginRequest.builder()
                .email(userAccount.getEmail())
                .password(userAccount.getPassword())
                .accountUserId(userAccount.getId())
                .deviceId(deviceId)
                .build();
    }
}
