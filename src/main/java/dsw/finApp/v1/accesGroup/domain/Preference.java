package dsw.finApp.v1.accesGroup.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class Preference {
    @JsonProperty("resultsPerPage")
    private Integer resultsPerPage;
}
