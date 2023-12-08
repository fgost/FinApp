package dsw.finApp.v1.users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum RequiredPreferences {
    RECORDS_PER_PAGE("recordsPerPage", "24");

    private String name;
    private String defaultValue;

    public static Set<Preference> getRequiredPreference() {
        return Arrays.stream(values())
                .map(requiredPreference -> new Preference(requiredPreference.name, requiredPreference.defaultValue))
                .collect(Collectors.toSet());
    }
}
