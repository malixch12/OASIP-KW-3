package sit.oasip.config;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException() {
        super("Refresh token is expired, please log in again");
    }
}
