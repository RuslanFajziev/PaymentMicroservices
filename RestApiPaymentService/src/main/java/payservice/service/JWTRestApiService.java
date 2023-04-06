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

    public String addUser(UserDAO userDAO) {
        var userDAOOptional = findByLogin(userDAO.getLogin());
        String rsl;

        if (userDAOOptional.isEmpty()) {
            userDAO.setPasswordHash(bCryptPasswordEncoder.encode(userDAO.passwordHash));
            repos.save(userDAO);
            rsl = "user successfully added";
        } else {
            rsl = "there is already such a user";
        }
        return rsl;
    }

    public Optional<UserDAO> findByLogin(String login) {
        return repos.findByLogin(login);
    }
}