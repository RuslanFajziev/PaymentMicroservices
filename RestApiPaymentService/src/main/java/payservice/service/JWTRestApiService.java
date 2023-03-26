package payservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import payservice.model.UserDAO;
import payservice.repository.JWTRestApiRepos;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class JWTRestApiService {
    private final JWTRestApiRepos repos;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDAO addUser(UserDAO userDAO) {
        var userDAOOptional = findByLogin(userDAO.getLogin());
        UserDAO rsl;

        if (userDAOOptional.isEmpty()) {
            userDAO.setPasswordHash(bCryptPasswordEncoder.encode(userDAO.passwordHash));
            rsl = repos.save(userDAO);
        } else {
            rsl = userDAOOptional.get();
        }

        return rsl;
    }

    public Optional<UserDAO> findByLogin(String login) {
        return repos.findByLogin(login);
    }
}