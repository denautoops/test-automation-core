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
 * Repository for {@link RefreshSession}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 24.04.21
 */
@Repository(RefreshSessionRepository.RefreshSessionRepositoryImpl.class)
public interface RefreshSessionRepository extends CrudRepository<RefreshSession, Long> {

    RefreshSession findByUserAccount(UserAccount userAccount);

    @Persistence(UserManagementUnit.class)
    final class RefreshSessionRepositoryImpl extends SimpleCrudRepository<RefreshSession, Long> implements RefreshSessionRepository {

        @Override
        public RefreshSession findByUserAccount(UserAccount userAccount) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<RefreshSession> cq = cb.createQuery(getDomainClass());
            Root<RefreshSession> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(cb.equal(rootEntry.get("userAccount"), userAccount));
            TypedQuery<RefreshSession> query = getEntityManager().createQuery(cq);
            return query.getSingleResult();
        }
    }

}
