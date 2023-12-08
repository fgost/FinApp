package dsw.finApp.config.security;


import dsw.finApp.v1.authentication.services.JwtService;
import dsw.finApp.v1.users.domain.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);

        if(StringUtils.isNotBlank(token)) {
            var username = jwtService.extractUserName(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetail = userDetailsService.loadUserByUsername(username);
                boolean isValid = jwtService.isTokenValid(token, userDetail);

                if (isValid) {
                    authenticateUser(token, userDetail);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(final String token, UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetails;
        var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getToken(HttpServletRequest request) {
        final String token =request.getHeader("Authorization");
        var modifiedToken = extractToken(token);
        if(Objects.isNull(modifiedToken)){
            return null;
        }
        return modifiedToken;
    }

    private String extractToken(String token){
        if (token == null)
            return null;
        return normalizeToken(token).isEmpty() ? null : normalizeToken(token);
    }

    private String normalizeToken(String token) {
        return token.replace("Bearer", "").trim();
    }
}
