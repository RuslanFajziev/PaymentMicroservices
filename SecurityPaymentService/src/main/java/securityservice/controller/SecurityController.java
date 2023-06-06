package securityservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Slf4j
@Controller
public class SecurityController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/AccessDenied")
    public String access() {
        return "AccessDenied";
    }
}