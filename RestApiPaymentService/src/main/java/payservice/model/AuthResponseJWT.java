package payservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponseJWT {
    public String username;
    public String accessToken;
}