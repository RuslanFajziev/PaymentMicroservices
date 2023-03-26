package payservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static payservice.filter.JWTAuthenticationFilter.HEADERSTRING;
import static payservice.filter.JWTAuthenticationFilter.TOKENPREFIX;
import static payservice.filter.JWTAuthenticationFilter.SECRET;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger LOG = LoggerFactory.getLogger(JWTAuthorizationFilter.class.getSimpleName());

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADERSTRING);

        if (header == null || !header.startsWith(TOKENPREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADERSTRING);
        if (token != null) {
            /* parse the token. */
            var decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKENPREFIX, ""));
            String user = decodedJWT.getSubject();

            List<String> lstRole = decodedJWT.getClaim("claimAuthorities").asList(String.class);

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, getAuthorities(lstRole));
            }
            return null;
        }
        return null;
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}