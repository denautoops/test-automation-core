package api.business.usermanagement.dto.request;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    String email;
    String password;
    Long accountUserId;
    String deviceId;
}
