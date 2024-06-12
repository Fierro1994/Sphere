package com.example.sphere.service;

import com.example.sphere.entity.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Data

public class UserDetailsImpl implements UserDetails {

    private User user;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private List<ImagePromo> imagePromos;
    private List<ItemsMenu> itemsMenus;
    private List<MainPageModule> listModulesMainPage;
    private ETheme theme;
    List<NavModules> navModules;
    private List<Avatar> avatars;
    public UserDetailsImpl(User user) {
        this.user = user;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.userId = user.getUserId();
        this.id = user.getId();
        this.itemsMenus = user.getItemsMenus();
        this.listModulesMainPage = user.getMainPageModules();
        this.theme = user.getThemes();
        this.imagePromos = user.getImagePromos();
        this.avatars = user.getAvatar();
        this.navModules = user.getNavItems();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Здесь вы можете преобразовать роли пользователя в объекты типа GrantedAuthority
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Установите соответствующее значение
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Установите соответствующее значение
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Установите соответствующее значение
    }

    @Override
    public boolean isEnabled() {
        return true; // Установите соответствующее значение
    }

}