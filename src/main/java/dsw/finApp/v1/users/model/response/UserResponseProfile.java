package dsw.finApp.v1.users.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.profiles.model.response.ProfileResponseSummary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseProfile {
    @JsonProperty(value = "usuarioIdExterno")
    private String code;
    @JsonProperty(value = "usuarioNomeCompleto")
    private String fullName;
    @JsonProperty(value = "perfis")
    private Set<ProfileResponseSummary> profiles = new HashSet<>();
}
