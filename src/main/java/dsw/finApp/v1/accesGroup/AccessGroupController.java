package dsw.finApp.v1.accesGroup;

import dsw.finApp.utils.pagination.DefaultWrapper;
import dsw.finApp.utils.pagination.PaginationRequest;
import dsw.finApp.v1.accesGroup.domain.Permission;
import dsw.finApp.v1.accesGroup.model.request.AccessGroupRequestInput;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponse;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponseSummary;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponseWithProfiles;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/finApp/v1/access-group")
public class AccessGroupController {
    private final AccessGroupFacade facade;

    @GetMapping
    public ResponseEntity<DefaultWrapper> findAll(
            @RequestParam(name = "nome", defaultValue = "") String name,
            PaginationRequest paginationRequest) {
        var body = facade.findAll(paginationRequest, name);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessGroupResponse> findByCode(@PathVariable(name = "id") String code) {
        var body = facade.findByCode(code);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<Collection<AccessGroupResponseSummary>> insert(
            @Valid @RequestBody List<AccessGroupRequestInput> input) {
        var body = facade.insert(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessGroupResponseWithProfiles> update(
            @PathVariable(name = "id") String code,
            @Valid @RequestBody AccessGroupRequestInput input) {
        var body = facade.update(code, input);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}/permission")
    public ResponseEntity<Set<Permission>> updatePermissions(
            @PathVariable(name = "id") String code,
            @RequestBody @Valid Set<Permission> permissions) {
        var body = facade.updatePermissions(code, permissions);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String code) {
        facade.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
}
