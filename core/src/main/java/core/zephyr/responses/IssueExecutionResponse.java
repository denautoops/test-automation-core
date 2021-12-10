package core.zephyr.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueExecutionResponse {

    Long id;
    Long cycleId;
    String cycleName;
    Long versionId;
    String versionName;
    Long projectId;
    String projectKey;
    Long issueId;
    String issueKey;
    Long folderId;
    String folderName;
    String executionStatus;

}
