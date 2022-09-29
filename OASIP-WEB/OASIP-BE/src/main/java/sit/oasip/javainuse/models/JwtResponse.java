package sit.oasip.javainuse.models;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class JwtResponse{
    private String jwttoken;

    public JwtResponse() {  }
    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}