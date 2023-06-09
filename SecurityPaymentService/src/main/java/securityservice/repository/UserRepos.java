package securityservice.repository;

import org.springframework.data.repository.CrudRepository;
import securityservice.model.RoleDAO;
import securityservice.model.UserDAO;

import java.util.Optional;

public interface UserRepos extends CrudRepository<UserDAO, Integer> {
    Optional<UserDAO> findByUsername(String username);

    int countAllByUsernameAndIdNot(String username, int id);

    int countAllByRole(RoleDAO roleDAO);
}