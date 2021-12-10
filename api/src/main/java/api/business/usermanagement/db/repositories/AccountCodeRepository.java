package api.business.usermanagement.db.repositories;

import api.business.usermanagement.db.UserManagementUnit;
import api.business.usermanagement.db.models.UserAccount;
import core.db.persistence.CrudRepository;
import core.db.persistence.Persistence;
import core.db.persistence.Repository;
import core.db.persistence.SimpleCrudRepository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Repository for {@link AccountCode}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 24.04.21
 */
@Repository(AccountCodeRepository.AccountCodeRepositoryImpl.class)
public interface AccountCodeRepository extends CrudRepository<AccountCode, Long> {

    AccountCode findByUserAccount(UserAccount userAccount);

    List<AccountCode> findAllByUserAccount(UserAccount userAccount);

    AccountCode findByUserAccountAndType(UserAccount userAccount, AccountCodeType accountCodeType);

    @Persistence(UserManagementUnit.class)
    final class AccountCodeRepositoryImpl extends SimpleCrudRepository<AccountCode, Long> implements AccountCodeRepository {
        @Override
        public AccountCode findByUserAccount(UserAccount userAccount) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<AccountCode> cq = cb.createQuery(getDomainClass());
            Root<AccountCode> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(cb.equal(rootEntry.get("userAccount"), userAccount));
            TypedQuery<AccountCode> query = getEntityManager().createQuery(cq);
            return query.getSingleResult();
        }

        @Override
        public List<AccountCode> findAllByUserAccount(UserAccount userAccount) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<AccountCode> cq = cb.createQuery(getDomainClass());
            Root<AccountCode> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(cb.equal(rootEntry.get("userAccount"), userAccount));
            TypedQuery<AccountCode> query = getEntityManager().createQuery(cq);
            return query.getResultList();
        }

        @Override
        public AccountCode findByUserAccountAndType(UserAccount userAccount, AccountCodeType accountCodeType) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<AccountCode> cq = cb.createQuery(getDomainClass());
            Root<AccountCode> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(
                            cb.and(
                                    cb.equal(rootEntry.get("userAccount"), userAccount),
                                    cb.equal(rootEntry.get("type"), accountCodeType)
                            )
                    );
            TypedQuery<AccountCode> query = getEntityManager().createQuery(cq);
            return query.getSingleResult();
        }
    }
}
