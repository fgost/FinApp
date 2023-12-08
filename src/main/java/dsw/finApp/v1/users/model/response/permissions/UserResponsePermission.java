package dsw.finApp.v1.users.model.response.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponsePermission {
    @JsonProperty(value = "key")
    private String permissionKey;

    @JsonProperty(value = "values")
    private List<String> permissionValues;
}
