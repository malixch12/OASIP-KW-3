package sit.oasip.Component;

import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import sit.oasip.entities.User;
import sit.oasip.dtos.JwtResponse;
import sit.oasip.utils.Role;

@Component
public class JwtTokenUtil {
    private String secret;
    private int jwtExpirationInMs;
    private int refreshExpirationDateInMs;

    @Autowired
    private JwtResponse jwtResponse;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${jwt.expirationDateInMinute}")
    public void setExpirationDateInMinuit(int expirationDateInMinute) {
        this.jwtExpirationInMs = expirationDateInMinute * 1000 * 60;
    }

    @Value("${jwt.refreshExpirationDateInMinute}")
    public void refreshExpirationDateInMinuit(int refreshExpirationDateInMinute) {
        this.refreshExpirationDateInMs = refreshExpirationDateInMinute * 1000 * 60;
    }


    public JwtResponse generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails.getRole().equals(Role.Admin.name())) {
            claims.put("roles", Role.Admin.name());
        } else if (userDetails.getRole().equals(Role.Student.name())) {
            claims.put("roles", Role.Student.name());
        } else if (userDetails.getRole().equals(Role.Lecturer.name())) {
            claims.put("roles", Role.Lecturer.name());
        }
        doGenerateToken(claims, userDetails.getEmail(), userDetails.getUserName());
        doGenerateRefreshToken(claims, userDetails.getEmail(), userDetails.getUserName());
        jwtResponse = new JwtResponse(doGenerateToken(claims, userDetails.getEmail(), userDetails.getUserName()), doGenerateRefreshToken(claims, userDetails.getEmail(), userDetails.getUserName()));
        return jwtResponse;
    }

    public String doGenerateToken(Map<String, Object> claims, String subject, String username) {
        claims.put("refresh", false);
        claims.put("username", username);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer("https://intproj21.sit.kmutt.ac.th/kw3/#/").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject, String username) {
        claims.put("refresh", true);
        claims.put("username", username);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer("https://intproj21.sit.kmutt.ac.th/kw3/#/").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public JwtResponse doGenerateAccessToken(String claims, String subject, String username) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("roles", claims);
        claim.put("refresh", false);
        claim.put("username", username);
        String token = Jwts.builder().setClaims(claim).setSubject(subject).setIssuer("https://intproj21.sit.kmutt.ac.th/kw3/#/").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        jwtResponse.setAccessToken(token);
        return jwtResponse;

    }


    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        return Jwts.parser().setSigningKey().parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateToken(String authToken) {

        try {
            final String username = getUsernameFromToken(authToken);
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

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        List<SimpleGrantedAuthority> roles = null;

        String role = claims.get("roles", String.class);

        if (role != null) {
            if (role.equals(Role.Admin.name())) {
                roles = Arrays.asList(new SimpleGrantedAuthority(Role.Admin.name()));
            } else if (role.equals(Role.Student.name())) {
                roles = Arrays.asList(new SimpleGrantedAuthority(Role.Student.name()));
            } else if (role.equals(Role.Lecturer.name())) {
                roles = Arrays.asList(new SimpleGrantedAuthority(Role.Lecturer.name()));
            } else if (role.equals("Guest")) {
                roles = Arrays.asList(new SimpleGrantedAuthority("Guest"));
            }

        }
        return roles;

    }
}