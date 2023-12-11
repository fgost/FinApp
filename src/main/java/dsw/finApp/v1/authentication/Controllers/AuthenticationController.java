package dsw.finApp.v1.authentication.Controllers;

import dsw.finApp.v1.authentication.model.request.AuthenticationRequestDto;
import dsw.finApp.v1.authentication.model.response.AuthenticationResponseDto;
import dsw.finApp.v1.authentication.services.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finApp/v1/authentication")
@Api(tags = "Authentication Controller")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto requestDto) {
        AuthenticationResponseDto authentication = authenticationService.authenticate(requestDto);
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/check-token")
    public ResponseEntity<String> checkToken() {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.ok("Token válido");
        } else {
            return ResponseEntity.status(401).body("Token inválido");
        }
    }
}
