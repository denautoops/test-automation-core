package core.db.persistence;

import core.db.EntityModel;
import core.db.persistence.pool.EntityManagerFactoryPool;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of the {@link CrudRepository} interface. This will offer
 * you a more sophisticated interface than the plain {@link EntityManager} .
 * <p>
 * @param <T> the type of the entity to handle
 * @param <ID> the type of the entity's identifier
 * @author Denis.Martynov
 * Created on 24.04.21
 */
public abstract class SimpleCrudRepository<T extends EntityModel<ID>, ID extends Serializable> implements CrudRepository<T, ID> {

    private final Class<T> domainClass;

    private EntityManager em;

    @SuppressWarnings("unchecked")
    public SimpleCrudRepository() {
        this.domainClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void initEntityManger(PersistenceUnit persistenceUnit) {
        EntityManagerFactory emf = EntityManagerFactoryPool.getOrCreateEmf(persistenceUnit);
        this.em = emf.createEntityManager();
    }

    protected EntityManager getEntityManager() {
        if (em == null)
            throw new IllegalStateException("EntityManagerFactory has not been set on Repository before usage");
        return em;
    }

    public Class<T> getDomainClass() {
        return domainClass;
    }

    public T findById(ID id) {
        T result;
        em.getTransaction().begin();
        try {
            flushAndClear(em);
            result = em.find(domainClass, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        return result;
    }

    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
        Root<T> rootEntry = cq.from(getDomainClass());
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public void save(T entity) {
        em.getTransaction().begin();
        try {
            em.persist(entity);
            flushAndClear(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

    }

    public T merge(T entity) {
        T result;
        em.getTransaction().begin();
        try {
            result = em.merge(entity);
            flushAndClear(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        return result;
    }

    public void delete(T entity) {
        em.getTransaction().begin();
        try {
            flushAndClear(em);
            T managedEntity = em.find(domainClass, entity.getId());
            if (managedEntity == null) {
                em.getTransaction().rollback();
                return;
            }
            em.remove(managedEntity);
            flushAndClear(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public boolean existById(ID id) {
        return Objects.nonNull(findById(id));
    }

    @Override
    public void saveAll(List<T> entities) {
        for (T entity : entities) {
            save(entity);
        }
    }

    @Override
    public void deleteAll(List<T> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }

    @Override
    public long count() {
        return findAll().size();
    }

    private void flushAndClear(EntityManager em) {
        em.flush();
        em.clear();
    }
}
