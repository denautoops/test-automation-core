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

/**
 * Repository for {@link SettingsKey}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 7.06.21
 */
@Repository(SettingsKeyRepository.SettingsKeyRepositoryImpl.class)
public interface SettingsKeyRepository extends CrudRepository<SettingsKey, Long> {

    SettingsKey findByUserAccount(UserAccount userAccount);

    @Persistence(UserManagementUnit.class)
    final class SettingsKeyRepositoryImpl extends SimpleCrudRepository<SettingsKey, Long> implements SettingsKeyRepository {
        @Override
        public SettingsKey findByUserAccount(UserAccount userAccount) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<SettingsKey> cq = cb.createQuery(getDomainClass());
            Root<SettingsKey> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(cb.equal(rootEntry.get("userAccount"), userAccount));
            TypedQuery<SettingsKey> query = getEntityManager().createQuery(cq);
            return query.getSingleResult();
        }
    }
}
