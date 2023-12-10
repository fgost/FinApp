package dsw.finApp.v1.users;

import dsw.finApp.config.properties.ApplicationProperties;
import dsw.finApp.utils.dto.OnlyCodeDto;
import dsw.finApp.utils.pagination.DefaultWrapper;
import dsw.finApp.utils.pagination.PaginationRequest;
import dsw.finApp.v1.users.domain.Expense;
import dsw.finApp.v1.users.dto.UserDtoUpdate;
import dsw.finApp.v1.users.model.request.UserRequest;
import dsw.finApp.v1.users.model.response.UserResponse;
import dsw.finApp.v1.users.model.response.UserResponseExpense;
import dsw.finApp.v1.users.model.response.UserResponsePreference;
import dsw.finApp.v1.users.model.response.UserResponseSummary;
import dsw.finApp.v1.users.domain.Preference;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/finApp/v1/users")
@Validated
public class UserController {
    private UserFacade usersFacade;
    private ApplicationProperties properties;

    @GetMapping
    public ResponseEntity<DefaultWrapper> findAll(
            @RequestParam(name = "nome", defaultValue = "") String name,
            @RequestParam(name = "email", defaultValue = "") String email,
            PaginationRequest paginationRequest) {
        var response = usersFacade.findAll(paginationRequest, name, email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findByCode(@PathVariable(name = "id") String code) {
        var response = usersFacade.findByCode(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/preferences")
    public ResponseEntity<UserResponsePreference> getPreferences(@PathVariable(name = "id") String code) {
        var response = usersFacade.getPreferences(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/expenses")
    public ResponseEntity<UserResponseExpense> getExpenses(@PathVariable(name = "id") String code) {
        var response = usersFacade.getExpenses(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/permission")
    public ResponseEntity<DefaultWrapper> getPermissions(
            @PathVariable(name = "id") String code,
            PaginationRequest paginationRequest) {
        var response = usersFacade.getPermissions(code, paginationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<?> getProfilePhotoInfo(@PathVariable(name = "id") String code) {
        var response = usersFacade.getProfilePhotoInfo(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}/image", produces = "image/*")
    public ResponseEntity<?> getProfilePhoto(@PathVariable(name = "id") String code) {
        var response = usersFacade.getProfilePhoto(code);
        if (properties.getStorage().getImpl().isRedirect()) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, response.getUrl())
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(response.getContentType()))
                    .body(new InputStreamResource(response.getInputStream()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseSummary> create(
            @Valid @RequestBody UserRequest usersRequest,
            HttpServletResponse response) {

        var dto = usersFacade.insert(usersRequest);
        URI uri = getURIFor(dto.getCode());
        response.addHeader(HttpHeaders.LOCATION, uri.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseSummary> update(@PathVariable(name = "id") String code,
                                                      @Valid @RequestBody UserDtoUpdate userDtoUpdate) {
        var response = usersFacade.update(code, userDtoUpdate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/preferences")
    public ResponseEntity<UserResponsePreference> updatePreferences(@PathVariable(name = "id") String code,
                                                                    @RequestBody Set<@Valid Preference> preferences) {
        var response = usersFacade.updatePreferences(code, preferences);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/profiles")
    public ResponseEntity<UserResponse> updateProfiles(@PathVariable(name = "id") String code,
                                                       @Valid @RequestBody Set<OnlyCodeDto> ids) {
        var response = usersFacade.updateProfiles(code, ids);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<?> saveImage(@PathVariable(name = "id") String code, MultipartFile file) throws IOException {

        var dto = usersFacade.saveImage(code, file);
        return ResponseEntity
                .ok(dto);
    }

    @PutMapping("/{id}/expense")
    public ResponseEntity<UserResponse> updateExpense(@PathVariable(name = "id") String code,
                                                  @Valid @RequestBody Set<OnlyCodeDto> ids) {
        var response = usersFacade.updateExpense(code, ids);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByCode(@PathVariable(name = "id") String code) {
        usersFacade.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }



    private URI getURIFor(String code) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{code}")
                .buildAndExpand(code)
                .toUri();
    }
}
