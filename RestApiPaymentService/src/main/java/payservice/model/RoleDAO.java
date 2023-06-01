package payservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDAO roleDAO = (RoleDAO) o;
        return Objects.equals(rolename, roleDAO.rolename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolename);
    }
}