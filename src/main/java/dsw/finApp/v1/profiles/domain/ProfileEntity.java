package dsw.finApp.v1.profiles.domain;

import dsw.finApp.v1.accesGroup.domain.AccessGroupEntity;
import dsw.finApp.v1.users.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "profiles")
public class ProfileEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @ManyToMany(mappedBy = "profiles")
    private List<UserEntity> users;

    @ManyToMany
    @JoinTable(name="profiles_access_groups", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "access_group_id"))
    @Builder.Default
    private Set<AccessGroupEntity> accessGroups = new HashSet<>();

    @PrePersist
    private void setCode() {
        this.code = UUID.randomUUID().toString();
    }

}
