package core.extensions.db;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for inject repository
 * <p>
 *
 * @author Denis.Martynov
 * Created  on 3/22/2020
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface InjectRepository {
}
