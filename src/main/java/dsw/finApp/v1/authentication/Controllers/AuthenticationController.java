package dsw.finApp.v1.authentication.Controllers;

import dsw.finApp.v1.authentication.model.request.AuthenticationRequestDto;
import dsw.finApp.v1.authentication.model.response.AuthenticationResponseDto;
import dsw.finApp.v1.authentication.services.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
