package dsw.finApp.v1.profiles;

import dsw.finApp.utils.dto.OnlyCodeDto;
import dsw.finApp.utils.pagination.DefaultWrapper;
import dsw.finApp.utils.pagination.PaginationRequest;
import dsw.finApp.v1.profiles.model.request.ProfileRequestInput;
import dsw.finApp.v1.profiles.model.response.ProfileResponseSummary;
import dsw.finApp.v1.profiles.model.response.ProfileResponseWithAccessGroups;
import dsw.finApp.v1.profiles.model.response.ProfileResponseWithAccessGroupsAndPermissions;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/infoApp/v1/profile")
public class ProfileController {
    private final ProfileFacade facade;

    @GetMapping
    public ResponseEntity<DefaultWrapper> findAll(
            @RequestParam(name = "nome", defaultValue = "") String name,
            PaginationRequest paginationRequest) {
        var body = facade.findAll(paginationRequest, name);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseWithAccessGroupsAndPermissions> findByCode(
            @PathVariable(name = "id") String code) {
        var body = facade.profileResponseWithAccessGroupsAndPermissions(code);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<ProfileResponseSummary> create(@Valid @RequestBody ProfileRequestInput input) {
        var body = facade.insert(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseWithAccessGroups> update(
            @PathVariable(name = "id") String code,
            @RequestBody @Valid ProfileRequestInput input) {
        var body = facade.update(code, input);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}/access-group")
    @ResponseStatus(value = HttpStatus.OK)
    public ProfileResponseWithAccessGroupsAndPermissions updateProfileAccessGroups(
            @PathVariable(name = "id") String code,
            @Valid @RequestBody List<OnlyCodeDto> inputList) {
        return facade.updateProfileAccessGroups(code, inputList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String code) {
        facade.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
}
