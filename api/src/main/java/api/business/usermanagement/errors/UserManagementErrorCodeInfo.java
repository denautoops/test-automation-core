package api.business.usermanagement.errors;

import api.business.common.errors.IErrorCodeInfo;

/**
 * User Management Error Code Info
 * <p>
 *
 * @author Denis.Martynov
 * Created on 8.04.21
 */
public enum UserManagementErrorCodeInfo implements IErrorCodeInfo {
    DEFAULT_BAD_REQUEST("BAD_REQUEST", "Not correct request or one of the required fields is empty!");

    private final String errorCde;
    private final String message;

    UserManagementErrorCodeInfo(String errorCde, String message) {
        this.errorCde = errorCde;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return errorCde;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
