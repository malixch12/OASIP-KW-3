package sit.oasip.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

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