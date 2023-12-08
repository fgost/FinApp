package dsw.finApp.v1.users.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseSummary {

    @JsonProperty(value = "idExterno")
    private String code;
    private String nomeCompleto;
    @JsonProperty(value = "nome")
    private String name;
    @JsonProperty(value = "sobrenome")
    private String lastName;
    private String email;

}
