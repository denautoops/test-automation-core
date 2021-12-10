package core.db.persistence.pool;

import core.db.encryption.EncryptionListener;
import core.db.persistence.PersistenceUnit;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Instance pool for {@link EntityManagerFactory}
 * <p>
 *
 * @author Denis.Martynov
 * Created on 30.04.21
 */
public final class EntityManagerFactoryPool {

    private static volatile Map<String, EntityManagerFactory> emfPool = new LinkedHashMap<>();

    public static synchronized EntityManagerFactory getOrCreateEmf(final PersistenceUnit persistenceUnit) {
        String unitName = persistenceUnit.getUnitName();

        if (!emfPool.containsKey(unitName)) {
            synchronized (EntityManagerFactoryPool.class) {
                if (!emfPool.containsKey(unitName)) {
                    emfPool.put(unitName, createNewEmf(persistenceUnit));
                }
            }
        }
        return emfPool.get(unitName);
    }

    private static synchronized EntityManagerFactory createNewEmf(final PersistenceUnit persistenceUnit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit.getUnitName(), persistenceUnit.getProperties());

        SessionFactoryImplementor sessionFactory = emf.unwrap(SessionFactoryImplementor.class);
        EventListenerRegistry eventListenerRegistry = sessionFactory.getServiceRegistry()
                .getService(EventListenerRegistry.class);

        EncryptionListener encryptionListener = new EncryptionListener();
        eventListenerRegistry.appendListeners(EventType.PRE_LOAD, encryptionListener);
        eventListenerRegistry.appendListeners(EventType.PRE_INSERT, encryptionListener);
        eventListenerRegistry.appendListeners(EventType.PRE_UPDATE, encryptionListener);
        return emf;
    }

}
