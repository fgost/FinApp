package dsw.finApp.v1.expense.model.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
    @ApiModelProperty(value = "expense name", required = true, dataType = "java.lang.String")
    @NotBlank(message = "{expense.name.not.null}")
    @Size(max = 100, message = "{expense.name.max.size}")
    private String expenseName;

    @ApiModelProperty(value = "Expense Price", required = true, dataType = "java.lang.Double")
    private double expensePrice;
}
