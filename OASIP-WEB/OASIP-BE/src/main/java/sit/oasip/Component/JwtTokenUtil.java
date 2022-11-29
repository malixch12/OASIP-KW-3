package sit.oasip.Component;

import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import sit.oasip.entities.User;
import sit.oasip.dtos.JwtResponse;
import sit.oasip.utils.Role;

import javax.crypto.spec.SecretKeySpec;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

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
            claims.put("role", Role.Admin.name());
        } else if (userDetails.getRole().equals(Role.Student.name())) {
            claims.put("role", Role.Student.name());
        } else if (userDetails.getRole().equals(Role.Lecturer.name())) {
            claims.put("role", Role.Lecturer.name());
        }
        doGenerateToken(claims, userDetails.getEmail());
        doGenerateRefreshToken(claims, userDetails.getEmail());
        jwtResponse = new JwtResponse(doGenerateToken(claims, userDetails.getEmail()), doGenerateRefreshToken(claims, userDetails.getEmail()));
        return jwtResponse;
    }

    public String doGenerateToken(Map<String, Object> claims, String subject) {
        claims.put("refresh", false);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
//        Map<String, Object> claim = new HashMap<>();
//        claim.put("role",claims);
        claims.put("refresh", true);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer("http://localhost:8080/api/login").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public JwtResponse doGenerateAccessToken(String claims, String subject) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("role", claims);
        claim.put("refresh", false);
        String token = Jwts.builder().setClaims(claim).setSubject(subject).setIssuer("http://localhost:8080/api/login").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        jwtResponse.setAccessToken(token);
        return jwtResponse;

    }


    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
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

//    String role = null;
//    String username = null;

    public boolean validateToken(String authToken) throws Exception {

        try {
//            String[] chunks = authToken.split("\\.");
//            Base64.Decoder decoder = Base64.getUrlDecoder();
//
//            String header = new String(decoder.decode(chunks[0]));
//            String payload = new String(decoder.decode(chunks[1]));
//            String signature = chunks[2];
//            SignatureAlgorithm sa = HS256;
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), sa.getJcaName());
//            String tokenWithoutSignature = chunks[0] + "." + chunks[1];
//
//            DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);
//            System.out.println("----header---");
//            System.out.println(header);
//            System.out.println("----payload---");
//            System.out.println(payload);
//            System.out.println("----secret---");
//            System.out.println(secretKeySpec);
//            System.out.println("----validator---");
//            System.out.println(validator);
//
//            String[] payloads = payload.split(",");
//            if (payloads[11].contains("Admin")) {
//                role = "Admin";
//            }
//            System.out.println("--role--");
//            System.out.println(role);
//
//            int index = payloads[9].indexOf(":");
//            username = payloads[9].substring(index+2);
//            System.out.println(username);

//            if (!validator.isValid(tokenWithoutSignature, signature)) {
//                throw new Exception("Could not verify JWT token integrity!");
//            }
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

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        List<SimpleGrantedAuthority> roles = null;

        String role = claims.get("role", String.class);

        if (role != null) {
            if (role.equals(Role.Admin.name())) {
                roles = Arrays.asList(new SimpleGrantedAuthority(Role.Admin.name()));
            } else if (role.equals(Role.Student.name())) {
                roles = Arrays.asList(new SimpleGrantedAuthority(Role.Student.name()));
            } else if (role.equals(Role.Lecturer.name())) {
                roles = Arrays.asList(new SimpleGrantedAuthority(Role.Lecturer.name()));
            }

        }
        return roles;

    }
}