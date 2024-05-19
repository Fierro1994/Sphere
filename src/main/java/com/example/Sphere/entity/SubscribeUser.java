package com.example.Sphere.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "subscribe_users")
public class SubscribeUser {

    @Id
    @Column(name = "target_user_id")
    private Long targetUserId;

    @ManyToOne
    @JoinColumn(name = "subscribe_user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
