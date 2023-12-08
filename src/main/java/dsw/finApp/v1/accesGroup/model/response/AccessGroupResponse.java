package dsw.finApp.v1.accesGroup.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.accesGroup.domain.Permission;
import dsw.finApp.v1.profiles.model.response.ProfileResponseSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessGroupResponse {
    @JsonProperty("idExterno")
    private String code;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("perfis")
    Set<ProfileResponseSummary> profiles = new HashSet<>();

    @JsonProperty("permissoes")
    Set<Permission> permissions = new HashSet<>();
}
