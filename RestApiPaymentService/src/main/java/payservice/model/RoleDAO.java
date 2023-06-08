package payservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
@Table(name = "Roles_RestApiService")
@Schema(description = "Сущность импортируемой роли")
public class RoleDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public int id;
    @NotBlank(message = "rolename must be not empty")
    @Schema(description = "rolename пользователя", example = "user_full")
    public String rolename;

    public static RoleDAO of(String roleName) {
        var roleDAO = new RoleDAO();
        roleDAO.setRolename(roleName);
        return roleDAO;
    }
}