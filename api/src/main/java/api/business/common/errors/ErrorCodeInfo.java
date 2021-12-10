package api.business.common.errors;

/**
 * Default Error Code Info
 * <p>
 *
 * @author Denis.Martynov
 * Created on 7.04.21
 */
public enum ErrorCodeInfo implements IErrorCodeInfo{
    INTERNAL_ERROR("INTERNAL_ERROR", "Internal server error"),
    NOT_FOUND("NOT_FOUND", "Not found"),
    INVALID_JWT_TOKEN_FORBIDDEN("INVALID_JWT_TOKEN_FORBIDDEN", "Unauthorized"),
    MISSING_AUTH_HEADER_UNAUTHORIZED("MISSING_AUTH_HEADER_UNAUTHORIZED", "Unauthorized");

    private final String errorCode;
    private final String message;

    ErrorCodeInfo(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
