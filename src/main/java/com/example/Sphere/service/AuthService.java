package com.example.Sphere.service;

import com.example.Sphere.entity.*;
import com.example.Sphere.models.request.CreateUserRequest;
import com.example.Sphere.models.request.LoginRequest;
import com.example.Sphere.models.request.LogoutRequest;
import com.example.Sphere.models.response.AuthResponse;
import com.example.Sphere.models.response.SimpleResponse;
import com.example.Sphere.models.response.TokenRefreshResponse;
import com.example.Sphere.repository.*;
import com.example.Sphere.security.jwt.JwtUtils;
import com.example.Sphere.security.jwt.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    EmailService emailService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;
    Cookie cookie;
    @Autowired
    EmailValidator emailValidator;
    @Autowired
    ItemsMenuService itemsMenuService;
    @Autowired
    MainPageModuleService mainPageModuleService;
    @Autowired
    ImagePromoService imagePromoService;
    @Autowired
    InfoModuleService infoModuleService;
    @Autowired
    AvatarService avatarService;

    private String nameFolder = "avatars";


    @Value("${spring.mail.username}")
    private String userName;

    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest, HttpServletResponse response) throws SQLException, IOException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        if(loginRequest.getRememberMe() != null){
            if(loginRequest.getRememberMe()){
                if(refreshTokenRepository.findByUserId(userDetails.getId()).isPresent()){
                    refreshTokenService.deleteOldRefreshTokenByUserId(userDetails.getId());
                    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUserId());
                    cookie = refreshTokenService.generateRefreshJwtCookie(refreshToken.getToken(), 50000);
                    response.addCookie(cookie);
                }
                if(refreshTokenRepository.findByUserId(userDetails.getId()).isEmpty()){
                    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUserId());
                    cookie = refreshTokenService.generateRefreshJwtCookie(refreshToken.getToken(), 50000);
                    response.addCookie(cookie);
                }

        }

        }
        List<ItemsMenu> itemsMenus = userDetails.getItemsMenu().stream().toList();
        List<MainPageModule> listModulesMainPage = userDetails.getListModulesMainPage().stream().toList();
        ETheme theme = userDetails.getTheme();
        String email = userDetails.getEmail();
        String userId = userDetails.getUserId();
        List<Avatar> avatars = userDetails.getAvatar();

        Path path = Paths.get("src/main/resources/storage/"+ userId + "/" + nameFolder +"/" + avatars.get(0).getKey());
        byte[] blobAsBytes = null;
        try {
            blobAsBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .body(new AuthResponse(blobAsBytes, jwt, userId,  email, itemsMenus, listModulesMainPage, theme));
    }

    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody CreateUserRequest createUserRequest) throws SQLException, IOException {
        Map<String, Object> result = new HashMap<String, Object>();

              if (userRepository.existsByEmail(createUserRequest.getEmail())) {
                  result.put("Пользователь с таким именем уже существует!", null);

                  return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
        }

        int length = 14;
        boolean useLetters = true;
        boolean useNumbers = true;
        String userId = RandomStringUtils.random(length, useLetters, useNumbers);
        List<Avatar> avatars = new ArrayList<>();
        if (createUserRequest.getAvatar().contains("null")){
            avatars.add(avatarService.defaultUpload(userId));
        }else {
            avatars.add(avatarService.upload(createUserRequest.getAvatar(), userId)) ;

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

        return "<div style=\"display: flex; justify-content: center; height: 10vw;  width: 90vw; " +
                "background-color: rgb(0, 3, 2);\">" +
                "<div style=\" display: flex; justify-content: center; text-align: center; " +
                "height: 50vw;  width: 90vw; background-color: rgb(253, 253, 253);\">" +
                "<div>" +
                "<p style=\"font-size: 20px; font-weight: 700;\">Email подтверждён, спасибо! </p>" +
                "<p>Перейдите по ссылке, чтобы вернуться на сайт</p>" +
                "<a href=\"localhost:3000/\"> Активировать аккаунт </a>  </div> </div>";
    }

    private String buildEmail(String name, String link) {
        return
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
               +"<html lang=\"en\">"
               + "<head>"
                +  "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                  + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                  "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +

                  "<title>активация</title>" +

                  " <style type=\"text/css\">"
                  + "</style>"
                + "</head>"
                + "<body style=\"margin:0; padding:0; background-color:#F2F2F2;\">"
                  + "<center>"
                    + "<table width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"wrapper\" bgcolor=\"#FFFFFF\"> "
                        +"<tr>"
                          +"<td height=\"10\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td>"
                        +"</tr>"
                        +"<tr>"
                          + "<td align=\"center\" valign=\"top\">"

                            + "<table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"container\"> "
                                + "<tr>"
                                    + "<td align=\"center\" valign=\"top\" style = \"background-color: rgb(0, 0, 0)\">"
                                        + "<p style=\"font-size: 20px; font-weight: 700;  color: rgb(253, 253, 253)\">Спасибо, за регистрацию на сайте," +name +" </p>"

                                   + "</td>"
                                  + "</tr>"
                                  + "<tr>"
                                    + "<td align=\"center\" valign=\"top\">"
                                        + "<p>Чтобы завершить регистрацию, и активировать аккаунт, перейдите пожалуйста по ссылке ниже!</p>"
                                         + "<a style=\"font-size: 20px;\" href=\"" + link + "\"> Активировать аккаунт </a>  </div>"
                                    + "</td>"
                                  + "</tr>"
                            + "</table>"

                          + "</td>"
                       + "</tr>"
                        + "<tr>"
                          + "<td height=\"10\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td> "
                        + "</tr>"
                      + "</table>"
                  + "</center>"
                + "</body>"
               + "</html>";
    }
    public ResponseEntity<?> refreshToken(@RequestBody HttpServletRequest request, HttpServletResponse response){

        try {
            refreshTokenService.findByToken(WebUtils.getCookie(request, "refresh").getValue())
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        String accessToken= null;
                        try {
                            accessToken = jwtUtils.generateTokenFromEmail(user.getEmail(),userDetailsService.loadUserByUsername(user.getEmail()));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        refreshTokenService.deleteOldRefreshTokenByUserId(user.getId());
                        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());
                        cookie = refreshTokenService.generateRefreshJwtCookie(refreshToken.getToken(), 50000);
                        response.addCookie(cookie);
                        return ResponseEntity.ok()
                                .body(new TokenRefreshResponse(accessToken));
                    }).orElseThrow(()->new RuntimeException("Refresh token is not in database!.." + WebUtils.getCookie(request, "refresh").getValue()));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SimpleResponse("Refresh token not found"));
        }

        return ResponseEntity.ok().body(new SimpleResponse(""));
    }


    public ResponseEntity<?> logout(LogoutRequest logoutRequest) {
        User user = userRepository.findByuserId(logoutRequest.getUserId()).get();
        if(refreshTokenRepository.findByUser(user).isPresent()) {
            refreshTokenService.deleteOldRefreshTokenByUserId(user.getId());
            return ResponseEntity.ok(new SimpleResponse("logout sucessful"));
        }
        return ResponseEntity.ok(new SimpleResponse("logout sucessful"));

    }

}
