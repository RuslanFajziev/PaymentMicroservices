package payservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserJWT {
    public String username;
    public String role;
}