package com.example.Sphere.security.jwt;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import com.example.Sphere.repository.RefreshTokenRepository;
import com.example.Sphere.repository.UserRepository;
import com.example.Sphere.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.lifetime}")
    private Long jwtExpirationMs;

    @Value("${jwtCookieName}")
    private String jwtRefreshCookie;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public String generateJwtToken(UserDetailsImpl userDetails) throws SQLException {
        return generateTokenFromEmail(userDetails.getEmail(), userDetails);
    }

    public String generateTokenFromEmail(String email, UserDetailsImpl userDetails) throws SQLException {

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Blob blob = userDetails.getAvatar();
        byte[]  blobAsBytes = null;
        int blobLength = 1;
        if(blob!=null){
             blobLength = (int) blob.length();
            blobAsBytes = blob.getBytes(1, blobLength);
        }





        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("firstName", userDetails.getFirstName());
        claims.put("lastName", userDetails.getLastName());
        claims.put("email", userDetails.getEmail());
        claims.put("roles",roles);
        claims.put("activateEmail", userDetails.getEnabled());
        claims.put("avatar", blobAsBytes);
        claims.put("theme", userDetails.getTheme().name());


        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println(e.getMessage());
        } catch (SignatureException e) {
            System.err.println(e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println(e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public String getEmailFromJwtToken(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
    }

}
