package core.db;

import java.io.Serializable;

/**
 * Base Entity Model
 * <p>
 *
 * @author Denis.Martynov
 * Created on 30.08.21
 */
public interface EntityModel<ID> extends Serializable {

    ID getId();

}
