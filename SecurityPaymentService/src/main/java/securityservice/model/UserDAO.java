package securityservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Users_RestApiService")
@Setter
@Getter
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String username;
    public String password;
    public String role;
    @Column(name = "fio_user")
    public String fioUser;

    public static UserDAO of(String username, String password,
                             String role, String fioUser) {
        var usr = new UserDAO();
        usr.setUsername(username);
        usr.setPassword(password);
        usr.setRole(role);
        usr.setFioUser(fioUser);
        return usr;
    }
}