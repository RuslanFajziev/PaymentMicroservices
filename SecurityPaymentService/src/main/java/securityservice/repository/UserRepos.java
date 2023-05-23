package securityservice.repository;

import org.springframework.data.repository.CrudRepository;
import securityservice.model.UserDAO;

import java.util.Optional;

public interface UserRepos extends CrudRepository<UserDAO, Integer> {
}