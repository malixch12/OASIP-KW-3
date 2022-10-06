package sit.oasip.javainuse.config;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.server.ResponseStatusException;
//import sit.oasip.Component.JwtTokenUtil;
import org.springframework.web.servlet.HandlerExceptionResolver;
import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.javainuse.models.JwtResponse;
import sit.oasip.javainuse.services.JWTUserDetailsService;
import sit.oasip.repositories.UserRepository;
import sit.oasip.services.AuthenticationService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUserDetailsService jwtUserDetailsService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String jwtToken = extractJwtFromRequest(request);
        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token


            if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {

                UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken), "",
                        jwtTokenUtil.getRolesFromToken(jwtToken));
                String userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
                UserDetails userDetail = this.jwtUserDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                /*
                 After setting the Authentication in the context, we specify
                 that the current user is authenticated. So it passes the
                 Spring Security Configurations successfully.
                 */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                request.setAttribute("message", "Please log in for get Token again.");
                System.out.println("Cannot set the Security Context");

            }
        } catch (ExpiredJwtException ex) {

            String requestURL = request.getRequestURL().toString();

            request.setAttribute("message", "Token is expired");


                if (requestURL.contains("refresh") && ex.getClaims().get("refresh").equals(true)) {
                    if (ex.getClaims().getExpiration().after(new Date(System.currentTimeMillis())) == true) {
                        allowForRefreshToken(ex, request);
                    } else if(ex.getClaims().getExpiration().before(new Date(System.currentTimeMillis())) == true) {

                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Refresh token is expired, please log in");
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
}