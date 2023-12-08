package dsw.finApp.v1.profiles.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    @JsonProperty("idExterno")
    private String code;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("gruposAcesso")
    private Set<AccessGroupResponse> accessGroups = new HashSet<>();
}
