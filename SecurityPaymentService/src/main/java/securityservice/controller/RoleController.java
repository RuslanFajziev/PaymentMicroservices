package securityservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import securityservice.model.RoleDAO;
import securityservice.service.RoleService;

@AllArgsConstructor
@Slf4j
@Controller
public class RoleController {
    private final RoleService service;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('super_admin')")
    public String getRoles(Model model) {
        model.addAttribute("roleServiceList", service.getAll());
        return "roles";
    }

    @GetMapping("/role_edit{id}")
    @PreAuthorize("hasAuthority('super_admin')")
    public String roleEdit(Model model, @RequestParam int id) {
        model.addAttribute("roleService", service.getRoleById(id));
        return "role_edit";
    }

    @PostMapping({"/role_update", "/role_add"})
    @PreAuthorize("hasAuthority('super_admin')")
    public String roleUpdate(Model model, RoleDAO roleDAO) {
        var rsl = service.addRole(roleDAO);
        if (rsl) {
            return "redirect:/roles";
        } else {
            roleDAO.setRolename("");
            model.addAttribute("errorMessage", "This name is already registered");
            model.addAttribute("roleService", roleDAO);
            return "role_edit";
        }
    }

    @GetMapping("/role_add")
    @PreAuthorize("hasAuthority('super_admin')")
    public String roleAdd() {
        return "role_add";
    }

    @GetMapping("/role_del{id}")
    @PreAuthorize("hasAuthority('super_admin')")
    public String roleDel(Model model, @RequestParam int id) {
        var rsl = service.delRoleById(id);
        if (!rsl) {
            model.addAttribute("errorMessage", "This role is referenced, cannot be deleted");
        }
        return getRoles(model);
    }
}