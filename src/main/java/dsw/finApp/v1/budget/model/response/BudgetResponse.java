package dsw.finApp.v1.budget.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BudgetResponse {
    @JsonProperty(value = "idExterno")
    private String code;
    @JsonProperty(value = "Name")
    private String budgetName;
    @JsonProperty(value = "Limit")
    private String budgetLimit;

}
