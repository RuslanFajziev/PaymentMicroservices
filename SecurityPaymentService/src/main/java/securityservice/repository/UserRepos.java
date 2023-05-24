package securityservice.repository;

import org.springframework.data.repository.CrudRepository;
import securityservice.model.UserDAO;

import java.util.List;
import java.util.Optional;

public interface UserRepos extends CrudRepository<UserDAO, Integer> {
    Optional<UserDAO> findByUsername(String username);

    List<UserDAO> findByUsernameAndIdNot(String username, int id);

    Integer countAllByUsernameAndIdNot(String username, int id);
}