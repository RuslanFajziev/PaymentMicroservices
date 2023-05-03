package payservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Users_RestApiService")
@Setter
@Getter
@Schema(description = "Сущность пользователя")
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public int id;
    @NotBlank(message = "login must be not empty")
    @Schema(description = "login пользователя", example = "vadim")
    public String login;
    @Column(name = "password_hash")
    @NotBlank(message = "passwordHash must be not empty")
    @Schema(description = "password пользователя", example = "p@$$word")
    public String passwordHash;
    @NotBlank(message = "role must be not empty")
    @Schema(description = "role пользователя", example = "user_full")
    public String role;
    @Column(name = "fio_user")
    @NotBlank(message = "fioUser must be not empty")
    @Schema(description = "ФИО пользователя", example = "Tsoy Vadim Batkovich")
    public String fioUser;

    public static UserDAO of(String login, String passwordHash,
                             String role, String fioUser) {
        var usr = new UserDAO();
        usr.setLogin(login);
        usr.setPasswordHash(passwordHash);
        usr.setRole(role);
        usr.setFioUser(fioUser);
        return usr;
    }
}