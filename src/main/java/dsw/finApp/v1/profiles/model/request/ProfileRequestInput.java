package dsw.finApp.v1.profiles.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestInput {
    @NotBlank(message = "{profile.description.not.null}")
    @Size(max = 100, message = "{profile.description.max.size}")
    @JsonProperty("nome")
    private String name;
}
