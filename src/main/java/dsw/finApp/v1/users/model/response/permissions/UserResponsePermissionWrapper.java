package dsw.finApp.v1.users.model.response.permissions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponsePermissionWrapper {
    private boolean success;

    private UserResponsePermissionData data;
}
