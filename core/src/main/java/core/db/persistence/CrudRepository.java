package core.db.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * <p>
 *
 * @author Denis.Martynov
 * Created on 24.04.21
 */
public interface CrudRepository<T, ID extends Serializable> {

    void save(T entity);

    T merge(T entity);

    void saveAll(List<T> entities);

    T findById(ID id);

    List<T> findAll();

    void delete(T entity);

    void deleteAll(List<T> entities);

    void deleteById(ID id);

    boolean existById(ID id);

    long count();

}
