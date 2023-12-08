package dsw.finApp.v1.accesGroup.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.accesGroup.domain.Permission;
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
public class AccessGroupResponseWithPermissions {
    @JsonProperty("idExterno")
    private String code;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("permissoes")
    Set<Permission> permissions = new HashSet<>();
}
