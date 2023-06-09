package payservice.repository;

import org.springframework.data.repository.CrudRepository;
import payservice.model.RoleDAO;

import java.util.Optional;

public interface RolesRestApiRepos extends CrudRepository<RoleDAO, Integer> {
    Optional<RoleDAO> findByRolename(String role);

    int countAllByRolenameAndIdNot(String rolename, int id);
}