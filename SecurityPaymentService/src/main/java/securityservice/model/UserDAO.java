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
    @ManyToOne
    @JoinColumn(name = "role_id")
    public RoleDAO role;
    @Column(name = "fio_user")
    public String fioUser;
}