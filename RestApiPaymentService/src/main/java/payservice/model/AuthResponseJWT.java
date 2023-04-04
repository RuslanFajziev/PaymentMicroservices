package payservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponseJWT {
    public String login;
    public String accessToken;
}