package securityservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import securityservice.model.UserDAO;
import securityservice.service.UserService;

@AllArgsConstructor
@Slf4j
@Controller
public class UserController {
    private final UserService service;

//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"/", "index"})
    public String getIndex(Model model) {
        model.addAttribute("userServiceList", service.getAll());
        model.addAttribute("userCurrent", "JavaUsr");
        return "index";
    }

    @GetMapping("/user_edit{id}")
    public String userEdit(Model model, @RequestParam int id) {
        model.addAttribute("userService", service.getUserById(id));
        return "user_edit";
    }

    @PostMapping("/user_update")
    public String userUpdate(UserDAO userDAO) {
//        userDAO.setPassword(bCryptPasswordEncoder.encode(userDAO.password));
        service.addUser(userDAO);
        return "redirect:/";
    }

    @PostMapping("/user_add")
    public String userAdd(UserDAO userDAO) {
//        userDAO.setPassword(bCryptPasswordEncoder.encode(userDAO.password));
        service.addUser(userDAO);
        return "redirect:/";
    }

    @GetMapping("/user_add")
    public String userAdd() {
        return "user_add";
    }

    @GetMapping("/user_del{id}")
    public String userDel(@RequestParam int id) {
        service.delUserById(id);
        return "redirect:/";
    }

//    @PostMapping("/login")
//    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
//        var userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
//        if (userOptional.isEmpty()) {
//            model.addAttribute("error", "Почта или пароль введены неверно");
//            return "users/login";
//        }
//        var session = request.getSession();
//        session.setAttribute("user", userOptional.get());
//        return "redirect:/vacancies";
//    }
}