package api.business.usermanagement.db;

import api.config.ApiConfiguration;
import api.config.PersistenceProperties;
import core.db.persistence.PersistenceUnit;
import org.hibernate.cfg.AvailableSettings;

import java.util.Properties;

/**
 * Persistence unit for User Management DB
 * <p>
 *
 * @author Denis.Martynov
 * Created on 23.04.21
 */
public class UserManagementUnit implements PersistenceUnit {

    PersistenceProperties PERSISTENCE = ApiConfiguration.getPersistenceProperties();

    public String getUnitName() {
        return PERSISTENCE.userManagementUnitName();
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.URL,
                String.format(getUrlPattern(), PERSISTENCE.dbServer(), PERSISTENCE.userManagementDbName()));
        properties.setProperty(AvailableSettings.USER, PERSISTENCE.dbUser());
        properties.setProperty(AvailableSettings.PASS, PERSISTENCE.dbPassword());
        return properties;
    }
}
