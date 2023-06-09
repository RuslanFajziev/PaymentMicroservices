package securityservice.repository;

import org.springframework.data.repository.CrudRepository;
import securityservice.model.RoleDAO;

import java.util.Optional;

public interface RolesRepos extends CrudRepository<RoleDAO, Integer> {
    Optional<RoleDAO> findByRolename(String role);

    int countAllByRolenameAndIdNot(String rolename, int id);
}