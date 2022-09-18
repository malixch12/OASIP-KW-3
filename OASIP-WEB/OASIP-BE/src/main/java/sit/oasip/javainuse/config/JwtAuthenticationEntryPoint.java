package sit.oasip.javainuse.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sit.oasip.javainuse.models.JwtResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException authException) throws IOException {

        String message = (String) request.getAttribute("message");
//        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "message");

        response.setStatus(401);
        response.getOutputStream().println(message);


    }
}