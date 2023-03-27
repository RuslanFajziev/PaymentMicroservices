package payservice;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;
import payservice.filter.JWTAuthenticationFilter;
import payservice.filter.JWTAuthorizationFilter;
import payservice.service.UserDetailsServiceImpl;

import static payservice.filter.JWTAuthenticationFilter.ADDUSERURL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Gson gson;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration.time}")
    private String expirationtime;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, Gson gson) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.gson = gson;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager(),
                gson, secret, Long.parseLong(expirationtime));
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, ADDUSERURL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                /* this disables session creation on Spring Security */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}