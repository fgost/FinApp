package dsw.finApp.v1.profiles.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponseWithPermissions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseWithAccessGroupsAndPermissions {
    @JsonProperty("idExterno")
    private String code;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("gruposAcesso")
    private Set<AccessGroupResponseWithPermissions> accessGroups = new HashSet<>();
}
