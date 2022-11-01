package sit.oasip.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.services.JWTUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token

            String jwtToken = extractJwtFromRequest(request);

            if (StringUtils.hasText(jwtToken) == true && jwtTokenUtil.validateToken(jwtToken)) {

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


            } else if(StringUtils.hasText(jwtToken) == false && request.getMethod().equals(HttpMethod.POST.toString())) {
                List<SimpleGrantedAuthority> role = Arrays.asList(new SimpleGrantedAuthority("Guest"));
                UserDetails userDetails = new User("guest", "",role);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
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
        }catch (Exception ex) {
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