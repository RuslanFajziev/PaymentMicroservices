package securityservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import securityservice.model.UserDAO;
import securityservice.service.RoleService;
import securityservice.service.UserService;

@AllArgsConstructor
@Slf4j
@Controller
public class UserController {
    private final UserService service;
    private final RoleService serviceRole;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"/", "index"})
    @PreAuthorize("hasAuthority('super_admin')")
    public String getIndex(Model model) {
        model.addAttribute("userServiceList", service.getAll());
        return "index";
    }

    @GetMapping("/user_edit{id}")
    public String userEdit(Model model, @RequestParam int id) {
        model.addAttribute("userService", service.getUserById(id));
        model.addAttribute("roleList", serviceRole.getAll());
        return "user_edit";
    }

    @PostMapping({"/user_update", "/user_add"})
    @PreAuthorize("hasAuthority('super_admin')")
    public String userUpdate(Model model, UserDAO userDAO, int roleId) {
        userDAO.setPassword(bCryptPasswordEncoder.encode(userDAO.getPassword()));
        userDAO.setRole(serviceRole.getRoleById(roleId));
        var rsl = service.addUser(userDAO);
        if (rsl) {
            return "redirect:/";
        } else {
            userDAO.setUsername("");
            userDAO.setPassword("");
            model.addAttribute("errorMessage", "This name is already registered");
            model.addAttribute("userService", userDAO);
            return "user_edit";
        }
    }

    @GetMapping("/user_add")
    @PreAuthorize("hasAuthority('super_admin')")
    public String userAdd(Model model) {
        model.addAttribute("roleList", serviceRole.getAll());
        return "user_add";
    }

    @GetMapping("/user_del{id}")
    @PreAuthorize("hasAuthority('super_admin')")
    public String userDel(@RequestParam int id) {
        service.delUserById(id);
        return "redirect:/";
    }
}