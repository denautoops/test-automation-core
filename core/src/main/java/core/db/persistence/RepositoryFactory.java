package core.db.persistence;

import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * Factory for create generic repository
 * <p>
 *
 * @author Denis.Martynov
 * Created on 24.04.21
 */
public class RepositoryFactory {

    @SuppressWarnings("unchecked")
    public static synchronized <R extends CrudRepository<?, ?>, RImpl extends SimpleCrudRepository<?, ?>> R getRepository(Class<RImpl> rImplClass) throws Exception {
        RImpl rImplInstance = instantiateRepository(rImplClass);
        return ((R) rImplInstance);
    }

    private static synchronized <RImpl extends SimpleCrudRepository<?, ?>> RImpl instantiateRepository(Class<RImpl> rImplClass) throws Exception {
        RImpl rImplInstance;
        if (rImplClass.isAnnotationPresent(Persistence.class)) {
            Persistence persistence = rImplClass.getAnnotation(Persistence.class);
            PersistenceUnit persistenceUnit = ConstructorUtils.invokeConstructor(persistence.value());
            rImplInstance = ConstructorUtils.invokeConstructor(rImplClass);
            rImplInstance.initEntityManger(persistenceUnit);
        } else {
            throw new RuntimeException(String.format("Class '%s' doesn't have 'Persistence' annotation!", rImplClass.getSimpleName()));
        }
        return rImplInstance;
    }

}
