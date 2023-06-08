package payservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import payservice.model.RoleDAO;
import payservice.model.UserDAO;
import payservice.repository.JWTRestApiRepos;
import payservice.repository.RolesRestApiRepos;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class JWTRestApiService {
    private final JWTRestApiRepos repos;
    private final RolesRestApiRepos reposRole;

    private final RoleRestApiService serviceRole;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String addUser(UserDAO userDAO, RoleDAO roleDAO) {
        if (userDAO.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password is too short < 8");
        }

        var userDAOOptional = findByLogin(userDAO.getUsername());
        String rsl;

        serviceRole.addRole(roleDAO);
        var optionalRoleDAO = reposRole.findByRolename(roleDAO.getRolename());
        if (optionalRoleDAO.isEmpty()) return "role search error";

        if (userDAOOptional.isEmpty()) {
            userDAO.setPassword(bCryptPasswordEncoder.encode(userDAO.password));
            userDAO.setRole(optionalRoleDAO.get());
            repos.save(userDAO);
            rsl = "user successfully added";
        } else {
            rsl = "there is already such a user";
        }
        return rsl;
    }

    public Optional<UserDAO> findByLogin(String login) {
        return repos.findByUsername(login);
    }

    public String getFioUser(String login) {
        var optionalUserDAO = findByLogin(login);
        return optionalUserDAO.isPresent() ? optionalUserDAO.get().getFioUser() : "undefined";
    }
}