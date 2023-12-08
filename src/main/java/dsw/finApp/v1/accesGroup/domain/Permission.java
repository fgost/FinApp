package dsw.finApp.v1.accesGroup.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "key")
    @NotBlank
    private String permissionKey;

    @JsonProperty(value = "value")
    @NotBlank
    private String permissionValue;
}
