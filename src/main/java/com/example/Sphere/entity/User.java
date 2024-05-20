package com.example.Sphere.entity;


import java.sql.Blob;
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
    @JoinTable(
            name = "user_friends",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "friend_id") }
    )
    private Set<User> friends = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "subscriber_id") }
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = { @JoinColumn(name = "subscriber_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> subscriptions = new HashSet<>();
    @ManyToMany
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

    public User( String userId, Blob avatar, String email, String password, String firstName, String lastName) {
        this.userId = userId;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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