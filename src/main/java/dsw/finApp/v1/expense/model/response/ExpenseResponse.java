package dsw.finApp.v1.expense.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private String code;
    @JsonProperty(value = "name")
    private String expenseName;
    @JsonProperty(value = "price")
    private String expensePrice;
    @JsonProperty(value = "type")
    private String expenseType;
}
