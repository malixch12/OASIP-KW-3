package sit.oasip.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import sit.oasip.entities.User;

@Component
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 60;
    private String secret;
    private int jwtExpirationInMs;
    private int refreshExpirationDateInMs;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${jwt.expirationDateInMinuit}")
    public void setExpirationDateInMinuit(int expirationDateInMinuit) {
        this.jwtExpirationInMs = expirationDateInMinuit*1000*60;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public boolean validateToken(String authToken) {

        try {
            final String username = getUsernameFromToken(authToken) ;
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }
    }


    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();

    }

//    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
//        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//
//        List<SimpleGrantedAuthority> roles = null;
//
//        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
//        Boolean isUser = claims.get("isUser", Boolean.class);
//
//        if (isAdmin != null && isAdmin) {
//            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
//
//        if (isUser != null && isAdmin) {
//            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//        }
//        return roles;
//
//    }
}