package dsw.finApp.v1.accesGroup.model.request;

import dsw.finApp.v1.accesGroup.domain.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessGroupRequestInputPermissions {

    private Set<Permission> permissions = new HashSet<>();
}
