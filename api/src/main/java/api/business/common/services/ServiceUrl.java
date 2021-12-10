package api.business.common.services;

import api.config.ApiConfiguration;
import api.config.ApiEnvironment;

/**
 * URL's of Services
 * <p>
 *
 * @author Denis.Martynov
 * Created on 12.07.21
 */
public interface ServiceUrl {
    default String userManagementUrl(String uri) {
        ApiEnvironment env = ApiConfiguration.getEnvironment();

        return new StringBuilder().append(env.apiBaseUrl())
                .append(env.apiPrivateBaseUri())
                .append(env.apiUserManagementMappingUri())
                .append(uri)
                .toString();
    }
}
