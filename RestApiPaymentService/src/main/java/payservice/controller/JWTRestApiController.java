package payservice.controller;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import payservice.service.RoleRestApiService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/")
@Tag(name = "Система управления пользователями", description = "Принимает пользователей для импорта в БД, выводит информацию login/role из authentication")
public class JWTRestApiController {
    private final JWTRestApiService serviceUser;
    private final RoleRestApiService serviceRole;
    private final Gson gson;

    @PostMapping("adduser")
    @PreAuthorize("hasAuthority('super_admin')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Загрузка пользователей", description = "Позволяет загрузить пользователей в БД")
    public ResponseEntity<String> getJwt(@Valid @RequestBody UserDAO userDAO) {
        String rsl = serviceUser.addUser(userDAO, userDAO.getRole());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("result: " + rsl);
    }

    @GetMapping("user")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Вывод данных authentication", description = "Позволяет выводить информацию об username/role из authentication")
    public ResponseEntity<String> user(Authentication authentication) {
        Map<String, String> value = new HashMap<>();
        value.put("username", authentication.getPrincipal().toString());
        value.put("role", authentication.getAuthorities().toString());
        String body = gson.toJson(value);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}