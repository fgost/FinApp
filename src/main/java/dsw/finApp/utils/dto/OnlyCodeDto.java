package dsw.finApp.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OnlyCodeDto {
    @NotBlank(message = "{not.blank}")
    @Size(max = 36, min = 36, message = "{size}")
    @JsonProperty("idExterno")
    private String code;
}
