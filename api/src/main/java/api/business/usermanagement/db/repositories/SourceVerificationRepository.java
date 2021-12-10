package api.business.usermanagement.db.repositories;

import api.business.usermanagement.db.UserManagementUnit;
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
 * Repository for {@link SourceVerification}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 13.07.21
 */
@Repository(SourceVerificationRepository.SourceVerificationRepositoryImpl.class)
public interface SourceVerificationRepository extends CrudRepository<SourceVerification, Long> {

    SourceVerification findBySourceValueAndDeviceIdAndType(String sourceValue, String deviceId, SourceVerificationType type);
    SourceVerification findBySourceValueAndType(String sourceValue, SourceVerificationType type);

    @Persistence(UserManagementUnit.class)
    final class SourceVerificationRepositoryImpl extends SimpleCrudRepository<SourceVerification, Long>
            implements SourceVerificationRepository {
        @Override
        public SourceVerification findBySourceValueAndDeviceIdAndType(
                String sourceValue, String deviceId, SourceVerificationType type)
        {
            String encryptedSourceValue = Encrypter.encrypt(sourceValue);

            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<SourceVerification> cq = cb.createQuery(getDomainClass());
            Root<SourceVerification> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(
                            cb.and(
                                    cb.equal(rootEntry.get("sourceValue"), encryptedSourceValue),
                                    cb.equal(rootEntry.get("deviceId"), deviceId),
                                    cb.equal(rootEntry.get("type"), type)
                            )
                    );
            TypedQuery<SourceVerification> query = getEntityManager().createQuery(cq);
            return query.getSingleResult();
        }

        @Override
        public SourceVerification findBySourceValueAndType(
                String sourceValue, SourceVerificationType type)
        {
            String encryptedSourceValue = Encrypter.encrypt(sourceValue);

            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<SourceVerification> cq = cb.createQuery(getDomainClass());
            Root<SourceVerification> rootEntry = cq.from(getDomainClass());
            cq.select(rootEntry)
                    .where(
                            cb.and(
                                    cb.equal(rootEntry.get("sourceValue"), encryptedSourceValue),
                                    cb.equal(rootEntry.get("type"), type)
                            )
                    );
            TypedQuery<SourceVerification> query = getEntityManager().createQuery(cq);
            return query.getSingleResult();
        }
    }
}