package payservice.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import payservice.model.AuthResponseJWT;
import payservice.model.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static String SECRET;
    public static long EXPIRATIONTIME;
    public static final String TOKENPREFIX = "Bearer ";
    public static final String HEADERSTRING = "Authorization";
    public static final String SWAGGERUI = "/swagger-ui/*";
    public static final String APIDOCS = "/v3/api-docs";
    public static final String APIDOCS2 = "/v3/api-docs/*";
    private final AuthenticationManager auth;
    private final Gson gson;

    public JWTAuthenticationFilter(AuthenticationManager auth, Gson gson, String secret, long expirationtime) {
        this.auth = auth;
        this.gson = gson;
        this.SECRET = secret;
        this.EXPIRATIONTIME = expirationtime;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            UserDTO userDTO = new ObjectMapper()
                    .readValue(req.getInputStream(), UserDTO.class);

            return auth.authenticate(new UsernamePasswordAuthenticationToken(userDTO.username,
                    userDTO.password, new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        var userDetails = (UserDetails) auth.getPrincipal();
        String userName = userDetails.getUsername();
        List<String> lstRole = new ArrayList<>();
        var grantedAuthorities = userDetails.getAuthorities();
        for (var elm : grantedAuthorities) {
            lstRole.add(elm.getAuthority());
        }

        String token = JWT.create()
                .withSubject(userName)
                .withClaim("claimAuthorities", lstRole)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .sign(HMAC512(SECRET.getBytes()));

//        res.addHeader(HEADERSTRING, TOKENPREFIX + token);

        AuthResponseJWT body = new AuthResponseJWT();
        body.setUsername(userName);
        body.setAccessToken(token);

        res.getWriter().write(gson.toJson(body));
        res.setContentType("application/json");
    }
}