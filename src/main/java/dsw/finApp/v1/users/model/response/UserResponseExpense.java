package dsw.finApp.v1.users.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.users.domain.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseExpense {
    @JsonProperty(value = "user")
    private String fullName;

    private Set<Expense> expense;
}
