package api.business.usermanagement.db.repositories;

import api.business.usermanagement.db.UserManagementUnit;
import core.db.persistence.CrudRepository;
import core.db.persistence.Persistence;
import core.db.persistence.Repository;
import core.db.persistence.SimpleCrudRepository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Repository for {@link DeviceAction}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 24.04.21
 */
@Repository(DeviceActionRepository.DeviceActionRepositoryImpl.class)
public interface DeviceActionRepository extends CrudRepository<DeviceAction, Long> {

    DeviceAction findByDeviceId(String deviceId);

    @Persistence(UserManagementUnit.class)
    final class DeviceActionRepositoryImpl extends SimpleCrudRepository<DeviceAction, Long> implements DeviceActionRepository {

        @Override
        public DeviceAction findByDeviceId(String deviceId) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<DeviceAction> cq = cb.createQuery(getDomainClass());
            Root<DeviceAction> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(cb.equal(rootEntry.get("deviceId"), deviceId));
            TypedQuery<DeviceAction> query = getEntityManager().createQuery(cq);
            return query.getSingleResult();
        }
    }
}
