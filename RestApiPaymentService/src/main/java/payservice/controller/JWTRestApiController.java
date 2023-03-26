package payservice.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import payservice.model.UserDAO;
import payservice.service.JWTRestApiService;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class JWTRestApiController {
    private final JWTRestApiService service;
    private final Gson gson;

    @PostMapping("adduser")
    public UserDAO getJwt(@RequestBody UserDAO userDAO, HttpServletRequest request) {
        return service.addUser(userDAO);
    }

    @GetMapping("user")
    @PreAuthorize("isAuthenticated()")
    public String user(Authentication authentication) {
        var login = authentication.getPrincipal().toString();
        var role = authentication.getAuthorities().toString();
        return "Login: " + login + ",\n" + "Role: " + role;
    }
}