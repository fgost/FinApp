package dsw.finApp.v1.users.model.response.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsw.finApp.utils.pagination.CustomPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserResponsePermissionData {
    @JsonProperty(value = "itens")
    private List<UserResponsePermission> items;

    @JsonProperty(value = "paginacao")
    private CustomPage<?> pagination;
}
