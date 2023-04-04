package payservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    public String login;
    public String password;

    @Override
    public String toString() {
        return "UserDTO{"
                + "login='" + login + '\''
                + ", password='" + "******************" + '\''
                + '}';
    }
}