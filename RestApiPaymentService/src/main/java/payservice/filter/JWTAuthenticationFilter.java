package payservice.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import payservice.model.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //    @Value("${jwt.secret}")
    public static String SECRET = "SecretKeyToGenJWTs";
    //    @Value("${jwt.expiration.time}")
    public static long EXPIRATIONTIME = 864_000_000;
    public static final String TOKENPREFIX = "Bearer ";
    public static final String HEADERSTRING = "Authorization";
    public static final String ADDUSERURL = "/api/v1/adduser";
    public static final String ADDPAYMENTURL = "/api/v1/payment/add";
    private AuthenticationManager auth;
    private final Gson gson;

    public JWTAuthenticationFilter(AuthenticationManager auth, Gson gson) {
        this.auth = auth;
        this.gson = gson;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            UserDTO userDTO = new ObjectMapper()
                    .readValue(req.getInputStream(), UserDTO.class);

            return auth.authenticate(new UsernamePasswordAuthenticationToken(userDTO.login,
                    userDTO.password, new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        var userDetails = (UserDetails) auth.getPrincipal();
        String userName = userDetails.getUsername();
        List<String> lstRole = new ArrayList();
        var grantedAuthorities = userDetails.getAuthorities();
        for (var elm : grantedAuthorities) {
            lstRole.add(elm.getAuthority());
        }

        String token = JWT.create()
                .withSubject(userName)
                .withClaim("claimAuthorities", lstRole)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .sign(HMAC512(SECRET.getBytes()));

        res.addHeader(HEADERSTRING, TOKENPREFIX + token);

        Map<String, String> body = new HashMap<>();
        body.put("login", userName);
        body.put("access_token", token);

        res.getWriter().write(gson.toJson(body));
    }
}