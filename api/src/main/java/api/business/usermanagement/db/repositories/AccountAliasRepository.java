package api.business.usermanagement.db.repositories;

import api.business.usermanagement.db.UserManagementUnit;
import api.business.usermanagement.db.models.UserAccount;
import core.db.persistence.CrudRepository;
import core.db.persistence.Persistence;
import core.db.persistence.Repository;
import core.db.persistence.SimpleCrudRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Repository for {@link AccountAlias}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 20.08.21
 */
@Repository(AccountAliasRepository.AccountAliasRepositoryImpl.class)
public interface AccountAliasRepository extends CrudRepository<AccountAlias, Long> {

    AccountAlias findByUserAccountAndType(UserAccount userAccount, AliasType aliasType);

    @Persistence(UserManagementUnit.class)
    final class AccountAliasRepositoryImpl extends SimpleCrudRepository<AccountAlias, Long> implements AccountAliasRepository {
        @Override
        public AccountAlias findByUserAccountAndType(UserAccount userAccount, AliasType aliasType) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<AccountAlias> cq = cb.createQuery(getDomainClass());
            Root<AccountAlias> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(
                            cb.and(
                                    cb.equal(rootEntry.get("userAccount"), userAccount),
                                    cb.equal(rootEntry.get("type"), aliasType)
                            )
                    );
            TypedQuery<AccountAlias> query = getEntityManager().createQuery(cq);
            AccountAlias result = null;
            try {
                result = query.getSingleResult();
            } catch (NoResultException ignore) {
            }
            return result;
        }
    }
}
