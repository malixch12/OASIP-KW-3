package sit.oasip.javainuse.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sit.oasip.javainuse.models.JwtResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException authException) throws IOException {
        String errorMsg = (String) request.getAttribute("message");
    if (errorMsg != null){
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,errorMsg);
        response.getOutputStream().println(errorMsg);
    }else {
        response.sendError(HttpServletResponse.SC_FORBIDDEN,"No permission");
    }


    }

}