package dsw.finApp.v1.accesGroup.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessGroupRequestInput {
    @NotBlank(message = "{accessGroup.description.not.null}")
    @Size(max = 100, message = "{accessGroup.description.max.size}")
    @JsonProperty("nome")
    private String name;
}
