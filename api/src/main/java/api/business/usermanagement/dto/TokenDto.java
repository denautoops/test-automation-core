package api.business.usermanagement.dto;

import api.business.usermanagement.db.enums.AccountStatus;
import api.business.usermanagement.dto.enums.UserApproveType;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenDto {
    String accessToken;
    String refreshToken;
    AccountStatus status;
    UserApproveType userApproveType;
}
