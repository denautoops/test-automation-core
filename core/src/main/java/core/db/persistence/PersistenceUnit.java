package core.db.persistence;

import java.util.Properties;

/**
 * Interface for Persistence Unit information
 * <p>
 *
 * @author Denis.Martynov
 * Created  on 3/22/2020
 */
public interface PersistenceUnit {

    Properties getProperties();

    String getUnitName();

    default String getUrlPattern() {
        return "jdbc:postgresql://%s/%s?" +
                "allowPublicKeyRetrieval=true&" +
                "useSSL=false&" +
                "useUnicode=true&" +
                "characterEncoding=UTF-8&" +
                "serverTimezone=UTC&" +
                "stringtype=unspecified";
    }

}
