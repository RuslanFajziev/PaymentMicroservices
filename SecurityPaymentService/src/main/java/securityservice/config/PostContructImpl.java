package securityservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import securityservice.model.RoleDAO;
import securityservice.model.UserDAO;
import securityservice.service.RoleService;
import securityservice.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class PostContructImpl {
    private final RoleService roleService;
    private final UserService userService;
    @Value("${usr.name}")
    private String userName;
    @Value("${usr.password}")
    private String userPassword;
    @Value("${usr.role.name}")
    private String role;
    @Value("${usr.fio}")
    private String fio;

    public PostContructImpl(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        roleService.addRole(RoleDAO.of(role));
        var roleDAO = roleService.getRoleByRoleName(role);
        userService.addUser(UserDAO.of(userName, userPassword, null, fio), roleDAO);
    }
}