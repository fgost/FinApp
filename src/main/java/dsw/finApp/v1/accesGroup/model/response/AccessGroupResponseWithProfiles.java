package dsw.finApp.v1.accesGroup.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.profiles.model.response.ProfileResponseSummary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessGroupResponseWithProfiles {

    @JsonProperty(value = "idExterno")
    private String code;

    @JsonProperty(value = "nome")
    private String name;

    @JsonProperty(value = "perfis")
    private List<ProfileResponseSummary> profiles = new ArrayList<>();
}
