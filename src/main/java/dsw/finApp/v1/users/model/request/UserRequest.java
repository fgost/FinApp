package dsw.finApp.v1.users.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "{user.name.not.null}")
    @Size(max = 100, message = "{user.name.max.size}")
    @JsonProperty(value = "nome")
    private String name;

    @NotBlank(message = "{user.last.name.not.null}")
    @Size(max = 100, message = "{user.lastName.max.size}")
    @JsonProperty(value = "sobrenome")
    private String lastName;

    @NotBlank(message = "{user.email.not.null}")
    @Size(max = 100, message = "{user.email.max.size}")
    private String email;

    @NotBlank(message = "{user.password.not.null}")
    @Size(max = 100, message = "{user.password.max.size}")
    @JsonProperty(value = "senha")
    private String password;

}
