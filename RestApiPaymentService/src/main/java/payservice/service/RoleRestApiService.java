package payservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import payservice.model.RoleDAO;
import payservice.repository.RolesRestApiRepos;

@AllArgsConstructor
@Slf4j
@Service
public class RoleRestApiService {
    private final RolesRestApiRepos repos;

    public boolean addRole(RoleDAO role) {
        if (repos.countAllByRolenameAndIdNot(role.rolename, role.id) > 0) {
            return false;
        }
        repos.save(role);
        return true;
    }
}