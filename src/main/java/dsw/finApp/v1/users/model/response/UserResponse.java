package dsw.finApp.v1.users.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.v1.expense.model.response.ExpenseResponse;
import dsw.finApp.v1.profiles.model.response.ProfileResponseWithAccessGroupsAndPermissions;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("idExterno")
    private String code;
    private String nomeCompleto;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("sobrenome")
    private String lastName;
    private String email;
    @JsonProperty("perfis")
    private Set<ProfileResponseWithAccessGroupsAndPermissions> profiles = new HashSet<>();
    @JsonProperty("expenses")
    private Set<ExpenseResponse> expenses = new HashSet<>();
}
