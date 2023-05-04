package payservice.repository;

import org.springframework.data.repository.CrudRepository;
import payservice.model.UserDAO;

import java.util.Optional;

public interface JWTRestApiRepos extends CrudRepository<UserDAO, Integer> {
    Optional<UserDAO> findByUsername(String username);
}