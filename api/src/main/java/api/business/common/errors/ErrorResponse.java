package api.business.common.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Map;

import static core.constants.Global.EMPTY_STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"errorId", "details"})
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    /**
     * The value of this field is not checked,
     * since the server returns a random value
     */
    String errorId;

    String errorCode;
    String message;
    String applicationName;
    Map<String, String> details;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

         private IErrorCodeInfo errorCodeInfo;
         private String applicationName;
         private String arg = EMPTY_STRING;
         private Map<String, String> details = null;


         private Builder() {
         }

        public Builder errorCodeInfo(IErrorCodeInfo errorCodeInfo) {
             this.errorCodeInfo = errorCodeInfo;
             return this;
        }

        public Builder arg(String arg) {
             this.arg = arg;
             return this;
        }

        public Builder applicationName(String applicationName) {
             this.applicationName = applicationName;
             return this;
        }

        public Builder details(Map<String, String> details) {
             this.details = details;
             return this;
        }

        public ErrorResponse build() {
             return new ErrorResponse(
                     null,
                     errorCodeInfo.getErrorCode(),
                     String.format(errorCodeInfo.getMessage(), arg),
                     applicationName,
                     details
             );
        }

    }
}
