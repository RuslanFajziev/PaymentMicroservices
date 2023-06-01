package payservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "Users_RestApiService")
@Setter
@Getter
@Schema(description = "Сущность импортируемого пользователя")
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public int id;
    @NotBlank(message = "username must be not empty")
    @Schema(description = "username пользователя", example = "vadim")
    public String username;
    @NotBlank(message = "password must be not empty")
    @Schema(description = "password пользователя", example = "p@$$word")
    public String password;
    @NotNull(message = "role must be not empty")
    @Schema(description = "role пользователя", example = "rolename : user_full")
    @ManyToOne
    @JoinColumn(name = "role_id")
    public RoleDAO role;
    @Column(name = "fio_user")
    @NotBlank(message = "fioUser must be not empty")
    @Schema(description = "ФИО пользователя", example = "Tsoy Vadim Batkovich")
    public String fioUser;

    public static UserDAO of(String username, String password,
                             RoleDAO role, String fioUser) {
        var usr = new UserDAO();
        usr.setUsername(username);
        usr.setPassword(password);
        usr.setRole(role);
        usr.setFioUser(fioUser);
        return usr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDAO userDAO = (UserDAO) o;
        return Objects.equals(username, userDAO.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}