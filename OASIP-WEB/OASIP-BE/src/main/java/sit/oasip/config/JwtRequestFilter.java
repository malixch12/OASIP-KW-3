package sit.oasip.config;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.UserNamePasswordParameters;
import io.jsonwebtoken.*;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.IOException;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.services.JWTUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private HttpSecurity security;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            System.out.println("----- RUN JWT REQUEST FILTER ------");

            String jwtToken = extractJwtFromRequest(request);
            JSONObject payload = extractMSJwt(jwtToken, security);

            if (jwtToken == null &&
                    (request.getMethod().equals(HttpMethod.GET.toString()) && request.getRequestURL().toString().contains("events") ||
                            request.getMethod().equals(HttpMethod.GET.toString()) && request.getRequestURL().toString().contains("eventcategorys"))) {
                System.out.println("FOR GUEST ROLE");
                List<SimpleGrantedAuthority> role = Arrays.asList(new SimpleGrantedAuthority("c"));
                UserDetails userDetails = new User("guest", "", role);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

            if (StringUtils.hasText(jwtToken) == true && payload.getString("iss").equals("https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d/v2.0")) {
                String role = payload.getString("roles");
                String extract = role.replaceAll("[^a-zA-Z]+", "");
                System.out.println("MSIP");
                jwtToken = jwtTokenUtil.doGenerateAccessToken(extract, payload.getString("sub")).getAccessToken();
            }

            if (StringUtils.hasText(jwtToken) == true && jwtTokenUtil.validateToken(jwtToken)) {
                UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken), "",
                        jwtTokenUtil.getRolesFromToken(jwtToken));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                System.out.println(usernamePasswordAuthenticationToken);
                /*
                 After setting the Authentication in the context, we specify
                 that the current user is authenticated. So it passes the
                 Spring Security Configurations successfully.
                 */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                System.out.println("Please log in for get Token again.");
                request.setAttribute("message", "Please log in for get Token again.");
            }


        } catch (ExpiredJwtException ex) {

            String requestURL = request.getRequestURL().toString();
            request.setAttribute("message", "Token is expired");


            if (requestURL.contains("refresh") && ex.getClaims().get("refresh").equals(true)) {
                if (ex.getClaims().getExpiration().after(new Date(System.currentTimeMillis())) == true) {
                    allowForRefreshToken(ex, request);
                } else if (ex.getClaims().getExpiration().before(new Date(System.currentTimeMillis())) == true) {

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token is expired, please log in");
                    return;
                } else {
                    request.setAttribute("exception", ex);
                }
            }

        } catch (BadCredentialsException ex) {
            request.setAttribute("message", "Token incorrect");
            request.setAttribute("exception", ex);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        chain.doFilter(request, response);
    }


    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        /*
         After setting the Authentication in the context, we specify
         that the current user is authenticated. So it passes the
         Spring Security Configurations successfully.
         */
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        // Set the claims so that in controller we will be using it to create new JWT
        request.setAttribute("claims", ex.getClaims());

    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        request.setAttribute("message", "Please check your header key is Authorization and header value start with Bearer");
        return null;
    }

    @SneakyThrows
    public JSONObject extractMSJwt(String token, HttpSecurity security) {
        String[] chunks = token.split("\\.");

        JSONObject header = new JSONObject(decode(chunks[0]));
        JSONObject payload = new JSONObject(decode(chunks[1]));
        String signature = decode(chunks[2]);
        if (payload.getString("iss").equals("https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d/v2.0")) {
            System.out.println("BEFORE CONFIG");

            DecodedJWT jwt = JWT.decode(token);
            JwkProvider provider = new UrlJwkProvider(new URL("https://login.microsoftonline.com/common/discovery/keys"));
            Jwk jwk = provider.get(jwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);

            System.out.println("AFTER CONFIG");
        }
        System.out.println("PAYLOAD : " + payload);
        return payload;
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }


}
