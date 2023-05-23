package securityservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import securityservice.model.UserDAO;
import securityservice.repository.UserRepos;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {
    private final UserRepos repos;

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

    public UserDAO addUser(UserDAO userDAO) {
        return repos.save(userDAO);
    }
}