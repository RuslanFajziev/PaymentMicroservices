package securityservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "Roles_RestApiService")
public class RoleDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String rolename;

    public static RoleDAO of(String rolename) {
        var roleDAO = new RoleDAO();
        roleDAO.setRolename(rolename);
        return roleDAO;
    }
}