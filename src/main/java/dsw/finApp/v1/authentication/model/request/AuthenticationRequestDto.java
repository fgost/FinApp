package dsw.finApp.v1.authentication.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequestDto {
    private String email;

    @JsonProperty(value = "senha")
    private String password;
}
