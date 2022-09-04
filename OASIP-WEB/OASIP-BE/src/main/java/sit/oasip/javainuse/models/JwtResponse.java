package sit.oasip.javainuse.models;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class JwtResponse{

    private String message;
    private String jwttoken;

    public JwtResponse(String message) {
        this.message = message;
    }

    public JwtResponse(String message, String jwttoken) {
        this.message = message;
        this.jwttoken = jwttoken;
    }
}