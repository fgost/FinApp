package dsw.finApp.v1.accesGroup.domain;

import dsw.finApp.v1.profiles.domain.ProfileEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "accessGroups")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccessGroupEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String code;

    private String name;

    @ManyToMany(mappedBy = "accessGroups")
    private Set<ProfileEntity> profiles = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "access_groups_permissions", joinColumns = @JoinColumn(name = "access_group_id"))
    Set<Permission> permissions = new HashSet<>();

    @PrePersist
    private void setCode() {
        this.code = UUID.randomUUID().toString();
    }
}
