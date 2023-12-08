package dsw.finApp.v1.budget.model.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {

    @ApiModelProperty(value = "Budget name", required = true, dataType = "java.lang.String")
    @NotBlank(message = "{budget.name.not.null}")
    @Size(max = 100, message = "{budget.name.max.size}")
    private String budgetName;

    @ApiModelProperty(value = "Budget Limit", required = true, dataType = "java.lang.Double")
    private double budgetLimit;
}
