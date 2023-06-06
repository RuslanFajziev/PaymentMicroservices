package securityservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import securityservice.model.RoleDAO;
import securityservice.repository.RolesRepos;
import securityservice.repository.UserRepos;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class RoleService {
    private final RolesRepos repos;
    private final UserRepos reposUsr;

    public List<RoleDAO> getAll() {
        List<RoleDAO> lstRole = new ArrayList<>();
        repos.findAll().forEach(lstRole::add);
        return lstRole;
    }

    public RoleDAO getRoleById(int id) {
        var optionalRoleDAO = repos.findById(id);
        return optionalRoleDAO.orElse(new RoleDAO());
    }

    public RoleDAO getRoleByRoleName(String roleName) {
        var optionalRoleDAO = repos.findByRolename(roleName);
        return optionalRoleDAO.orElse(new RoleDAO());
    }

    public boolean delRoleById(int id) {
        var optionalRole = repos.findById(id);
        if (optionalRole.isPresent() && (reposUsr.countAllByRole(optionalRole.get()) == 0)) {
            repos.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addRole(RoleDAO role) {
        if (repos.countAllByRolenameAndIdNot(role.rolename, role.id) > 0) {
            return false;
        }
        repos.save(role);
        return true;
    }
}