package com.example.Sphere.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_friends")
public class UserFriends {

    @Id
    @Column(name = "friend_id")
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
