package payservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import payservice.model.UserDAO;
import payservice.repository.JWTRestApiRepos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private JWTRestApiRepos loginRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDAO> optionalLogin = loginRep.findByUsername(username);
        if (optionalLogin.isEmpty()) {
            log.info("Not found login={}", username);
            throw new UsernameNotFoundException(username);
        }

        var userDAO = optionalLogin.get();
        List<String> lstRole = new ArrayList<>();
        lstRole.add(userDAO.getRole());

        log.info("Found login={}", username);
        return new User(userDAO.getUsername(), userDAO.getPassword(), getAuthorities(lstRole));
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}