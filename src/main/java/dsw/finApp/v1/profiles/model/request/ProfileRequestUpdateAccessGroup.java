package dsw.finApp.v1.profiles.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProfileRequestUpdateAccessGroup {
    @NotBlank(message = "{accessGroup.code.not.null}")
    @Size(max = 36, min = 36, message = "{size}")
    @JsonProperty("idExternoGrupoAcesso")
    private String code;
}
