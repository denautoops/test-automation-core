package api.common;

import api.business.usermanagement.data.factories.UserAccountFactory;
import api.business.usermanagement.db.enums.AccountRole;
import api.business.usermanagement.db.models.UserAccount;
import api.business.usermanagement.db.repositories.UserAccountRepository;
import api.business.usermanagement.services.auth.AuthUserService;
import api.testexecution.ApiBaseTest;
import core.extensions.db.InjectRepository;
import core.extensions.db.RepositoryInjector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

/**
 * Base Test for Authorized API
 * <p>
 *
 * @author Denis.Martynov
 * Created on 9.07.21
 */
@ExtendWith(RepositoryInjector.class)
public abstract class ApiCommonBaseTest extends ApiBaseTest {
    protected AuthUserService authUserService;

    protected List<UserAccount> defaultUsers;

    @InjectRepository
    protected UserAccountRepository userAccountRepository;

    @BeforeEach
    void setUp() {
        createDefaultUsers();

        authUserService = invokeService(AuthUserService.class);
    }

    @AfterEach
    void cleanUp() {
        userAccountRepository.deleteAll(defaultUsers);
    }

    private void createDefaultUsers() {
        defaultUsers = UserAccountFactory.getDefaultUsers();
        userAccountRepository.saveAll(defaultUsers);
    }

    protected UserAccount getDefaultUserAccountByRole(final AccountRole accountRole) {
        return defaultUsers.stream()
                .filter(u -> u.getRole().equals(accountRole))
                .findAny()
                .orElseThrow();
    }
}
