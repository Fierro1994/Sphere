package com.example.sphere.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;



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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Avatar> avatar = new ArrayList<>();

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<ItemsMenu> itemsMenus = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_nav")
    private List<NavModules> navItems = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<MainPageModule> mainPageModules = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<ImagePromo> imagePromos = new ArrayList<>();
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_friends",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "friend_id") }
    )
    private Set<User> friends = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "subscriber_id") }
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = { @JoinColumn(name = "subscriber_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> subscriptions = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "block_users",
            joinColumns = { @JoinColumn(name = "blockUser_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> blockUsers = new HashSet<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User( String userId, List<Avatar> avatar, String email, String password, String firstName, String lastName, LocalDateTime lastTimeOnline ) {
        this.userId = userId;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastTimeOnline = lastTimeOnline;
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