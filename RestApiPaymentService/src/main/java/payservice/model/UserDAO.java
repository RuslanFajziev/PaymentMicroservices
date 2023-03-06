package payservice.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Users_RestApiService")
@Setter
@Getter
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String login;
    @Column (name = "password_hash")
    public String passwordHash;
    public String role;
    @Column (name = "fio_user")
    public String fioUser;
}