package api.business.usermanagement.db.repositories;

import api.business.usermanagement.db.UserManagementUnit;
import api.business.usermanagement.db.models.UserAccount;
import core.db.encryption.Encrypter;
import core.db.persistence.CrudRepository;
import core.db.persistence.Persistence;
import core.db.persistence.Repository;
import core.db.persistence.SimpleCrudRepository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Repository for {@link UserAccount}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 24.04.21
 */
@Repository(UserAccountRepository.UserAccountRepositoryImpl.class)
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findByPersonalId(String personalId);

    @Persistence(UserManagementUnit.class)
    final class UserAccountRepositoryImpl extends SimpleCrudRepository<UserAccount, Long> implements UserAccountRepository {
        @Override
        public UserAccount findByPersonalId(String personalId) {
            String encryptedPersonalId = Encrypter.encrypt(personalId);

            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<UserAccount> cq = cb.createQuery(getDomainClass());
            Root<UserAccount> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(cb.equal(rootEntry.get("personalId"), encryptedPersonalId));
            TypedQuery<UserAccount> query = getEntityManager().createQuery(cq);
            return query.getSingleResult();
        }
    }

}
