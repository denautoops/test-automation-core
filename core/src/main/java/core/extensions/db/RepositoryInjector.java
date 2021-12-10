package core.extensions.db;

import core.db.persistence.CrudRepository;
import core.db.persistence.Repository;
import core.db.persistence.RepositoryFactory;
import core.db.persistence.SimpleCrudRepository;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.util.List;

/**
 * JUnit extension for inject repository fields.
 * <p>
 *
 * @author Denis.Martynov
 * Created  on 3/22/2020
 */
public class RepositoryInjector implements TestInstancePostProcessor {

    @Override
    @SuppressWarnings("unchecked")
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {

        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(testInstance.getClass(), InjectRepository.class);

        for (Field field : fields) {
            Class<? extends CrudRepository<?, ?>> fieldClass = (Class<? extends CrudRepository<?, ?>>) field.getType();
            Class<? extends SimpleCrudRepository<?, ?>> repositoryImpl = fieldClass.getAnnotation(Repository.class).value();

            FieldUtils.writeField(field, testInstance, RepositoryFactory.getRepository(repositoryImpl), true);
        }
    }
}
