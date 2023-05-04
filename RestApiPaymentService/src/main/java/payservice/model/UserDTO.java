package payservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Сущность пользователя")
public class UserDTO {
    @Schema(description = "username пользователя", example = "vadim")
    public String username;
    @Schema(description = "password пользователя", example = "p@$$word")
    public String password;

    @Override
    public String toString() {
        return "UserDTO{"
                + "username='" + username + '\''
                + ", password='" + "******************" + '\''
                + '}';
    }
}