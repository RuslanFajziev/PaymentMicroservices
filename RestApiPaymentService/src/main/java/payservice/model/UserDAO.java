package payservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    public static UserDAO of(String username, String password, String roleName, String fioUser) {
        var userDAO = new UserDAO();
        userDAO.setUsername(username);
        userDAO.setPassword(password);
        userDAO.setRole(RoleDAO.of(roleName));
        userDAO.setFioUser(fioUser);
        return userDAO;
    }
}