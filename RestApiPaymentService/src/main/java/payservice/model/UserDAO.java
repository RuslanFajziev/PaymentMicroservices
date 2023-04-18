package payservice.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Users_RestApiService")
@Setter
@Getter
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @NotBlank(message = "login must be not empty")
    public String login;
    @Column(name = "password_hash")
    @NotBlank(message = "passwordHash must be not empty")
    public String passwordHash;
    @NotBlank(message = "role must be not empty")
    public String role;
    @Column(name = "fio_user")
    @NotBlank(message = "fioUser must be not empty")
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