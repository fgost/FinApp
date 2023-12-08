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
    @JsonProperty(value = "Name")
    private String expenseName;
    @JsonProperty(value = "Price")
    private String expensePrice;
}
