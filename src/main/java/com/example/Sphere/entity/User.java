package com.example.Sphere.entity;


import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.CollectionUtils;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId", unique = true)
    private String userId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "avatar", columnDefinition = "LONGBLOB")
    private Blob avatar;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<ItemsMenu> itemsMenus = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<MainPageModule> mainPageModules = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<ImagePromo> imagePromos = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<HeaderAvatar> headerAvatars = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Gallery> galleries = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<InfoModule> infoModules = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ETheme themes;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Moments> momentsList = new ArrayList<>();
    private Boolean enabled;
    private LocalDateTime lastTimeOnline;

    @ManyToMany
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "friend_id") )
    private Set<User> userFriends;

    @ManyToMany
    @JoinTable(name = "subscribe_users", joinColumns = @JoinColumn(name = "subscribe_user_id") , inverseJoinColumns = @JoinColumn(name = "target_user_id") )
    private Set<User> subscribeUsers;

    @ManyToMany
    @JoinTable(name = "block_users", joinColumns = @JoinColumn(name = "block_user_id") , inverseJoinColumns = @JoinColumn(name = "target_user_id") )
    private Set<User> blockUsers;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User( String userId, Blob avatar, String email, String password, String firstName, String lastName) {
        this.userId = userId;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Set<User> getUserFriends() {
        return userFriends;
    }

    public void setUserFriends(Set<User> userFriends) {
        this.userFriends = userFriends;
    }

    public Set<User> getSubscribeUsers() {
        return subscribeUsers;
    }

    public void setSubscribeUsers(Set<User> subscribeUsers) {
        this.subscribeUsers = subscribeUsers;
    }

    public Set<User> getBlockUsers() {
        return blockUsers;
    }

    public void setBlockUsers(Set<User> blockUsers) {
        this.blockUsers = blockUsers;
    }

    public void addUserFriends(User user) {
        if (CollectionUtils.isEmpty(this.userFriends)) {
            this.userFriends = new HashSet<>();
        }
        this.userFriends.add(user);
    }

    public void addSubscribeUsers(User user) {
        if (CollectionUtils.isEmpty(this.subscribeUsers)) {
            this.subscribeUsers = new HashSet<>();
        }
        this.subscribeUsers.add(user);
    }

    public void addBlockUsers(User user) {
        if (CollectionUtils.isEmpty(this.blockUsers)) {
            this.blockUsers = new HashSet<>();
        }
        this.blockUsers.add(user);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}