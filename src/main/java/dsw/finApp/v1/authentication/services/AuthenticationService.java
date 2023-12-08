package dsw.finApp.v1.authentication.services;

import dsw.finApp.v1.authentication.model.request.AuthenticationRequestDto;
import dsw.finApp.v1.authentication.model.response.AuthenticationResponseDto;
import dsw.finApp.v1.users.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationResponseDto authenticate (AuthenticationRequestDto request) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user =(UserEntity) auth.getPrincipal();
        var jwtToken =jwtService.generateToken(user);
        return new AuthenticationResponseDto(jwtToken);
    }

    public UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
