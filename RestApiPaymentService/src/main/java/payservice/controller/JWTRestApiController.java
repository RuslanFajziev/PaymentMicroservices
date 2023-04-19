package payservice.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import payservice.model.UserDAO;
import payservice.service.JWTRestApiService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class JWTRestApiController {
    private final JWTRestApiService service;
    private final Gson gson;

    @PostMapping("adduser")
    @PreAuthorize("hasAuthority('super_admin')")
    public ResponseEntity<String> getJwt(@Valid @RequestBody UserDAO userDAO) {
        String rsl = service.addUser(userDAO);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("result: " + rsl);
    }

    @GetMapping("user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> user(Authentication authentication) {
        Map<String, String> value = new HashMap<>();
        value.put("login", authentication.getPrincipal().toString());
        value.put("role", authentication.getAuthorities().toString());
        String body = gson.toJson(value);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}