package dsw.finApp.v1.users.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.users.domain.Preference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponsePreference {
    @JsonProperty(value = "usuarioNomeCompleto")
    private String fullName;

    @JsonProperty(value = "preferencias")
    private Set<Preference> preferences;
}
