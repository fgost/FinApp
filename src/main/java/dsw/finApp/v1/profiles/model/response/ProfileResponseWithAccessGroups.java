package dsw.finApp.v1.profiles.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponseSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseWithAccessGroups {
    @JsonProperty("idExterno")
    private String code;

    @JsonProperty("nome")
    private String name;

    @Builder.Default
    @JsonProperty("gruposAcesso")
    private Set<AccessGroupResponseSummary> accessGroups = new HashSet<>();
}
