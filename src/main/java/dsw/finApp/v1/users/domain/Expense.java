package dsw.finApp.v1.users.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class Expense {

    @NotBlank
    private String expenseName;

    @NotBlank
    private String expensePrice;

    @NotBlank
    private String code;
}
