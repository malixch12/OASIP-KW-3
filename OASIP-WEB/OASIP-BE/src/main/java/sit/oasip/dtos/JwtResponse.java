package sit.oasip.dtos;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class JwtResponse{
    private String accessToken;
    private String refreshToken;

    public JwtResponse() {
    }

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}