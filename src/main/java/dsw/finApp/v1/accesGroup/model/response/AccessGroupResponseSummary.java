package dsw.finApp.v1.accesGroup.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessGroupResponseSummary {
    @JsonProperty("idExterno")
    private String code;

    @JsonProperty("nome")
    private String name;
}
