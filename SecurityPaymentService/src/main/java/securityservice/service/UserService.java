package securityservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import securityservice.model.RoleDAO;
import securityservice.model.UserDAO;
import securityservice.repository.UserRepos;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {
    private final UserRepos repos;

    private final RoleService serviceRole;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<UserDAO> getAll() {
        List<UserDAO> lstUsr = new ArrayList<>();
        repos.findAll().forEach(elm -> lstUsr.add(elm));
        return lstUsr;
    }

    public UserDAO getUserById(int id) {
        var optionalUserDAO = repos.findById(id);
        return optionalUserDAO.orElse(new UserDAO());
    }

    public void delUserById(int id) {
        repos.deleteById(id);
    }

    public boolean addUser(UserDAO userDAO, int roleId) {
        return addUserCommon(userDAO, serviceRole.getRoleById(roleId));
    }

    public boolean addUser(UserDAO userDAO, RoleDAO roleDAO) {
        return addUserCommon(userDAO, roleDAO);
    }

    private boolean addUserCommon(UserDAO userDAO, RoleDAO roleDAO) {
        userDAO.setPassword(bCryptPasswordEncoder.encode(userDAO.getPassword()));
        userDAO.setRole(roleDAO);
        if (repos.countAllByUsernameAndIdNot(userDAO.username, userDAO.id) > 0) {
            return false;
        }
        repos.save(userDAO);
        return true;
    }
}