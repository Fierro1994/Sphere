package com.example.Sphere.service;

import com.example.Sphere.entity.*;
import com.example.Sphere.exception.EntityNotFoundException;
import com.example.Sphere.exception.RefreshTokenException;
import com.example.Sphere.models.request.CreateUserRequest;
import com.example.Sphere.models.request.LoginRequest;
import com.example.Sphere.models.request.LogoutRequest;
import com.example.Sphere.models.response.AuthResponse;
import com.example.Sphere.models.response.RefreshTokenResponse;
import com.example.Sphere.models.response.SimpleResponse;
import com.example.Sphere.repository.*;
import com.example.Sphere.security.jwt.JwtUtils;
import com.example.Sphere.security.jwt.RefreshTokenService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final EmailValidator emailValidator;
    private final ItemsMenuService itemsMenuService;
    private final MainPageModuleService mainPageModuleService;
    private final ImagePromoService imagePromoService;
    private final InfoModuleService infoModuleService;
    private final AvatarService avatarService;

    @Value("${spring.mail.username}")
    private String userName;

    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest, HttpServletResponse response) throws SQLException, IOException {
        RefreshToken refreshToken;
        boolean isRememberMe = loginRequest.getRememberMe();
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        if (loginRequest.getRememberMe() != null && isRememberMe ) {
            refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
            Cookie cookie = refreshTokenService.generateRefreshCookie(refreshToken.getToken());
            response.addCookie(cookie);
        }
        List<ItemsMenu> itemsMenus = userDetails.getItemsMenus().stream().toList();
        List<MainPageModule> listModulesMainPage = userDetails.getListModulesMainPage().stream().toList();
        ETheme theme = userDetails.getTheme();
        String email = userDetails.getEmail();
        String userId = userDetails.getUserId();
        List<Avatar> avatars = userDetails.getAvatars();
        log.info("Аутентифицирован пользователь{}", userId);

        return ResponseEntity.ok()
                .body(new AuthResponse(avatars.get(0), jwt, userId, email, itemsMenus, listModulesMainPage, theme));
    }


    public ResponseEntity<Map<String, Object>> registerUser(CreateUserRequest createUserRequest) throws SQLException, IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean isExist = userRepository.existsByEmail(createUserRequest.getEmail());
        if (isExist) {
            log.error("User with email: {} already exists", createUserRequest.getEmail());
            throw  new EntityNotFoundException("User with email: " + createUserRequest.getEmail() + " is already exist");
        }
        int length = 14;
        boolean useLetters = true;
        boolean useNumbers = true;
        String userId = RandomStringUtils.random(length, useLetters, useNumbers);
        List<Avatar> avatars = new ArrayList<>();
        if (createUserRequest.getAvatar() == null) {
            avatars.add(avatarService.defaultUpload(userId));
        } else {
            avatars.add(avatarService.upload(createUserRequest.getAvatar(), userId));

        }
        User user = new User(
                userId,
                avatars,
                createUserRequest.getEmail(),
                encoder.encode(createUserRequest.getPassword()),
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                LocalDateTime.now());

        userRepository.save(user);

        Set<String> strRoles = createUserRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        List<ItemsMenu> itemsMenus = new ArrayList<>();
        List<MainPageModule> mainPageModules = new ArrayList<>();
        List<ImagePromo> imagePromos = new ArrayList<>();
        List<InfoModule> infoModules = new ArrayList<>();
        user.setThemes(ETheme.BLACK);
        userRepository.save(user);

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            itemsMenus.addAll(itemsMenuService.setDefaultUserItemsMenu());
            mainPageModules.addAll(mainPageModuleService.setDefaultUserPageModule());
            roles.add(userRole);
            imagePromos.addAll(imagePromoService.defaultUpload(user.getUserId()));
            infoModules.addAll(infoModuleService.setDefaultInfo(user.getUserId()));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        itemsMenus.addAll(itemsMenuService.setDefaultUserItemsMenu());
                        mainPageModules.addAll(mainPageModuleService.setDefaultUserPageModule());
                        roles.add(userRole);
                        try {
                            imagePromos.addAll(imagePromoService.defaultUpload(user.getUserId()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            infoModules.addAll(infoModuleService.setDefaultInfo(user.getUserId()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }
            });
        }

        user.setRoles(roles);
        user.setItemsMenus(itemsMenus);
        user.setMainPageModules(mainPageModules);
        user.setImagePromos(imagePromos);
        userRepository.save(user);

        boolean isValidEmail = emailValidator.test(createUserRequest.getEmail());
        if (isValidEmail) {
            String tokenForNewUser = userDetailsService.getVerifyEmailToken(user);

            String link = "http://localhost:8080/api/auth/confirm?token=" + tokenForNewUser;
            emailService.sendEmail(createUserRequest.getEmail(), buildEmail(createUserRequest.getFirstName(), link));

        } else {
            throw new IllegalStateException(String.format("Email %s, not valid", createUserRequest.getEmail()));
        }

        result.put("Регистрация прошла успешно!", null);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }


    @Transactional
    public String confirmToken(String token) {
        Optional<ConfirmationToken> confirmToken = confirmationTokenService.getToken(token);

        if (confirmToken.isEmpty()) {
            throw new IllegalStateException("Token not found!");
        }

        if (confirmToken.get().getConfirmedAt() != null) {
            throw new IllegalStateException("Email is already confirmed");
        }

        LocalDateTime expiresAt = confirmToken.get().getExpiresAt();

//        if (expiresAt.isBefore(LocalDateTime.now())) {
//            throw new IllegalStateException("Token is already expired!");
//        }
        confirmationTokenService.setConfirmedAt(token);
        userDetailsService.enableUser(confirmToken.get().getUser().getEmail());

        return """
        <div style="display: flex; justify-content: center; height: 10vw;  width: 90vw; background-color: rgb(0, 3, 2);">
            <div style="display: flex; justify-content: center; text-align: center; height: 50vw;  width: 90vw; background-color: rgb(253, 253, 253);">
                <div>
                    <p style="font-size: 20px; font-weight: 700;">Email подтверждён, спасибо! </p>
                    <p>Перейдите по ссылке, чтобы вернуться на сайт</p>
                    <a href="localhost:3000/"> Активировать аккаунт </a>
                </div>
            </div>
        </div>
        """;
    }

    private String buildEmail(String name, String link) {
            return """
        <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html lang="en">
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>активация</title>
        <style type="text/css">
        </style>
        </head>
        <body style="margin:0; padding:0; background-color:#F2F2F2;">
        <center>
        <table width="640" cellpadding="0" cellspacing="0" border="0" class="wrapper" bgcolor="#FFFFFF">
        <tr>
        <td height="10" style="font-size:10px; line-height:10px;">&nbsp;</td>
        </tr>
        <tr>
        <td align="center" valign="top">
        <table width="600" cellpadding="0" cellspacing="0" border="0" class="container">
        <tr>
        <td align="center" valign="top" style="background-color: rgb(0, 0, 0)">
        <p style="font-size: 20px; font-weight: 700; color: rgb(253, 253, 253)">Спасибо, за регистрацию на сайте,%s</p>
        </td>
        </tr>
        <tr>
        <td align="center" valign="top">
        <p>Чтобы завершить регистрацию, и активировать аккаунт, перейдите пожалуйста по ссылке ниже!</p>
        <a style="font-size: 20px;" href="%s"> Активировать аккаунт </a> </div>
        </td>
        </tr>
        </table>
        </td>
        </tr>
        <tr>
        <td height="10" style="font-size:10px; line-height:10px;">&nbsp;</td>
        </tr>
        </table>
        </center>
        </body>
        </html>
        """.formatted(name, link);
    }
    public ResponseEntity<RefreshTokenResponse> refreshToken(String accessToken, String refreshToken, HttpServletResponse response) {

        return refreshTokenService.findByRefreshToken(refreshToken)
                .map(refreshTokenService::validateRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    String accessTokenBearer = null;
                    if (StringUtils.isNotEmpty(accessToken) && accessToken.startsWith("Bearer ")) {
                         accessTokenBearer = accessToken.substring(7);
                    }
                     boolean isAccessValid = jwtUtils.isTokenValidFromRefresh(accessTokenBearer);
                     if (!isAccessValid){
                         log.error("Access token {} is not valid" , accessToken);
                         throw new RefreshTokenException("Access token is not valid");
                     }
                     User user = userRepository.findById(userId).orElseThrow(() ->
                             new RefreshTokenException("Fail get refresh token from user: " + userId));
                     UserDetailsImpl userDetails = new UserDetailsImpl(user);
                     RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
                     String resAccessToken = jwtUtils.generateToken(userDetails);
                     String resRefreshToken = newRefreshToken.getToken();
                     Cookie cookie = refreshTokenService.generateRefreshCookie(resRefreshToken);

                     response.addCookie(cookie);
                     return ResponseEntity.ok().body(new RefreshTokenResponse(resAccessToken));
                }).orElseThrow(()-> new RefreshTokenException("Refresh token not found"));
    }


    public ResponseEntity<?> logout(LogoutRequest logoutRequest) {
        User user = userRepository.findById(logoutRequest.getId()).get();
        if (refreshTokenRepository.findByUserId(user.getId()).isPresent()) {
            return ResponseEntity.ok(new SimpleResponse("logout sucessful"));
        }
        return ResponseEntity.ok(new SimpleResponse("logout sucessful"));
    }

}
