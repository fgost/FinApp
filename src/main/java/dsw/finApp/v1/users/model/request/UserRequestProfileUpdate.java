package dsw.finApp.v1.users.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestProfileUpdate {
    @JsonProperty(value = "perfilIdExterno")
    @NotBlank
    private String code;
}
