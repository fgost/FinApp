package dsw.finApp.v1.authentication.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import dsw.finApp.v1.users.domain.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    @Value("${jwt.config.privateKey}")
    private String privateKey;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(privateKey);
    }

    private DecodedJWT getDecodeJwt(String jwt) {
            return JWT.require(getAlgorithm())
                    .build()
                    .verify(jwt);
    }

    public String extractUserName(String token) {
        return getDecodeJwt(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return getDecodeJwt(token).getExpiresAt();
    }

    public Map<String, Claim> extractAllClaims(String token) {
        return getDecodeJwt(token).getClaims();
    }

    public boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    public String createToken(Map<String, Object> claims, UserDetails userDetails) {
        UserEntity user =(UserEntity) userDetails;
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .withHeader(claims)
                .sign(getAlgorithm());
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        UserEntity user =(UserEntity) userDetails;
        final String userName = extractUserName(token);
        return(userName.equals(user.getEmail()) && !isTokenExpired(token));
    }
}
