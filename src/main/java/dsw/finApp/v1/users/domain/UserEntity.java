package dsw.finApp.v1.users.domain;

import dsw.finApp.v1.expense.domain.ExpenseEntity;
import dsw.finApp.v1.profiles.domain.ProfileEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
//@AuditTable(value = "users_audit")
//@Audited
public class UserEntity implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String lastName;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_preferences", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Preference> preferences = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "users_profiles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private List<ProfileEntity> profiles;

    @ManyToMany
    @JoinTable(
            name = "users_expense",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_id")
    )
    private List<ExpenseEntity> expense;

    @PrePersist
    private void setCode() {
        this.code = UUID.randomUUID().toString();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Transient
    public String getNomeCompleto() {
        return this.name.trim() + " " + this.lastName.trim();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Map<String, String> getPreferencesAsMap() {
        Map<String, String> map = new HashMap<>();
        preferences.forEach(p -> {
            map.put(p.getPreferenceKey(), p.getPreferenceValue());
        });
        return map;
    }

    public Map<String, Double> getExpenseAsMap(){
        Map<String, Double> map = new HashMap<>();
        expense.forEach(p -> {
            map.put(p.getExpenseName(),p.getExpensePrice());
        });
        return map;
    }
}
