package securityservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    public String username;
    public String password;

    @Override
    public String toString() {
        return "UserDTO{"
                + "username='" + username + '\''
                + ", password='" + "******************" + '\''
                + '}';
    }
}