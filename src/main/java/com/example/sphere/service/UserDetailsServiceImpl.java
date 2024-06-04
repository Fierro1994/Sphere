package com.example.sphere.service;

import com.example.sphere.entity.ConfirmationToken;
import com.example.sphere.entity.User;
import com.example.sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email: {} not found", email);
                    return new UsernameNotFoundException("User with email: " + email + " not found");
                });
        return new UserDetailsImpl(user);
    }

    public UserDetailsImpl loadUserFromContext(){
        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null){
                return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            } else return null;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    public String getVerifyEmailToken(User user) {
        boolean userExists = userRepository.existsByEmail(user.getEmail());
        String token = UUID.randomUUID().toString();
        saveConfirmationToken(user, token);
        return token;
    }


    private void saveConfirmationToken(User user, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);

    }
}

