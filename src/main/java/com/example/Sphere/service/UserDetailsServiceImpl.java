package com.example.Sphere.service;

import com.example.Sphere.entity.ConfirmationToken;
import com.example.Sphere.entity.User;
import com.example.Sphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();

        return UserDetailsImpl.build(user);
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

