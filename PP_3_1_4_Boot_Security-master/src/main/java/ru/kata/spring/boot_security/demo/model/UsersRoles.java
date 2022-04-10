package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "users_roles")
public class UsersRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long row_id;
    private long user_id;
    private long role_id;

    public UsersRoles() {
    }

    public UsersRoles(long user_id, long role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }


    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }
}
