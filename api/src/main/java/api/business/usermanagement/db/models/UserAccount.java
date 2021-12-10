package api.business.usermanagement.db.models;

import api.business.usermanagement.db.enums.AccountRole;
import api.business.usermanagement.db.enums.AccountStatus;
import core.db.EntityModel;
import core.db.encryption.Encrypted;
import core.db.encryption.PasswordEncoded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_account")
public class UserAccount implements EntityModel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Encrypted
    @Column(name = "personal_id")
    String personalId;

    @Encrypted
    @Column(name = "email")
    String email;

    @Encrypted
    @Column(name = "first_name")
    String firstName;

    @Encrypted
    @Column(name = "last_name")
    String lastName;

    @Encrypted
    @Column(name = "phone")
    String phone;

    @Encrypted
    @Column(name = "address")
    String address;

    @PasswordEncoded
    @Column(name = "password")
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    AccountRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    AccountStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    Instant createdAt;
}
