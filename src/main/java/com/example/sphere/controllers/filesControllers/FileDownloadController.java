package com.example.sphere.controllers.filesControllers;

import com.example.sphere.service.FileManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/files")
@RequiredArgsConstructor
public class FileDownloadController {
    @Autowired
    FileManager fileManager;
    @Value("${jwt.secret}")
    private String jwtSecret;
        @GetMapping("/temp/{token}")
        public ResponseEntity<Resource> getTemporaryVideoUrl(@PathVariable("token") String token, @RequestHeader HttpHeaders headers) throws IOException {try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(token)
                        .getBody();
                String userId = claims.getSubject();
                String key = claims.get("key", String.class);
                String format = claims.get("format", String.class);
                String category = claims.get("category", String.class);

                return fileManager.download(headers, userId, category, key, format);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

}
