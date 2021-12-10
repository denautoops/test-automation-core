package api.business.usermanagement.db.repositories;

import api.business.usermanagement.db.UserManagementUnit;
import core.db.persistence.CrudRepository;
import core.db.persistence.Persistence;
import core.db.persistence.Repository;
import core.db.persistence.SimpleCrudRepository;

/**
 * Repository for {@link AccountAction}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 7.06.21
 */
@Repository(AccountActionRepository.AccountActionRepositoryImpl.class)
public interface AccountActionRepository extends CrudRepository<AccountAction, Long> {

    @Persistence(UserManagementUnit.class)
    final class AccountActionRepositoryImpl extends SimpleCrudRepository<AccountAction, Long> implements AccountActionRepository {
    }
}
